package Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class TargetModule
{
	
	public TargetModule(String workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}
	
	private ArrayList<ArrayList<Double>> previousTacValues = new ArrayList<ArrayList<Double>>();

//	private TreeMap<String,Double> tmrtCache=new TreeMap<String,Double>();
//	private TreeMap<String,Double> utciCache=new TreeMap<String,Double>();
//	private TreeMap<String,Double> Tb_rurCache=new TreeMap<String,Double>();
//	private TreeMap<String,Double> calcLoopCache=new TreeMap<String,Double>();
	private double Tb_rur_prev=0.0;
	private double Ri_rur_prev=0.0;
	
	private RnCalcNew rnCalcNew = new RnCalcNew();
	private Lumps lumps = new Lumps();
	private ForceRestore forceRestore = new ForceRestore();
	private SfcRi sfcRi = new SfcRi();
	private Httc httc = new Httc();
	private CD cd = new CD();
	private TsEbW tsEbW = new TsEbW();
	private UTCI utciInstance = new UTCI();
	//private TbRurSolver tbRurSolver = new TbRurSolver();
	private String workingDirectory;
	private TbRurSolver_python tbRurSolver = new TbRurSolver_python();
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
	public static final int FOR_TAB_UTCI_dte_INDEX = 3;

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
	private static final int MOD_DATA_TSOIL_INDEX = 8;
	private static final int MOD_DATA_AVG_INDEX = 9;
	private static final int MOD_DATA_DATE_INDEX = 10;
	
	
			
//	ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_all_timesteps =new ArrayList<ArrayList<TreeMap<Integer,Double>>>();
//	ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_tmrt_utci_all_timesteps =new ArrayList<ArrayList<TreeMap<Integer,Double>>>();
	//NetCdfOutput netCdfOutput = new NetCdfOutput(loc);

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
			return 1;
		}
		return returnValue;
	}




	public void modelRun(Cfm cfm, ArrayList<ArrayList<Double>> lc_data, ArrayList<ArrayList<Object>> met_data_all, TreeMap<String,Date> Dats,
			double maxH, double maxW, 
			int x, int y, double latEdge, double latResolution, double lonEdge, double lonResolution, String outputFile)
	{
		
		String[] disableOutput = cfm.getValues("disableOutput");
		
		
		//ArrayList<Double> mod_rslts_prev = new ArrayList<Double>();
		
		// # parse dates for input met file using format defined in control file
		String run = cfm.getValue("run_name");                           
		// # model run name 
		String tmstp = cfm.getValue("timestep");                            
		// # time step (minutes)
		int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
//	    ######### DEFINE START AND FINISH DATES HERE ########
		Date date1A = cfm.getDateValue("date1A");
		
		Common common = new Common();
		String date1ADateStr = common.getYearMonthDayStrFromDate(date1A);
		

		// ## the date/time that the simulation starts
		Date date1 = cfm.getDateValue("date1");
   
		//  ## the date/time for period of interest (i.e. before this will not be saved)
		Date date2 = cfm.getDateValue("date2");
//		System.out.println(cfm.getDateValue("date2"));
      
		// ## end date/time of simulation 
		long tD = (date2.getTime() - date1A.getTime()) ;  
		// to days    / (1000 * 60 * 60 * 24)
//		System.out.println(tD);
 
		//## time difference between start and end date

		//# number of timesteps
		long ntLong = (tD / 1000) / tmstpInt ;
		int nt = (int) ntLong -1 ;
//		System.out.println(nt);
        
		// #  date range for model period 
		//# date range for model period (i.e. including spin-up period)
		// # this is a dictionary with all the date/time information 

		int timedelta = tmstpInt*1000;
		
		double[][][] mod_data_ts_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_tm_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qh_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qe_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_qg_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][] mod_data_rn_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		  
		//## NB: "TSOIL" is the soil temperature below the water layer
		double[] mod_fm = new double[nt];
		double[] mod_cd = new double[nt];
		double[] mod_U_TaRef = new double[nt];
	        
	        // # begin looping through the met forcing data file
		    for (int i=0;i<nt;i++)
	        {
                long t10 = System.currentTimeMillis();
                
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
	                Date dte   = date1A;
	                dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	                Dats.put("dte", dte);
	                ArrayList<ArrayList<Object>> met_d = met_data_all ;
	                System.out.println(dte + " " + i);
	                
	                //## BEGIN CALCULATION OF Tb_rur
	                String ref_surf1 = LCData.DRY_SURF;
	                String ref_surf2 = LCData.CONC_SURF;
	                //## radiation balance
	                
	                ArrayList<Double> prevTsRef1 = new ArrayList<Double>();	
	                ArrayList<Double> prevTsRef2 = new ArrayList<Double>();	
	                ArrayList<Double> prevTmRefForce1 = new ArrayList<Double>();
	                ArrayList<Double> prevTmRefForce2 = new ArrayList<Double>();
//	                System.out.println(mod_data_ts_.toString());
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
	                	prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf1)]);
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(0.);
		                prevTsRef2.add(0.);
	                }
	                else if (i < 3)
	                {
	                	prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf1)]);
	                	prevTsRef1.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf1)]);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(0.);
	                }
	                else
	                {
		                prevTsRef1.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf1)]);
		                prevTsRef1.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf1)]);
		                prevTsRef1.add(mod_data_ts_[i-3][9][getSurfIndex(ref_surf1)]);
		                
		                prevTsRef2.add(mod_data_ts_[i-1][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-2][9][getSurfIndex(ref_surf2)]);
		                prevTsRef2.add(mod_data_ts_[i-3][9][getSurfIndex(ref_surf2)]);
	                }
	                TreeMap<String,Double> rad_rur1  = rnCalcNew.rn_calc_new(cfm,met_d, ref_surf1,Dats,prevTsRef1,i,1.0);  // # creates dictionary with radiation variables for current timestep and surface type
//	                System.out.println(i + " " + rad_rur1.toString()); 
	            
	                TreeMap<String,Double> rad_rur2  = rnCalcNew.rn_calc_new(cfm,met_d, ref_surf2,Dats,prevTsRef2,i,1.0); // # creates dictionary with radiation variables for current timestep and surface type                             
	                //##################### ENG BALANCE for "reference" site #######################
	                TreeMap<String,Double> eng_bals_rur1=lumps.lumps(rad_rur1,cfm,met_d,ref_surf1,Dats,i);            // # creates dictionary with energy balance for current timestep and surface type
	                TreeMap<String,Double> eng_bals_rur2=lumps.lumps(rad_rur2,cfm,met_d,ref_surf2,Dats,i);            // # creates dictionary with energy balance for current timestep and surface type
	                //##################### CALC LST for "reference" site #########################
	                if (i < 1)
	                {
		                prevTmRefForce1.add(0.);	                
		                prevTmRefForce2.add(0.);
	                }
	                else
	                {
		                prevTmRefForce1.add(mod_data_tm_[i-1][9][getSurfIndex(ref_surf1)]);	                
		                prevTmRefForce2.add(mod_data_tm_[i-1][9][getSurfIndex(ref_surf2)]);
	                }

	                ArrayList<Double> prevTsRefForce2 = new ArrayList<Double>();
	                TreeMap<String,Double> Ts_stfs_rur1 =forceRestore.Ts_calc_surf(eng_bals_rur1,cfm,prevTsRef1,prevTmRefForce2, Dats,ref_surf1,i) ;  // # creates dictionary with surface temperature for current timestep and surface type
	                TreeMap<String,Double> Ts_stfs_rur2 =forceRestore.Ts_calc_surf(eng_bals_rur2,cfm,prevTsRef2,prevTmRefForce2, Dats,ref_surf2,i);   // # creates dictionary with surface temperature for current timestep and surface type  
	                
	                double Ts_stfs_rur = Ts_stfs_rur2.get(ForceRestore.TS_KEY); 
	                
	                //### these are the parameters for calculating Tb_rur and httc_rur - may need to add a method for calculating these from 
	                // #  canopy displacement height (m)
	                double dcan_rur = 0.01;  
	                // #  Roughness lenght for momentum (m)
	                double z0m_rur = 0.45;
	                // #  Roughness lenght for heat (m)
	                double z0h_rur = z0m_rur/10.;

	                double z_Hx3 = maxW * 3.0; // # height of Tb (3 x max building height)  -  this is the height of the main Tb value 
	                double z_Hx2  = maxH * 2.0; // # height of Tb (2 x max building height) - this is the secondary height used for Tb above canyon 
	                
	                double z_TaRef = Constants.cs_z_TaRef;  // # height of air temperature measurements (usually 2 m)
	                double z_Uref  = Constants.cs_z_URef;   // # height of reference wind speed measurement (usually 10 m)
	                
	                double Tlow_surf = Ts_stfs_rur ;      // # surface temperature at rural (reference) site
	                
	                double ref_ta = metTa0;       // # observed air temperature
	                
	                //####### DEFINE REFERENCE WIND SPEED RURAL #######
	                double uTopHeightMinimumValue = 0.1;
	                double uTopHeight = Math.max(metWS0 * ((Math.log(z_TaRef/z0m_rur))/(Math.log(z_Uref/z0m_rur))),uTopHeightMinimumValue);  //## convert to wind speed for Utop height using log profile.         CHECK initial extroplation is based on log profile (alternative is to use Fm from prev time step)
	                mod_U_TaRef[i]=uTopHeight;
//	                System.out.println("uTopHeight " + " " +  metWS0 + " " +z_TaRef + " " + z0m_rur + " " +z_Uref+ " " + z0m_rur  + " " + uTopHeight);
	                
	                //####### calculate Richardson's number and heat transfer coefficient for rural site 
	                double Ri_rur = sfcRi.sfc_ri(z_TaRef-z0m_rur,ref_ta,Tlow_surf,mod_U_TaRef[i]);   //## calculate Richardson's number for Rural site   
//	                System.out.println("Ri_rur " + " " +z_TaRef+ " " +z0m_rur+ " " +ref_ta+ " " +Tlow_surf+ " " +mod_U_TaRef[i] + " " + Ri_rur);
//	                if (Ri_rur > 5 || Ri_rur < -5)
//	                {
//	                	System.out.println("bad value for Ri_rur of " + Ri_rur + " changing to previous value of "  + Ri_rur_prev);
//	                	Ri_rur = Ri_rur_prev;
//	                }
//	                else 
//	                {
//	                	Ri_rur_prev = Ri_rur;
//	                }
	                TreeMap<String,Double> httc_rural = httc.httc(Ri_rur,mod_U_TaRef[i],z_TaRef-z0m_rur,z0m_rur,z0h_rur, met_d,i,Tlow_surf,ref_ta) ;      //## calculate httc for Rural site           
	                double httc_rur = httc_rural.get(Httc.HTTC_KEY);
	                
	                //###### sensible heat flux
	                //#Qh_ = httc_rur*(Tlow_surf-ref_ta)
	                
	               // ###### calculate cd, fm, ustar used for calcuating wind speed    
	                TreeMap<String,Double> cd_out =  cd.cd(Ri_rur,z_TaRef-z0m_rur,z0m_rur,z0h_rur) ; 
//	                System.out.println("cd_out " + Ri_rur + " " + (z_TaRef-z0m_rur) + " " + z0m_rur + " " + z0h_rur + " " + cd_out.toString());
	                double modFmI = cd_out.get(CD.FM_KEY);
	                mod_fm[i]=modFmI;
	                double modCdI = cd_out.get(CD.CD_OUT_KEY); 
	                mod_cd[i]=modCdI;	                
	                double ustar=Math.sqrt(mod_cd[i]) * Math.max(mod_U_TaRef[i],0.1) ;  
//	                System.out.println("ustar " + mod_cd[i] + " " + mod_U_TaRef[i] + " " + ustar);	                
	                //###### calculate wind speed at Tb height (3 x H) -- accounts for stability (unlike log profile)
	                double UTb=Math.max(ustar/Constants.cs_karman*Math.log(z_Hx2/z0m_rur)/Math.sqrt(mod_fm[i]),0.1) ;
//	                System.out.println("UTb " + ustar + " " + z_Hx2 + " " + z0m_rur + " " + mod_fm[i] + " " + UTb);
	                //## convert to wind speed for Utop height using log profile.    
	                
	                //###### Solve Richardson's number eq for "high temperature" aka Tb_rur 
	                double dz = z_Hx2 - z_TaRef ;
//	                System.out.println("dz + ref_ta + UTb + mod_U_TaRef[i] + i + Ri_rur = "   + dz + " " + ref_ta + " " + UTb + " " + mod_U_TaRef[i] + " " + i + " " + Ri_rur);
//	                System.out.println("dz"+dz);
//	                System.out.println("ref_ta"+ref_ta);
//	                System.out.println("UTb"+UTb);
//	                System.out.println("mod_U_TaRef[i]"+mod_U_TaRef[i]);
//	                System.out.println("Ri_rur"+Ri_rur);
//	                System.out.println("i"+i);
//	                System.out.println("met_data_all.get(i)"+met_data_all.get(i));
	                //System.exit(1);
	                
	                double Tb_rur ;
	                
	                	                
	                tbRurSolver.setWorkingDirectory(this.workingDirectory);
	                Tb_rur = tbRurSolver.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
	                
	                if (Tb_rur == TbRurSolver.ERROR_RETURN || Tb_rur == 0.0)
	                {
//	                	System.out.println("Error with Tb_rur");
	                	Tb_rur = Tb_rur_prev;
	                	System.out.println("using previous Tb_rur=" + Tb_rur_prev);
	                	//System.exit(1);
	                }
	                else
	                {
//	                	System.out.println("Tb_rur=" + Tb_rur);
	                	Tb_rur_prev = Tb_rur;
	                }
	                
	                //this is the java version of the convergence. Mostly works but not for every case. So, disable for now.
//	                Tb_rur = tbRurSolverOld.converge(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
//	                System.out.println("Tb_rurOld=" + Tb_rur);
//	                if (Tb_rur == TbRurSolver.ERROR_RETURN || Tb_rur == 0.0)
//	                {
//	                	System.out.println("Error with java Tb_rur");
//	                	Tb_rur = Tb_rur_prev;
//	                }
	                
	                Tb_rur = Tb_rur - 9.806/1004.67*dz;
//	                System.out.println("Tb_rur after=" + Tb_rur);
	        	                
	                //# always use iterative solution for rural Tb
	                
	                //###### Begin calculating modelled variables for 10 different SVF values... 
	                for (int vf=0;vf<=10;vf++)
	                {
	                    double svfg = (vf+1)/10.0;
	                    for (String surf : surfs)
	                    {
	                    	// # cycle through surface type for current timestep
	                    	if ( !(surf.equals(WATR_KEY) || surf.equals(VEG_KEY)) )
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
	                    			prevTsRef.add(mod_data_ts_[i-1][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                    		}
	                    		else if (i<3)
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
	                            //TODO I think this one will crash
	                            mod_data_ts_[i][vf][getSurfIndex("TSOIL")] = wtr_stf.get(TsEbW.TSOIL_KEY);
	                            mod_data_rn_[i][vf][getSurfIndex(surf)] = rad.get(RnCalcNew.RN_KEY) ; 
	                        }
	                        //# if (surf == 'Veg'):
//	                            # rad  = rn_calc_new(cs,cfM,met_d,surf,Dats,mod_data_ts_[:,vf],i,svfg)  # creates dictionary with radiation variables for current timestep and surface type  
//	                            # ##################### ENG BALANCE tree #######################
//	                            # eng_bals=LUMPS(rad,cs,cfM,met_d,surf,Dats,i)            # creates dictionary with energy balance for current timestep and surface type
//	                            # #####################  LST tree #########################
//	                            # mod_data_qh_[i][vf]['Veg'] = eng_bals['Qh']
//	                            # mod_data_qe_[i][vf]['Veg'] = eng_bals['Qe']
//	                            # mod_data_qg_[i][vf]['Veg'] = eng_bals['Qg']
//	                            # mod_data_rn_[i][vf]['Veg'] = rad['Rn']
	                    }
	                }
	                     
	                int counter=-1;
	                
	                //previousTacValues
	                ArrayList<Double> timestepsTacValues = new ArrayList<Double>();
	                
	                //# now cycle through each grid point
	                for (int grid=0;grid< lc_data.size();grid++)
	                {
	                    counter+=1;
	                    long t0 = System.currentTimeMillis();
	                    TreeMap<Integer,Double> for_tab = calcLoop(lc_data,grid,counter,i,met_d,mod_data_ts_
	                    		,cfm,z_Uref,z_Hx2,Tb_rur,dte,mod_U_TaRef[i],UTb,previousTacValues, httc_rur) ;         
//	                    System.out.println(for_tab.toString());
	                    long t1 = System.currentTimeMillis();
	                    //mod_rslts_prev.add(for_tab.get(FOR_TAB_Tac_INDEX));
//	                    System.out.println("for_tab.get(FOR_TAB_Tac_INDEX)="+for_tab.get(FOR_TAB_Tac_INDEX));
	                    timestepsTacValues.add(for_tab.get(FOR_TAB_Tac_INDEX));
	                    mod_rslts.add(for_tab);

	                    // # need yd_actual, TM, lat
	                    // # hardcoded for now
	                    //TODO
	                    //double lat = -37.8136;
	                    double lat = latEdge;
	                    
	            	    Calendar calendar = Calendar.getInstance();
	            	    calendar.setTime(dte);	
	            	    int yd_actual = calendar.get(Calendar.DAY_OF_YEAR);
	            	    int TM = calendar.get(Calendar.HOUR_OF_DAY);
	                    
	                    double tmrt;
	                    double utci;
	                    double Tac = for_tab.get(FOR_TAB_Tac_INDEX);
	                    double Ucan = for_tab.get(FOR_TAB_Ucan_INDEX);
	                    double Tsurf_can = for_tab.get(FOR_TAB_Tsurf_can_INDEX);
	                    if (Tac == -999.0)
	                    {
	                        tmrt = -999.0;
	                        utci = -999.0;
	                    }
	                    else
	                    {
	                        	                        
	                        double lup = Constants.cs_sb*Math.pow((metTa0  +273.15),4);
	                        
	                        tmrt = utciInstance.getTmrtForGrid_RH(Tac,metRH0,Ucan,metKd0,Tsurf_can,metLD0, lup ,yd_actual, TM, lat);
	                                          
	                        utci = utciInstance.getUTCIForGrid_RH(Tac,Ucan,metRH0,tmrt);
	                        
//	                        System.out.println("getTmrtForGrid_RH "+ grid+ " " + i + " " + tmrt+ " " + Tac + " " + metRH0 + " " + Ucan + " " + metKd0 + " " 
//	                        + Tsurf_can + " " + metLD0 + " " + lup + " " + yd_actual + " " + TM + " " + lat);
//	                        System.out.println("getUTCIForGrid_RH " + utci +" " + Tac + " " + Ucan + " " + metRH0 + " " + tmrt );	      
	                    }
	    	                    
	                	TreeMap<Integer,Double> for_tab_tmrt_utci = new TreeMap<Integer,Double>();
	                	double fid = (double)lc_data.get(grid).get(LCData.FID);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_FID_INDEX,fid);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_tmrt_INDEX,tmrt);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_utci_INDEX,utci);
	                	double dteDouble = (double)dte.getTime();
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_dte_INDEX,dteDouble);
	                	mod_rslts_tmrt_utci.add(for_tab_tmrt_utci);	          
	                }
	                
	                previousTacValues.add(timestepsTacValues);
	                NetCdfOutput netCdfOutput = new NetCdfOutput();
	                netCdfOutput.setDisabled(disableOutput);
	                netCdfOutput.outputNetcdf2(outputFile, x, y, mod_rslts, mod_rslts_tmrt_utci,i,tmstpInt,date1ADateStr,
	                		latEdge, latResolution, lonEdge, lonResolution);
	            }
     
                long t11 = System.currentTimeMillis();
//                System.out.println("int i=0;i<met_data_all.size() loop takes " + (t11-t10) + " " + new Date(System.currentTimeMillis()));
	            
	        }	                        
	}                 



	     


public TreeMap<Integer,Double> calcLoop(ArrayList<ArrayList<Double>> lc_data,int grid,int counter,int i,ArrayList<ArrayList<Object>> met_d,
		double[][][] mod_data_ts_,Cfm cfm, double z_Uref, double z_Hx2, double Tb_rur,
		Date dte, double mod_U_TaRef, double UTb, 
		ArrayList<ArrayList<Double>> mod_rslts_prev, 
		double httc_rur)
{
	ArrayList<Object> met0 = met_d.get(i);	
	double metTa0 = (double)met0.get(MetData.Ta);
	double metKd0 = (double)met0.get(MetData.Kd);
	double metWS0 = (double)met0.get(MetData.WS);
	double metRH0 = (double)met0.get(MetData.RH);
	   

    //# create dictionary for raw LC inputs
    double H      = lc_data.get(grid).get(LCData.H);     //  # building height for grid point
    double W      = lc_data.get(grid).get(LCData.W);      // # street width for grid point 
    //## create dictionary with land cover stuff
    LcSort lcSort = new LcSort();
    ArrayList<Double> LC_orig = lc_data.get(grid);
//    System.out.println("LC="+LC_orig.toString());

    ArrayList<Double> LC = new ArrayList<Double>(Arrays.asList(new Double[LC_orig.size()]));
	Collections.copy(LC, LC_orig);
   
    TreeMap<String,Object> lc_stuff = lcSort.lc_sort(LC,H,W);
    //System.out.println(lc_stuff.toString());
//    System.out.println(LC_orig.toString());
    //System.exit(1);
   
    double Wtree  = (double) lc_stuff.get(LcSort.Wtree_KEY);        // # street width (including area below trees) for grid point 
        
    //# define surface temperature of trees as canyon air temperature     
    if (! (i == 0))
    {
    	//System.out.println(i + " " + grid);
//    	System.out.println(mod_rslts_prev.toString());
        //mod_data_ts_[i][9][getSurfIndex("Veg")] = mod_rslts_prev.get(grid);
    	//ArrayList<Double> prevTimestepValues = mod_rslts_prev.get(i-1);
    	//double tacForPrevTimestepGrid = prevTimestepValues.get(grid);
    	//System.out.println(tacForPrevTimestepGrid);
    	//mod_data_ts_[i][9][getSurfIndex("Veg")] = tacForPrevTimestepGrid; 
    	mod_data_ts_[i][9][getSurfIndex("Veg")] = mod_rslts_prev.get(i-1).get(grid);
        
    	//mod_data_ts_[i][getSurfIndex("Veg")][9] = mod_rslts_prev.get(grid);

//        System.out.println(mod_rslts_prev.get(i-1).get(grid));
//        System.exit(1);
    }
    else
    {
        mod_data_ts_[i][9][getSurfIndex("Veg")] = metTa0;
    	//mod_data_ts_[i][getSurfIndex("Veg")][9] = metTa0;
//        System.out.println(metTa0);
//        System.exit(1);
    }
            
    
    //### below various land cover dictionaries are defined.
    LC  = (ArrayList<Double>) lc_stuff.get(LcSort.LC_KEY);    
    //  # all surfaces not averaged (can be > 1.0)
    ArrayList<Double> LC_wRoofAvg   = (ArrayList<Double>) lc_stuff.get(LcSort.LC_wRoofAvg_KEY);        
    // # average over the total 3D surface (will be = 1.0)
    
//    System.out.println("LC,2="+LC.toString());
//    System.exit(1);

    double svfgA = Math.pow((1.0+Math.pow((H/Wtree),2)),0.5) - H/Wtree ;     
    // # average SVF of the ground
    double svfwA = 1./2.*(1.+W/H-Math.pow((1.+Math.pow((W/H),2.)),0.5)) ;    
    // # average SVF of the wall

     
    int fw   = (int) lc_stuff.get(LcSort.fw_KEY);     
    //  # integer used to indicate relevant wall SVF - this doesn't get used any more - Ts for walls is stored in "mod_data_ts_[i][9]['wall']"
    int fg   = (int) lc_stuff.get(LcSort.fg_KEY);     
    //  # integer used to indicate relevant ground SVF

//    if (cfm.getValue("use_obs_ws").equals("Y"))
//    {
//        obs_ws = obs_data_all['WS_ms_Avg_'+cfm['STa'][counter]][i];    
    // # observed wind speed file  | users will not use observed wind speed, this is just for testing
//    }
//    else
//    {
//        obs_ws = []; // # create an empty list for obs wind speed  - place filler
//    }

    
    //## convert to wind speed for, top of the canopy, using log profile.
    double Hz  = Math.max(H, Constants.cs_zavg);
    double z0m_urb = 0.1 * Hz;  // ## urban roughness length momentum [CHECK]
    double z0h_urb = z0m_urb/10.0; // ##  urban roughness lenght heat [CHECK]

    double Uz = Math.max(metWS0 * ((Math.log(Hz/z0m_urb))/(Math.log(z_Uref/z0m_urb))),0.1);
    //## calculate wind speed in the canyon - I used the wind speed from log profile here not the "stability" calculated wind speed
    double lcStuffWTree = (double) lc_stuff.get(LcSort.Wtree_KEY);
    double Ucan     = Uz*Math.exp(-0.386*(Hz/lcStuffWTree)); // # [CHECK] which forcing wind speed height should we use for this? currently using 3xH                
    double rs_can   = (Constants.cs_pa*Constants.cs_cpair)/(11.8+(4.2*Ucan));		//## calculate surface resistance (s/m)
    double httc_can = 1.0/rs_can ;                                          //## heat transfer coefficient for the canyon 

    double rs_roof = (Constants.cs_pa*Constants.cs_cpair)/(11.8+(4.2*Uz));
    double httc_roof = 1.0/rs_roof;

    
    //##### average Ta above canyon and Ta above roof by canyon and roof fraction
  //# plan area of roof 
    double LCroof = LC.get(LCData.roof);     
  //# plan area of canyon
    double LCcan  = LC.get(LCData.road)+LC.get(LCData.watr)+LC.get(LCData.conc)+LC.get(LCData.Veg)+LC.get(LCData.dry)+LC.get(LCData.irr); 
//    #LCcan  = LC['road']+LC['watr']+LC['conc']+LC['dry']+LC['irr'] //# plan area of canyon
    double LChorz  = LCroof+LCcan;
    //double PlanRoof   =  LCroof/LChorz;
    double PlanCan   = LCcan/LChorz;
    
//    System.out.println ("LC " + " " + LC.get(LCData.road) + " " + LC.get(LCData.watr) + " " + LC.get(LCData.conc) + " " + LC.get(LCData.Veg)
//    + " " + LC.get(LCData.dry) + " " + LC.get(LCData.irr) );
    
    //System.out.println ("PlanCan " + " " + LCroof + " " + LCcan + " " + LChorz + " " + PlanCan);
    //################################

    double Tacprv;
    double roofTsrfT;
    
    int roofIndex = getSurfIndex("roof");
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
    
//    System.out.println("Tac_can_roof calcuation "+ " " + LCroof + " " + LChorz + " " + roofTsrfT + " " + LCcan + " " + Tacprv );
//    System.out.println("grid "+grid);
//    if (grid > 11)
//    {
//    	System.exit(1);
//    }
    
    
    //## calculate Richardson's number for roof  
    double Tac_can_roof = ((LCroof/LChorz)*roofTsrfT) + ((LCcan/LChorz)*Tacprv)  ;  
    //System.out.println("Tac_can_roof="+Tac_can_roof);

    //## calculate httc for Rural site  
    double Ri_urb_new = sfcRi.sfc_ri(z_Hx2-H-z0m_urb,Tb_rur,Tac_can_roof,Uz);  

    TreeMap<String,Double> httcReturn = httc.httc(Ri_urb_new,Uz,z_Hx2-H-z0m_urb,z0m_urb,z0h_urb, met_d,i,Tac_can_roof,Tb_rur); 
//    System.out.println("Ri_urb_new calcuation "+ " " + Ri_urb_new + " " + Uz + " " + z_Hx2 + " " + H + " " + z0m_urb + " " + z0m_urb + " " + z0h_urb 
//    		+ " " +  i + " " + Tac_can_roof + " " + Tb_rur);
//    System.out.println(httcReturn.toString());
    
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
        if (cfm.getValue("direct roofs").equals("Y"))
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
//            System.out.println("direct roofs,TAC="+Tac);
//            System.out.println("LC.get(LCData.road)="+LC.get(LCData.road));
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
//        System.out.println("include roofs,TAC="+Tac);
    }

//    ## we don't calculate Tac if the roof frac is 1.0
    if (LC.get(LCData.roof) > 0.75 )
    {
        Tac = -999.0;
//        Tsurf_horz = mod_data_ts_[i][roofIndex][9]*LcH.get(LCData.roof);
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
        
public static <T> void fillAndSet(int index, T object, List<T> list) 
{
    if (index > (list.size() - 1))
    {
        for (int i = list.size(); i < index; i++)
        {
            list.add(null);
        }
        list.add(object);
    }
    else
    {
        list.set(index, object);
    }
 }
                    
	        		         
	
	
	
	
	
}
