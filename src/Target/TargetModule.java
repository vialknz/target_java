package Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

import Target.HTC.VdiPETCorrected;

public class TargetModule
{
	
	public TargetModule(String workingDirectory)
	{
//		this.workingDirectory = workingDirectory;
	}
	
    public static double[][][] mod_data_ts_;
	private ArrayList<ArrayList<Double>> previousTacValues = new ArrayList<ArrayList<Double>>();

	private TreeMap<String,Double> tmrtCache=new TreeMap<String,Double>();
	private TreeMap<String,Double> utciCache=new TreeMap<String,Double>();
	private TreeMap<String,Double> petCache=new TreeMap<String,Double>();

	private double Tb_rur_prev=0.0;

	
	private RnCalcNew rnCalcNew = new RnCalcNew();
	private Lumps lumps = new Lumps();
	private ForceRestore forceRestore = new ForceRestore();
	private SfcRi sfcRi = new SfcRi();
	private Httc httc = new Httc();
	private CD cd = new CD();
	private TsEbW tsEbW = new TsEbW();
	private UTCI utciInstance = new UTCI();
	
	private VdiPETCorrected petInstance = new VdiPETCorrected();
	
	private TbRurSolver tbRurSolverOld = new TbRurSolver();
	
	public static final int FOR_TAB_FID_INDEX = 0;
	public static final int FOR_TAB_Ucan_INDEX = 1;
	public static final int FOR_TAB_Tsurf_horz_INDEX = 2;
	public static final int FOR_TAB_Tsurf_can_INDEX = 3;
	public static final int FOR_TAB_Tsurf_wall_INDEX = 4;
	public static final int FOR_TAB_Tac_INDEX = 5;
	public static final int FOR_TAB_dte_INDEX = 6;
	public static final int FOR_TAB_httc_urb_new_INDEX = 7;
	public static final int FOR_TAB_httc_can_INDEX = 8;
	public static final int FOR_TAB_Tb_rur_INDEX = 9;
	public static final int FOR_TAB_mod_U_TaRef_INDEX = 10;
	public static final int FOR_TAB_UTb_INDEX = 11;
	
	public static final int FOR_TAB_UTCI_FID_INDEX = 0;
	public static final int FOR_TAB_UTCI_tmrt_INDEX = 1;
	public static final int FOR_TAB_UTCI_utci_INDEX = 2;
	public static final int FOR_TAB_UTCI_PET_INDEX = 3;
	public static final int FOR_TAB_UTCI_dte_INDEX = 4;

	private static final String ROOF_KEY = "roof";
	private static final String ROAD_KEY = "road";
	private static final String WATR_KEY = "watr";
	private static final String CONC_KEY = "conc";
	private static final String VEG_KEY = "Veg";
	private static final String DRY_KEY = "dry";
	private static final String IRR_KEY = "irr";
	private static final String WALL_KEY = "wall";
	
	private static final int numberOfVf = 11;
	private static final int numberOfSurfaces = 11;
			
	public static ArrayList<String> surfs  = new ArrayList<String>()
	{/**
		 * 
		 */
		private static final long serialVersionUID = -615901337445318075L;

	{this.add(ROOF_KEY);
	this.add(ROAD_KEY);
	this.add(WATR_KEY);
	this.add(CONC_KEY);
	this.add(VEG_KEY);
	this.add(DRY_KEY);
	this.add(IRR_KEY);
	this.add(WALL_KEY);
	}};
	
	private static final int MOD_DATA_WALL_INDEX = 0;
	private static final int MOD_DATA_ROOF_INDEX = 1;
	private static final int MOD_DATA_ROAD_INDEX = 2;
	private static final int MOD_DATA_WATR_INDEX = 3;
	private static final int MOD_DATA_CONC_INDEX = 4;
	private static final int MOD_DATA_VEG_INDEX = 5;
	private static final int MOD_DATA_DRY_INDEX = 6;
	private static final int MOD_DATA_IRR_INDEX = 7;

	
	public static int getSurfIndex(String surf)
	{
		int returnValue = -999;
		
		if (surf.equals(ROOF_KEY))
		{
			return MOD_DATA_ROOF_INDEX;
		}
		if (surf.equals(ROAD_KEY))
		{
			return MOD_DATA_ROAD_INDEX;
		}
		if (surf.equals(WATR_KEY))
		{
			return MOD_DATA_WATR_INDEX;
		}
		if (surf.equals(CONC_KEY))
		{
			return MOD_DATA_CONC_INDEX;
		}
		if (surf.equals(VEG_KEY))
		{
			return MOD_DATA_VEG_INDEX;
		}
		if (surf.equals(DRY_KEY))
		{
			return MOD_DATA_DRY_INDEX;
		}
		if (surf.equals(IRR_KEY))
		{
			return MOD_DATA_IRR_INDEX;
		}
		if (surf.equals(WALL_KEY))
		{
			return MOD_DATA_WALL_INDEX;
		}
		
		if (surf.equals("TSOIL"))
		{
			return 8;
		}
		return returnValue;
	}




	public void modelRun(Cfm cfm, ArrayList<ArrayList<Double>> lc_data, ArrayList<ArrayList<Object>> met_data_all, TreeMap<String,Date> Dats,
			double maxH, double maxW, 
			int x, int y, 
			double latEdge, double latResolution, double lonEdge, double lonResolution, String outputFile)
	{
		
//		int n = Runtime.getRuntime().availableProcessors();
//		System.out.println(n + " processors available");
		
		String[] disableOutput = cfm.getValues("disableOutput");
		boolean individualNetcdfFiles = false;
		String individualNetcdfFilesCfm = cfm.getValue("individualNetcdfFiles");
		if (individualNetcdfFilesCfm != null && individualNetcdfFilesCfm.equalsIgnoreCase("true"))
		{
			individualNetcdfFiles = true;
		}
		
		// # model run name 
		String tmstp = cfm.getValue("timestep");                            
		// # time step (minutes)
		int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
//	    ######### DEFINE START AND FINISH DATES HERE ########
		// this variable is now called SpinUp in the config file
		Date spinUp = cfm.getDateValue("SpinUp");
		long simulationStartTimeLong = spinUp.getTime();
		
		Common common = new Common();
		String spinUpDateStr = common.getYearMonthDayStrFromDate(spinUp);

		//  ## the date/time for period of interest (i.e. before this will not be saved)
		Date endDate = cfm.getDateValue("EndDate");
      
		// ## end date/time of simulation 
		long tD = (endDate.getTime() - spinUp.getTime()) ;  
		// to days    / (1000 * 60 * 60 * 24)
 
		//## time difference between start and end date
		//# number of timesteps
		long ntLong = (tD / 1000) / tmstpInt ;
		int nt = (int) ntLong -1 ;
        
		// #  date range for model period 
		//# date range for model period (i.e. including spin-up period)
		// # this is a dictionary with all the date/time information 

		long timedelta = tmstpInt*1000;
		
		mod_data_ts_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_tm_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qh_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qe_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qg_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_rn_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		  
		//## NB: "TSOIL" is the soil temperature below the water layer
		double[] mod_fm = new double[nt];
		double[] mod_cd = new double[nt];
		double[] mod_U_TaRef = new double[nt];
	        
		long spinUpLong = spinUp.getTime();
	        // # begin looping through the met forcing data file
		    for (int i=0;i<nt;i++)
	        {             
	            if (! (i == met_data_all.size()-1))
	            {
	            	
	            	ArrayList<Object> met0 = met_data_all.get(i);	
	            	double metTa0 = (double)met0.get(MetData.Ta);
	            	double metKd0 = (double)met0.get(MetData.Kd);
	            	double metWS0 = (double)met0.get(MetData.WS);
	            	double metRH0 = (double)met0.get(MetData.RH);
	            	double metLD0 = (double)met0.get(MetData.Ld);
	                
	            	ArrayList<TreeMap<Integer,Double>> mod_rslts =new ArrayList<TreeMap<Integer,Double>>();
	            	// # this is the main data array where surface averaged outputs are stored
	                
	            	ArrayList<TreeMap<Integer,Double>> mod_rslts_tmrt_utci =new ArrayList<TreeMap<Integer,Double>>();
	                //############ Met variables for each time step (generate dataframe) ##########
	            	long timedeltaAddition = i*timedelta;
	            	// # current timestep 
	                Date dte   = new Date(spinUpLong + timedeltaAddition);  
	                Dats.put("dte", dte);
	                ArrayList<ArrayList<Object>> met_d = met_data_all ;
	                System.out.println(dte + " " + i );
	                
	                //## BEGIN CALCULATION OF Tb_rur
	                String ref_surf = LCData.DRY_SURF;
	                String ref_surf2 = LCData.CONC_SURF;
	                
	                //override these values if they are in the control file
	                if (cfm.getValue("ref_surf")!=null)
	                {
	                	ref_surf=cfm.getValue("ref_surf").trim();	
	                	System.out.println("overrode ref_surf as " + ref_surf);
	                }
	                if (cfm.getValue("ref_surf2")!=null)
	                {
	                	ref_surf2=cfm.getValue("ref_surf2").trim();	                	
	                }
	                
	                //## radiation balance
	                
	                ArrayList<Double> prevTsRef1 = new ArrayList<Double>();	
	                ArrayList<Double> prevTsRef2 = new ArrayList<Double>();	
	                ArrayList<Double> prevTmRefForce1 = new ArrayList<Double>();
	                ArrayList<Double> prevTmRefForce2 = new ArrayList<Double>();
	                if (i < 1)
	                {
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(0.);
		                prevTsRef2.add(0.);
		                prevTsRef2.add(0.);
	                }
	                else if (i < 2)
	                {
	                	prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf)]);
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(0.);
		                prevTsRef2.add(0.);
	                }
	                else if (i < 3)
	                {
	                	prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf)]);
	                	prevTsRef1.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf)]);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(0.);
	                }
	                else
	                {
		                prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf)]);
		                prevTsRef1.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf)]);
		                prevTsRef1.add(mod_data_ts_[i-3][9][getSurfIndex(ref_surf)]);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-3][9][getSurfIndex(ref_surf2)]);
	                }
	                // # creates dictionary with radiation variables for current timestep and surface type  
	                TreeMap<String,Double> rad_rur2  = rnCalcNew.rn_calc_new(cfm,met_d, ref_surf2,Dats,prevTsRef2,i,1.0);                            
	                //##################### ENG BALANCE for "reference" site #######################
	                // # creates dictionary with energy balance for current timestep and surface type
	                TreeMap<String,Double> eng_bals_rur2=lumps.lumps(rad_rur2,cfm,met_d,ref_surf2,Dats,i);            
	                //##################### CALC LST for "reference" site #########################
	                if (i < 1)
	                {
		                prevTmRefForce1.add(0.);	                
		                prevTmRefForce2.add(0.);
	                }
	                else
	                {
		                prevTmRefForce1.add(mod_data_tm_[i-1][9][getSurfIndex(ref_surf)]);	                
		                prevTmRefForce2.add(mod_data_tm_[i-1][9][getSurfIndex(ref_surf2)]);
	                }

	                TreeMap<String,Double> Ts_stfs_rur2 =forceRestore.Ts_calc_surf(eng_bals_rur2,cfm,prevTsRef2,prevTmRefForce2, Dats,ref_surf2,i);   // # creates dictionary with surface temperature for current timestep and surface type  
	                
	                double Ts_stfs_rur = Ts_stfs_rur2.get(ForceRestore.TS_KEY); 
	                
	                //### these are the parameters for calculating Tb_rur and httc_rur 
	                // #  Roughness length for momentum (m)
	                double z0m_rur = Constants.z0m_rur;
	                //override these values if they are in the control file
	                if (cfm.getValue("z0m_rur")!=null)
	                {
	                	z0m_rur=new Double(cfm.getValue("z0m_rur")).doubleValue();
	                }
	                
	                // #  Roughness length for heat (m)
	                double z0h_rur = z0m_rur/10.;

	                // # height of Tb (2 x max building height) - this is the secondary height used for Tb above canyon 
	                double z_Hx2  = maxH * 2.0; 
	                // # height of air temperature measurements (usually 2 m)
	                double z_TaRef = Constants.cs_z_TaRef;  
	                //override these values if they are in the control file
	                if (cfm.getValue("z_TaRef")!=null)
	                {
	                	z_TaRef=new Double(cfm.getValue("z_TaRef")).doubleValue();
	                }
	                // # height of reference wind speed measurement (usually 10 m)
	                double z_Uref  = Constants.cs_z_URef;   
	                //override these values if they are in the control file
	                if (cfm.getValue("z_URef")!=null)
	                {
	                	z_Uref=new Double(cfm.getValue("z_URef")).doubleValue();
	                }
	                // # surface temperature at rural (reference) site
	                double Tlow_surf = Ts_stfs_rur ;      
	                // # observed air temperature
	                double ref_ta = metTa0;       
	                
	                //####### DEFINE REFERENCE WIND SPEED RURAL #######
	                double uTopHeightMinimumValue = 0.1;
	                double uTopHeight = Math.max(metWS0 * ((Math.log(z_TaRef/z0m_rur))/(Math.log(z_Uref/z0m_rur))),uTopHeightMinimumValue);  //## convert to wind speed for Utop height using log profile.         CHECK initial extroplation is based on log profile (alternative is to use Fm from prev time step)
	                mod_U_TaRef[i]=uTopHeight;
	                
	                //####### calculate Richardson's number and heat transfer coefficient for rural site 
	                  //## calculate Richardson's number for Rural site   
	                double Ri_rur = sfcRi.sfc_ri(z_TaRef-z0m_rur,ref_ta,Tlow_surf,mod_U_TaRef[i]);   

	                TreeMap<String,Double> httc_rural = httc.httc(Ri_rur,mod_U_TaRef[i],z_TaRef-z0m_rur,z0m_rur,z0h_rur, met_d,i,Tlow_surf,ref_ta) ;      //## calculate httc for Rural site           
	                double httc_rur = httc_rural.get(Httc.HTTC_KEY);
	                
	                
	               // ###### calculate cd, fm, ustar used for calcuating wind speed    
	                TreeMap<String,Double> cd_out =  cd.cd(Ri_rur,z_TaRef-z0m_rur,z0m_rur,z0h_rur) ; 
	                double modFmI = cd_out.get(CD.FM_KEY);
	                mod_fm[i]=modFmI;
	                double modCdI = cd_out.get(CD.CD_OUT_KEY); 
	                mod_cd[i]=modCdI;	                
	                double ustar=Math.sqrt(mod_cd[i]) * Math.max(mod_U_TaRef[i],0.1) ;  
	                //###### calculate wind speed at Tb height (3 x H) -- accounts for stability (unlike log profile)
	                double UTb=Math.max(ustar/Constants.cs_karman*Math.log(z_Hx2/z0m_rur)/Math.sqrt(mod_fm[i]),0.1) ;
  	                
	                //###### Solve Richardson's number eq for "high temperature" aka Tb_rur 
	                double dz = z_Hx2 - z_TaRef ;
	                dz = Math.max(dz, 0.01);

	                
	                double Tb_rur ;
	                double javaTbRur=0;

	                //this is the java version of the convergence. Mostly works but not for every case. 
	                Tb_rur = tbRurSolverOld.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
	                if (Tb_rur == TbRurSolver.ERROR_RETURN || Tb_rur == 0.0)
	                {
	                	System.out.println("Error with java Tb_rur, returned value=" + Tb_rur);
	                	System.out.println("Called with " +i+" "+ dz+" "+ ref_ta+" "+ UTb+" "+ mod_U_TaRef[i]+" "+ Ri_rur+" ");
	                	Tb_rur = Tb_rur_prev;
	                	System.out.println("using previous Tb_rur=" + Tb_rur_prev);
	                }
	                else
	                {
//	                	System.out.println("Java Tb_rur=" + "\t" + Tb_rur);
	                	javaTbRur = Tb_rur;
	                }	             
	                Tb_rur = Tb_rur - 9.806/1004.67*dz;
        	                
	                //# always use iterative solution for rural Tb
	                                
	                //###### Begin calculating modelled variables for 10 different SVF values... 
	                for (int vf=0;vf<10;vf++)
	                {
	                    double svfg = (vf+1)/10.0;
	                    for (String surf : surfs)
	                    {
	                    	// # cycle through surface type for current timestep
	                    	if ( surf.equals(WATR_KEY) || surf.equals(VEG_KEY) )
	                    	{
	                    		//do nothing
	                    	}
	                    	else
	                        {	                    		
	                    		ArrayList<Double> prevTsRef = new ArrayList<Double>();	
	                    		if (i<1)
	                    		{
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                    		}
	                    		else if (i<2)
	                    		{
	                    			prevTsRef.add(mod_data_ts_[i-1][vf][getSurfIndex(surf)]);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                    		}
	                    		else if (i<3)
	                    		{
	                    			prevTsRef.add(mod_data_ts_[i-1][vf][getSurfIndex(surf)]);
	                    			prevTsRef.add(mod_data_ts_[i-2][vf][getSurfIndex(surf)]);
		        	                prevTsRef.add(0.);
	                    		}
	                    		else
	                    		{
		        	                prevTsRef.add(mod_data_ts_[i-1][vf][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-2][vf][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-3][vf][getSurfIndex(surf)]);
	                    		}
	                    		                
	        	                ArrayList<Double> prevTmRefForce = new ArrayList<Double>();
	        	                if (i < 1)
	        	                {
	        	                	prevTmRefForce.add(0.);
	        	                }
	        	                else
	        	                {
	        	                	prevTmRefForce.add(mod_data_tm_[i-1][9][getSurfIndex(surf)]);
	        	                }
	        	                
	                    		TreeMap<String,Double> rad = rnCalcNew.rn_calc_new(cfm,met_d,surf,Dats,prevTsRef,i,svfg);  
	                    		// # creates dictionary with radiation variables for current timestep and surface type                             
	                            //##################### ENG BALANCE non-water #######################
	                            TreeMap<String,Double> eng_bals=lumps.lumps(rad,cfm,met_d,surf,Dats,i);            
	                            // # creates dictionary with energy balance for current timestep and surface type
	                            //##################### CALC LST non-water #########################

	                            TreeMap<String,Double> Ts_stfs =forceRestore.Ts_calc_surf(eng_bals,cfm,prevTsRef,prevTmRefForce, Dats,surf,i);   // # creates dictionary with surface temperature for current timestep and surface type
	                           // ################################################################################
	                            //### append modelled data to dataframes below... 
	                                                   
	                            mod_data_ts_[i][vf][getSurfIndex(surf)] = Ts_stfs.get(ForceRestore.TS_KEY);
	                            mod_data_tm_[i][vf][getSurfIndex(surf)] = Ts_stfs.get(ForceRestore.TM_KEY) ;                
	                            mod_data_qh_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QH_KEY);
	                            mod_data_qe_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QE_KEY);
	                            mod_data_qg_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QG_KEY);
	                            mod_data_rn_[i][vf][getSurfIndex(surf)] = rad.get(RnCalcNew.RN_KEY);	                            
	                        }
	                        if (surf.equals(WATR_KEY))
	                        {
	                        	ArrayList<Double> prevTsRef = new ArrayList<Double>();	
	                        	if (i < 1)
	                        	{
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                        	}
	                        	else if (i < 2)
	                        	{
	                        		prevTsRef.add(mod_data_ts_[i-1][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                        	}
	                        	else if (i < 3)
	                        	{
	                        		prevTsRef.add(mod_data_ts_[i-1][9][getSurfIndex(surf)]);
	                        		prevTsRef.add(mod_data_ts_[i-2][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(0.);
	                        	}
	                        	else
	                        	{
		        	                prevTsRef.add(mod_data_ts_[i-1][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-2][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-3][9][getSurfIndex(surf)]);
	                        	}
	        	             
	                        	TreeMap<String,Double> rad  = rnCalcNew.rn_calc_new(cfm,met_d,surf,Dats,prevTsRef,i,svfg);  
	                        	// # creates dictionary with radiation variables for current timestep and surface type                             
	                        	TreeMap<String,Double> wtr_stf = tsEbW.ts_EB_W(met_d,cfm,mod_data_ts_,mod_data_tm_,Dats,i,rad,vf); 
	                        	// # creates dictionary with water surface temperature and energy balance 
	                            //### append modelled water variables to dataframes below...
	                        	                        	
	                            mod_data_ts_[i][vf][getSurfIndex(surf)] = wtr_stf.get(TsEbW.TSW_KEY);
	                            mod_data_tm_[i][vf][getSurfIndex(surf)] = wtr_stf.get(TsEbW.TM_KEY) ;                
	                            mod_data_qh_[i][vf][getSurfIndex(surf)]= wtr_stf.get(TsEbW.QHW_KEY);
	                            mod_data_qe_[i][vf][getSurfIndex(surf)] = wtr_stf.get(TsEbW.QEW_KEY);
	                            mod_data_qg_[i][vf][getSurfIndex(surf)]= wtr_stf.get(TsEbW.QGW_KEY);

	                            mod_data_ts_[i][vf][getSurfIndex("TSOIL")] = wtr_stf.get(TsEbW.TSOIL_KEY);
	                            mod_data_rn_[i][vf][getSurfIndex(surf)] = rad.get(RnCalcNew.RN_KEY) ; 
	                        }

	                    }
	                }
	                     
	                int counter=-1;
	                
	                //previousTacValues
	                ArrayList<Double> timestepsTacValues = new ArrayList<Double>();
	                
	                //# now cycle through each grid point
	                for (int grid=0;grid< lc_data.size();grid++)
	                {
	                    counter+=1;
	                    
	                    
	                    TreeMap<Integer,Double> for_tab = calcLoop(lc_data,grid,counter,i,met_d,
	                    		cfm,z_Uref,z_Hx2,Tb_rur,dte,mod_U_TaRef[i],UTb,previousTacValues, httc_rur) ;         

	                    timestepsTacValues.add(for_tab.get(FOR_TAB_Tac_INDEX));
	                    mod_rslts.add(for_tab);
       
	                    double lat = latEdge;
	                    
	            	    Calendar calendar = Calendar.getInstance();
	            	    calendar.setTime(dte);	
	            	    int yd_actual = calendar.get(Calendar.DAY_OF_YEAR);
	            	    int TM = calendar.get(Calendar.HOUR_OF_DAY);
	                    
	                    double tmrt;
	                    double utci;
	                    double pet;
	                    double Tac = for_tab.get(FOR_TAB_Tac_INDEX);
	                    double Ucan = for_tab.get(FOR_TAB_Ucan_INDEX);
	                    double Tsurf_can = for_tab.get(FOR_TAB_Tsurf_can_INDEX);
	                    if (Tac == -999.0)
	                    {
	                        tmrt = -999.0;
	                        utci = -999.0;
	                        pet =  -999.0;
	                    }
	                    else
	                    {   
	                    	double lup = Constants.cs_sb*Math.pow((metTa0  +273.15),4);	   
	                    	
	                    	String tmrtCacheKey = Tac + " " + metRH0+ " " +Ucan+ " " +metKd0+ " " +Tsurf_can+ " " +metLD0+ " " +lup+ " " +yd_actual+ " " +TM+ " " +lat;                       
	                    	Double tmrtCached = tmrtCache.get(tmrtCacheKey);                    	
	                    	if (tmrtCached == null)
	                    	{
	                    		tmrt = utciInstance.getTmrtForGrid_RH(Tac,metRH0,Ucan,metKd0,Tsurf_can,metLD0,lup,yd_actual,TM,lat);	
	                    		tmrtCache.put(tmrtCacheKey, tmrt);
//	                    		String tmrtOutput = tmrt + "," + Tac + "," + metRH0+ "," +Ucan+ "," +metKd0+ "," +Tsurf_can+ "," +metLD0+ "," +lup+ "," +yd_actual+ "," +TM+ "," +lat;                       	                    	
//	                    		common.appendFile(tmrtOutput, rootDirectory + "/" + "tmrtOutput.csv");                    		
	                    	}
	                    	else
	                    	{
	                    		tmrt = tmrtCached;
	                    	}
	                         
	                        String utciCacheKey = Tac+ " " +Ucan+ " " +metRH0+ " " +tmrt;	                        
	                      	Double utciCached = utciCache.get(utciCacheKey);
	                    	if (utciCached == null)
	                    	{
	                    		utci = utciInstance.getUTCIForGrid_RH(Tac,Ucan,metRH0,tmrt);
	                    		utciCache.put(utciCacheKey, utci);
	                    	}
	                    	else
	                    	{
	                    		utci = utciCached;
	                    	}
	                                       		
	                		double Tair=Tac;  //air temp in C
	                		double Tmrt=tmrt;  //tmrt in C
	                		double v_air=Ucan; //air velocity in m/s 
	                		double M_activity=80; // [W]
	                		double icl=0.9; //clothing level
	                		double rh = metRH0;
	                		
	                		// PET is experimental and hasn't been added yet
	                		String petCacheKey = Tair+ " " + Tmrt+ " " + rh+ " " + v_air+ " " + M_activity;	                		
	                      	Double petCached = petCache.get(petCacheKey);
	                    	if (petCached == null)
	                    	{	                    		
	                    		double[] petSystemreturnValue = petInstance.system(Tair, Tmrt, rh, v_air, M_activity, icl);		
	                    		double[] petReturnValue = petInstance.pet(petSystemreturnValue[VdiPETCorrected.tcore2_index], 
	                    				petSystemreturnValue[VdiPETCorrected.tsk2_index], 
	                    				petSystemreturnValue[VdiPETCorrected.tcl2_index], 
	                    				Tair, petSystemreturnValue[VdiPETCorrected.esw2_index]);
	                    		pet = petReturnValue[VdiPETCorrected.tx_index];
	                    		petCache.put(petCacheKey, pet);
	                    	}
	                    	else
	                    	{
	                    		pet = petCached;
	                    	}	
	                    	
	                    }
	    	                    
	                	TreeMap<Integer,Double> for_tab_tmrt_utci = new TreeMap<Integer,Double>();
	                	double fid = (double)lc_data.get(grid).get(LCData.FID);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_FID_INDEX,fid);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_tmrt_INDEX,tmrt);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_utci_INDEX,utci);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_PET_INDEX,pet);
	                	double dteDouble = (double)dte.getTime();
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_dte_INDEX,dteDouble);
	                	mod_rslts_tmrt_utci.add(for_tab_tmrt_utci);	          
	                }
	                
	                previousTacValues.add(timestepsTacValues);
	                NetCdfOutput netCdfOutput = new NetCdfOutput();
	                netCdfOutput.setDisabled(disableOutput);
	                netCdfOutput.setIndividualNetcdfFiles(individualNetcdfFiles);
	                netCdfOutput.setSimulationStartTimeLong(simulationStartTimeLong);
	                netCdfOutput.outputNetcdf2(outputFile, x, y, mod_rslts, mod_rslts_tmrt_utci,i,tmstpInt,spinUpDateStr,
	                		latEdge, latResolution, lonEdge, lonResolution);
	            }	            
	        }	                        
	}                 


public TreeMap<Integer,Double> calcLoop(ArrayList<ArrayList<Double>> lc_data,int grid,int counter,int i,ArrayList<ArrayList<Object>> met_d,
		Cfm cfm, double z_Uref, double z_Hx2, double Tb_rur,
		Date dte, double mod_U_TaRef, double UTb, 
		ArrayList<ArrayList<Double>> mod_rslts_prev, 
		double httc_rur)
{
	ArrayList<Object> met0 = met_d.get(i);	
	double metTa0 = (double)met0.get(MetData.Ta);
	double metWS0 = (double)met0.get(MetData.WS);   

    //# create dictionary for raw LC inputs
    double H      = lc_data.get(grid).get(LCData.H);     //  # building height for grid point
    double W      = lc_data.get(grid).get(LCData.W);      // # street width for grid point 
    //## create dictionary with land cover stuff
    LcSort lcSort = new LcSort();
    ArrayList<Double> LC_orig = lc_data.get(grid);
    ArrayList<Double> LC = new ArrayList<Double>(Arrays.asList(new Double[LC_orig.size()]));
	Collections.copy(LC, LC_orig);  
    TreeMap<String,Object> lc_stuff = lcSort.lc_sort(LC,H,W);
          
    //# define surface temperature of trees as canyon air temperature     
    if (! (i == 0))
    {
    	mod_data_ts_[i][9][getSurfIndex("Veg")] = mod_rslts_prev.get(i-1).get(grid);
    }
    else
    {
        mod_data_ts_[i][9][getSurfIndex("Veg")] = metTa0;
    }
            
    
    //### below various land cover dictionaries are defined.
    LC  = (ArrayList<Double>) lc_stuff.get(LcSort.LC_KEY);    
    //  # all surfaces not averaged (can be > 1.0)
    ArrayList<Double> LC_wRoofAvg   = (ArrayList<Double>) lc_stuff.get(LcSort.LC_wRoofAvg_KEY);        
    // # average over the total 3D surface (will be = 1.0)

    int fw   = (int) lc_stuff.get(LcSort.fw_KEY);     
    //  # integer used to indicate relevant wall SVF - this doesn't get used any more - Ts for walls is stored in "mod_data_ts_[i][9]['wall']"
    int fg   = (int) lc_stuff.get(LcSort.fg_KEY);     
    

    
    //## convert to wind speed for, top of the canopy, using log profile.
    double zAve = Constants.cs_zavg;
    if (cfm.getValue("zavg")!=null)
    {
    	zAve=new Double(cfm.getValue("zavg")).doubleValue();
    }
    double Hz  = Math.max(H, zAve);
    
    double z0m_urb = 0.1 * Hz;  // ## urban roughness length momentum [CHECK]
    double z0h_urb = z0m_urb/10.0; // ##  urban roughness lenght heat [CHECK]

    double Uz = Math.max(metWS0 * ((Math.log(Hz/z0m_urb))/(Math.log(z_Uref/z0m_urb))),0.1);
    //## calculate wind speed in the canyon - I used the wind speed from log profile here not the "stability" calculated wind speed
    double lcStuffWTree = (double) lc_stuff.get(LcSort.Wtree_KEY);
    // # forcing wind speed height, currently using 3xH   
    double Ucan     = Uz*Math.exp(-0.386*(Hz/lcStuffWTree));   
    //## calculate surface resistance (s/m)
    double rs_can   = (Constants.cs_pa*Constants.cs_cpair)/(11.8+(4.2*Ucan));		
    //## heat transfer coefficient for the canyon 
    double httc_can = 1.0/rs_can ;                                          

    
    //##### average Ta above canyon and Ta above roof by canyon and roof fraction
  //# plan area of roof 
    double LCroof = LC.get(LCData.roof);     
  //# plan area of canyon
    double LCcan  = LC.get(LCData.road)+LC.get(LCData.watr)+LC.get(LCData.conc)+LC.get(LCData.Veg)+LC.get(LCData.dry)+LC.get(LCData.irr); 
//    #LCcan  = LC['road']+LC['watr']+LC['conc']+LC['dry']+LC['irr'] //# plan area of canyon
    double LChorz  = LCroof+LCcan;
    //double PlanRoof   =  LCroof/LChorz;
    double PlanCan   = LCcan/LChorz;
    //################################

    double Tacprv;
    double roofTsrfT;
    
//    int roofIndex = getSurfIndex("roof");
    int roofIndex = 0;
    int roadIndex = getSurfIndex("road");
    int wallIndex = getSurfIndex("wall");
    int dryIndex = getSurfIndex("dry");
    int concIndex = getSurfIndex("conc");
    int VegIndex = getSurfIndex("Veg");
    int irrIndex = getSurfIndex("irr");
    int watrIndex = getSurfIndex("watr");
    
    
    if (! (i == 0))
    {
        //Tacprv=mod_rslts_prev.get(grid); 
    	Tacprv=mod_rslts_prev.get(i-1).get(grid);         
        //roofTsrfT = mod_data_ts_[i-1][roofIndex][9];
        roofTsrfT = mod_data_ts_[i-1][9][roofIndex];
    }        
    else
    {
        Tacprv = metTa0;
        roofTsrfT = metTa0;
    } 
    
    //## calculate Richardson's number for roof  
    double Tac_can_roof = ((LCroof/LChorz)*roofTsrfT) + ((LCcan/LChorz)*Tacprv)  ;  

    //## calculate httc for Rural site  
    double Ri_urb_new = sfcRi.sfc_ri(z_Hx2-H-z0m_urb,Tb_rur,Tac_can_roof,Uz);  

    TreeMap<String,Double> httcReturn = httc.httc(Ri_urb_new,Uz,z_Hx2-H-z0m_urb,z0m_urb,z0h_urb, met_d,i,Tac_can_roof,Tb_rur); 
    
    double httc_urb_new =  httcReturn.get(Httc.HTTC_KEY);  

    //## calculate Tsurf of the canyon... this inclues walls
    double Tsurf_can  = (mod_data_ts_[i][9][roofIndex]*LC.get(LCData.roof)) 
    		+ (mod_data_ts_[i][fg][concIndex]*LC.get(LCData.conc)) 
    		+  (mod_data_ts_[i][fg][roadIndex]*LC.get(LCData.road)) 
    		+ (mod_data_ts_[i][fg][watrIndex]*LC.get(LCData.watr)) 
    		+ (mod_data_ts_[i][fg][dryIndex]*LC.get(LCData.dry))  
    		+  (mod_data_ts_[i][fg][irrIndex]*LC.get(LCData.irr)) 
    		+ (mod_data_ts_[i][fw][wallIndex]*LC.get(LCData.wall))  
    		+ (mod_data_ts_[i][9][VegIndex]*LC.get(LCData.Veg)) ; 
    
    //# calculate average horizontal surface temperature (excludes walls) -- Tsurf = 1.0
    ArrayList<Double> LcH  = LC_wRoofAvg ;
    double Tsurf_horz = (mod_data_ts_[i][9][roofIndex]*LcH.get(LCData.roof)) 
    		+ (mod_data_ts_[i][fg][concIndex]*LcH.get(LCData.conc)) 
    		+  (mod_data_ts_[i][fg][roadIndex]*LcH.get(LCData.road)) 
    		+ (mod_data_ts_[i][fg][watrIndex]*LcH.get(LCData.watr)) 
    		+ (mod_data_ts_[i][fg][dryIndex]*LcH.get(LCData.dry))  
    		+  (mod_data_ts_[i][fg][irrIndex]*LcH.get(LCData.irr)) 
    		+ (mod_data_ts_[i][9][VegIndex]*LcH.get(LCData.Veg));
    double Tsurf_wall = mod_data_ts_[i][fw][wallIndex];
     
//    ################################################
//                    ## calculate the canopy air temperature (Tac)  ##
//    ################################################

    double Tac = -999.0;
    if (cfm.getValue("include roofs").equals("Y"))
    {
        if (Constants.directRoofs)
        {
//            ## this connects the roofs directly to the canyon via 2 resistances [this one works best]
            Tac =  ((mod_data_ts_[i][fg][concIndex]*httc_can*LC.get(LCData.conc)) 
            		+ (mod_data_ts_[i][9][roofIndex]/((1./httc_can)+(1./httc_urb_new))*LC.get(LCData.roof)) 
            		+  (mod_data_ts_[i][fg][roadIndex]*httc_can*LC.get(LCData.road)) 
            		+ (mod_data_ts_[i][fg][watrIndex]*httc_can*LC.get(LCData.watr)) 
            		+ (mod_data_ts_[i][fg][dryIndex]*httc_can*LC.get(LCData.dry))  
            		+  (mod_data_ts_[i][fg][irrIndex]*httc_can*LC.get(LCData.irr)) 
            		+ (mod_data_ts_[i][fw][wallIndex]*httc_can*LC.get(LCData.wall))  
            		+ (mod_data_ts_[i][9][VegIndex]*httc_can*LC.get(LCData.Veg)) 
            		+ (Tb_rur* httc_urb_new*PlanCan) )   / ((httc_can*LC.get(LCData.conc)) 
            				+ (LC.get(LCData.roof)/((1./httc_can)+(1./httc_urb_new))) 
            				+ (httc_can*LC.get(LCData.road)) + (httc_can*LC.get(LCData.watr)) 
            				+ (httc_can*LC.get(LCData.dry))  +  (httc_can*LC.get(LCData.irr)) 
            				+ (httc_can*LC.get(LCData.wall))  
            				+ (httc_can*LC.get(LCData.Veg)) 
            				+ (httc_urb_new*PlanCan));  

        }
    }
//    # roofs are not connected to the canyon at all [this one works well, but means that (green) roofs affect Tac]   
    if (cfm.getValue("include roofs").equals("N"))
    {
        Tac =  ((mod_data_ts_[i][fg][concIndex]*httc_can*LC.get(LCData.conc)) 
        		+  (mod_data_ts_[i][fg][roadIndex]*httc_can*LC.get(LCData.road)) 
        		+ (mod_data_ts_[i][fg][watrIndex]*httc_can*LC.get(LCData.watr)) 
        		+ (mod_data_ts_[i][fg][dryIndex]*httc_can*LC.get(LCData.dry))  
        		+  (mod_data_ts_[i][fg][irrIndex]*httc_can*LC.get(LCData.irr)) 
        		+ (mod_data_ts_[i][fw][wallIndex]*httc_can*LC.get(LCData.wall))  
        		+ (mod_data_ts_[i][9][VegIndex]*httc_can*LC.get(LCData.Veg)) 
        		+ (Tb_rur* httc_rur*PlanCan) )   
        		/ ((httc_can*LC.get(LCData.conc)) +  (httc_can*LC.get(LCData.road)) 
        				+ (httc_can*LC.get(LCData.watr)) + (httc_can*LC.get(LCData.dry))  
        				+  (httc_can*LC.get(LCData.irr)) + (httc_can*LC.get(LCData.wall))  
        				+ (httc_can*LC.get(LCData.Veg)) + (httc_rur*PlanCan));

    }

//    ## we don't calculate Tac if the roof frac is 1.0
    if (LC.get(LCData.roof) > 0.75 )
    {
        Tac = -999.0;
        Tsurf_horz = mod_data_ts_[i][9][roofIndex]*LcH.get(LCData.roof);
    }
    
    TreeMap<Integer,Double> for_tab = new TreeMap<Integer,Double>();

//    ## append everything to output table #####   
        double FID = (double)lc_data.get(grid).get(LCData.FID);
        for_tab.put(FOR_TAB_FID_INDEX, FID);        
        for_tab.put(FOR_TAB_Ucan_INDEX,Ucan);
        for_tab.put(FOR_TAB_Tsurf_horz_INDEX, Tsurf_horz);
        for_tab.put(FOR_TAB_Tsurf_can_INDEX, Tsurf_can);
        for_tab.put(FOR_TAB_Tsurf_wall_INDEX, Tsurf_wall);
        for_tab.put(FOR_TAB_Tac_INDEX, Tac);
        double dteDouble = (double)dte.getTime();
        for_tab.put(FOR_TAB_dte_INDEX, dteDouble);
        for_tab.put(FOR_TAB_httc_urb_new_INDEX, httc_urb_new);
        for_tab.put(FOR_TAB_httc_can_INDEX, httc_can);
        for_tab.put(FOR_TAB_Tb_rur_INDEX, Tb_rur);
        for_tab.put(FOR_TAB_mod_U_TaRef_INDEX, mod_U_TaRef);
        for_tab.put(FOR_TAB_UTb_INDEX, UTb);
               
    return for_tab;
}
        

         		      
	
}
