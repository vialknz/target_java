package Target;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class TargetModule
{

//	# -*- coding:utf-8 -*-
//
//	"""
//	This is the main script that runs the toolkit air temperature module 
//
//	Developed by Ashley Broadbent, Andrew Coutts, and Matthias Demuzere.
//
//	This script should be run from the ./scripts directory
//
//	see ./documents/Toolkit-2_tech-description.docx for full description of the module
//
//	These scripts should run in Windows and Linux - however the gis function (arcpy) can't be used in Linux
//
//	Developed in Windows with Python 2.7.12 Anaconda 4.1.1 (32-bit)
//
//	Tested with Python 2.7.9 (Linux)
//
//
//	"""
//	import time
//	from configobj import ConfigObj
//	import pandas
//	import numpy as np
//	import datetime
//	from datetime import timedelta
//	import os
//
//	#import Tkinter, tkFileDialog
//	#from confirm import confirm
//	import math
//	from sympy import solve, Eq, Symbol
//	######################################################
//	import constants2 as cs     # This is the main constants file where constants are defined. Contains dictionary called cs
//	################## functions used by the code
//	from rn_calc_3a import rn_calc_new   # net radiation calcs  (3.1 tech notes)
//	from LUMPS import LUMPS       # energy balance calcs (3.2 tech notes)
//	from force_restore import Ts_calc_surf   # force restore calcs (3.3 tech notes)
//	from simple_water import Ts_EB_W     # simple water body model (3.4 tech notes)
//	from ld_mod import ld_mod            # model ldown (appendix tech notes)
//	from Ta_module_new import calc_ta    # air temperature module (3.5 tech notes)
//	#from plotting import val_ts, val_ta, gis    # Ash Broadbent's plotting functions 
//	from lc_sort import lc_sort          # lc_sorting and SVFs
//	from sfc_ri import sfc_ri # Richardson's number calc
//	from httc import httc # heat transfer coefficient
//	from cd import CD 
//	from utci import getTmrtForGrid_RH,getUTCIForGrid_RH
//	from time import time

	private TreeMap<String,Double> tmrtCache=new TreeMap<String,Double>();
	private TreeMap<String,Double> utciCache=new TreeMap<String,Double>();
	private TreeMap<String,Double> Tb_rurCache=new TreeMap<String,Double>();
	private TreeMap<String,Double> calcLoopCache=new TreeMap<String,Double>();
	private double Tb_rur_prev=21.8093381484548;
	
	private RnCalcNew rnCalcNew = new RnCalcNew();
	private Lumps lumps = new Lumps();
	private ForceRestore forceRestore = new ForceRestore();
	private SfcRi sfcRi = new SfcRi();
	private Httc httc = new Httc();
	private CD cd = new CD();
	private TsEbW tsEbW = new TsEbW();
	private UTCI utciInstance = new UTCI();
	private TbRurSolver tbRurSolver = new TbRurSolver();
	
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
	{{this.add(ROOF_KEY);
	this.add(ROAD_KEY);
	this.add(WATR_KEY);
	this.add(CONC_KEY);
	this.add(VEG_KEY);
	this.add(DRY_KEY);
	this.add(IRR_KEY);
	this.add(WALL_KEY);
	}};
	
//	('wall','<f8'),
//	('roof','<f8'), 
//	('road','<f8'), 
//	('watr','<f8'), 
//	('conc','<f8'), 
//	('Veg','<f8'), 
//	('dry','<f8'), 
//	('irr','<f8'), 
//	('TSOIL','<f8'), 
//	('avg','<f8'),
//	('date',object)]
	
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
	
	
			
	ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_all_timesteps =new ArrayList<ArrayList<TreeMap<Integer,Double>>>();
	ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_tmrt_utci_all_timesteps =new ArrayList<ArrayList<TreeMap<Integer,Double>>>();
    String loc = "/tmp/testWrite.nc";
	int y = 344; //lon
	int x = 235; //lat
	NetCdfOutput netCdfOutput = new NetCdfOutput(loc);


//	######## SELECTS MAIN CONTROL FILE (uses Tkinter package) ####
//	#root = Tkinter.Tk(); root.withdraw()    
//	#ControlFileName = tkFileDialog.askopenfilename()
//	#root.destroy()
//	#cfM = ConfigObj(ControlFileName)  ### This is the dictionary that contains all the control file information 
//	##############################################################


   

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
			double maxH, double maxW, int xDim, int yDim)
	{
		ArrayList<Double> mod_rslts_prev = new ArrayList<Double>();
		
	    //dateparse = lambda x: pandas.datetime.strptime(x, cfM['date_fmt'])      // # parse dates for input met file using format defined in control file
		String run = cfm.getValue("run_name");                           // # model run name 
		String tmstp = cfm.getValue("timestep");                            // # time step (minutes)
		int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
//	    ######### DEFINE START AND FINISH DATES HERE ########
		Date date1A = cfm.getDateValue("date1A");
//		date1A=datetime.datetime(int(cfM['date1A'][0]), int(cfM['date1A'][1]),int(cfM['date1A'][2]), int(cfM['date1A'][3]))  // ## the date/time that the simulation starts
		Date date1 = cfm.getDateValue("date1");
//		date1=datetime.datetime(int(cfM['date1'][0]), int(cfM['date1'][1]),int(cfM['date1'][2]), int(cfM['date1'][3]))      //  ## the date/time for period of interest (i.e. before this will not be saved)
		Date date2 = cfm.getDateValue("date2");
		System.out.println(cfm.getDateValue("date2"));
//		date2=datetime.datetime(int(cfM['date2'][0]), int(cfM['date2'][1]),int(cfM['date2'][2]), int(cfM['date2'][3]))       // ## end date/time of simulation 
		long tD = (date2.getTime() - date1A.getTime()) ;  // to days    / (1000 * 60 * 60 * 24)
		System.out.println(tD);
//		tD = date2-date1A   //## time difference between start and end date
//		nt=divmod(tD.days * 86400 + tD.seconds, (int(tmstp[:-1])))[0] //# number of timesteps
		long ntLong = (tD / 1000) / tmstpInt ;
		int nt = (int) ntLong -1 ;
		System.out.println(nt);
//		date_range = pandas.date_range(date1,date2,freq= tmstp)              // #  date range for model period 
//		date_range1A =pandas.date_range(date1A,(date2-timedelta(hours=1)),freq= tmstp) //# date range for model period (i.e. including spin-up period)
	    //Dats={'date1A':date1A, 'date1':date1, 'date2':date2, 'date_range':date_range,'date_rangeA':date_range1A} // # this is a dictionary with all the date/time information 
//		TreeMap<String,Date> Dats = new TreeMap<String,Date>();
		

		int timedelta = tmstpInt*1000;
		

//	    #########  MAIN PROGRAM BEGINS HERE ###################
	    //if (True):
	    //#if confirm('This run will be called: '+run) == True:    // # prints the run name to screen, user has to input "Y" to begin simulation 

//	        ########## LC GRID FILE #############################
	        //if cfM['gis_plot'] == 'Y':
	         //   if not os.path.exists(os.path.join('..','GIS',cfM['site_name'],'mod',run)):          
	        //        os.makedirs(os.path.join('..','GIS',cfM['site_name'],'mod',run))      //## creates a directory to output at GIS grid (shapefile) | gets used in plotting.py

//	        ########## DEFINE FIG DIR ########################### 
	        //figdir= os.path.join('..','plots',cfM['site_name'],run)       //## defines a director for outputing plots | only gets used if validating air temp (plotting.py)
	        //if not os.path.exists(figdir):          
	        //        os.makedirs(figdir)

//	        ################# read LC data  #####################        
//	#        lc_data = pandas.read_csv(os.path.join('..','input',cfM['site_name'],'LC',cfM['inpt_lc_file'])) # reads the input land cover data
//	#        avg_lc_data = np.mean(lc_data)
	                
//	        #LC_rur = [0.0,0.0, 0.0,0.0,0.0,1.0,0.0]  # list with land cover for grid point               
//	        #fw_rur   = 9
//	        #fg_rur   = 9
	            
	        //############## OBS AWS DATA files  #############################
	// #        if cfM['val_ts'] == 'Y':
	// #            obs_file = os.path.join('..','obs',cfM['site_name'],'stations_MET',cfM['inpt_obs_file'])  # file for observed AWS data
	// #            obs_data = pandas.read_csv(obs_file,parse_dates =['TIMESTAMP'], date_parser=dateparse, index_col=['TIMESTAMP'])  # reads observed AWS data and puts in dataframe  | only gets used if validating air temp (plotting.py)
	// #            obs_data_all = obs_data[date1A:date2]
	// #        ########## DEFINE INPUT MET FILE LOCATION HERE #######
	// #        met_file = os.path.join('..','input',cfM['site_name'],'MET',cfM['inpt_met_file'])    # input meteorological forcing data file 
	// #        met_data = pandas.read_csv(met_file,parse_dates=['datetime'], date_parser=dateparse,index_col=['datetime']) # convert to data frame
	// #        met_data= met_data.resample(tmstp).ffill() # interpolates forcing data 
	// #        met_data_all = met_data.ix[date1A:date2]   # main forcing meteorological dataframe (including spin up)
	// #        #met_data_all = met_data_all.interpolate(method='time') # interpolates forcing data 
	// #        if cfM['mod_ldwn'] == 'Y':                                          # model Ldown in data is not available
	// #            for i in range(len(met_data_all)):
	// #                met_data_all.ix[i]['Ld'] = ld_mod(met_data_all.ix[i])['Ld_md'] ## Ld_mod is added to meteorological forcing data frame
	//#
	        //########## DEFINE MAIN DATAFRAME ####################  dataframe for different modelled variables     
		
		//outer ArrayList, index 0-10 vf, inner ArrayList index with i, the met data, 
		
		// [i, the amount of met data][vf][number of surface types]
		double[][][] mod_data_ts_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		//ArrayList<ArrayList<TreeMap<String,Double>>> mod_data_ts_ = new ArrayList<ArrayList<TreeMap<String,Double>>>(); 
//		ArrayList<TreeMap<String,Double>> mod_data_tm_ = new ArrayList<TreeMap<String,Double>>();
//		ArrayList<TreeMap<String,Double>> mod_data_qh_ = new ArrayList<TreeMap<String,Double>>();
//		ArrayList<TreeMap<String,Double>> mod_data_qe_ = new ArrayList<TreeMap<String,Double>>();
//		ArrayList<TreeMap<String,Double>> mod_data_qg_ = new ArrayList<TreeMap<String,Double>>();
//		ArrayList<TreeMap<String,Double>> mod_data_rn_ = new ArrayList<TreeMap<String,Double>>();
		double[][][]  mod_data_tm_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][]  mod_data_qh_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][]  mod_data_qe_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][]  mod_data_qg_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		double[][][]  mod_data_rn_ = new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
		
		
//	        mod_data_ts_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))   // # surface temperature of each surface    
//	        mod_data_tm_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))   // # ground temperature of each surface
//	        mod_data_qh_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))   // # sensible heat flux of each surface
//	        mod_data_qe_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))    // # latent heat flux of each surface
//	        mod_data_qg_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))    // # storage heat flux of each surface
//	        mod_data_rn_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))    // # net radiation of each surface
	        //## NB: "TSOIL" is the soil temperature below the water layer
	        
	//#        if cfM['gis_plot'] == 'Y':
	//#            mod_rslts=np.zeros((nt,len(lc_data),1),  np.dtype([('ID',np.int32),('Ws', '<f8'), ('Ts','<f8'), ('Ta','<f8'),('date',object)]))   # this is the main data array where surface averaged outputs are stored
	//#        else:
	//#            mod_rslts=np.zeros((nt,len(lc_data),1),  np.dtype([('ID',np.int32),('Ws', '<f8'), ('Ts','<f8'), ('Tsurf_can','<f8'),('Tsurf_wall','<f8'), ('Ta','<f8'),('date',object),('httc_urb', '<f8'), ('httc_can','<f8'), ('Tb_rur','<f8'),('mod_U_TaRef','<f8'), ('UTb','<f8')]))   # this is the main data array where surface averaged outputs are stored
	    
		double[] mod_fm = new double[nt];
		double[] mod_cd = new double[nt];
		double[] mod_U_TaRef = new double[nt];
		
		
//	        mod_fm = np.zeros((nt,1))
//	        mod_cd = np.zeros((nt,1))
//	        mod_U_TaRef = np.zeros((nt,1))
	        //#Mod_Tsurf_horz
	    
	        
	            
	        //stations = lc_data['FID'].values
	        //hk=-1
	        
	        
	        //ArrayList<Double> mod_rslts_prev=ArrayList<Double>();
	        
	        // # begin looping through the met forcing data file
	        //for (int i=0;i<met_data_all.size();i++)
		    for (int i=0;i<nt;i++)
	        //for i in range(0, len(met_data_all))
	        {
                long t10 = System.currentTimeMillis();
                

	        	
	            if (! (i == met_data_all.size()-1))
	            {
	            	
	            	ArrayList<Object> met0 = met_data_all.get(i);	
	            	double metTa0 = (double)met0.get(MetData.Ta);
	            	double metKd0 = (double)met0.get(MetData.Kd);
	            	double metWS0 = (double)met0.get(MetData.WS);
	            	double metRH0 = (double)met0.get(MetData.RH);
	                
	            	ArrayList<TreeMap<Integer,Double>> mod_rslts =new ArrayList<TreeMap<Integer,Double>>();
	                //mod_rslts=np.zeros((1,len(lc_data),1),  np.dtype([('ID',np.int32),('Ws', '<f8'), ('Ts','<f8'), ('Tsurf_can','<f8'),('Tsurf_wall','<f8'), ('Ta','<f8'),('date',object),('httc_urb', '<f8'), ('httc_can','<f8'), ('Tb_rur','<f8'),('mod_U_TaRef','<f8'), ('UTb','<f8')]))   // # this is the main data array where surface averaged outputs are stored
	                
	            	ArrayList<TreeMap<Integer,Double>> mod_rslts_tmrt_utci =new ArrayList<TreeMap<Integer,Double>>();
	                //mod_rslts_tmrt_utci=np.zeros((1,len(lc_data),1),  np.dtype([('ID',np.int32),('tmrt', '<f8'), ('utci','<f8'),('date',object)]))
	                //############ Met variables for each time step (generate dataframe) ##########
	                Date dte   = date1A;
	                dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	                Dats.put("dte", dte);
	                ArrayList<ArrayList<Object>> met_d = met_data_all ;
	                System.out.println(dte + " " + i);
	                //print dte, i
	                
	                //## BEGIN CALCULATION OF Tb_rur
	                String ref_surf1 = LCData.DRY_SURF;
	                String ref_surf2 = LCData.CONC_SURF;
	                //## radiation balance
	                
	                ArrayList<Double> prevTsRef1 = new ArrayList<Double>();	
	                ArrayList<Double> prevTsRef2 = new ArrayList<Double>();	
	                ArrayList<Double> prevTmRefForce1 = new ArrayList<Double>();
	                ArrayList<Double> prevTmRefForce2 = new ArrayList<Double>();
	                if (i < 3)
	                {
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                prevTsRef1.add(0.);
		                
		                prevTsRef2.add(0.);
		                prevTsRef2.add(0.);
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
	                System.out.println("uTopHeight " + " " +  metWS0 + " " +z_TaRef + " " + z0m_rur + " " +z_Uref+ " " + z0m_rur  + " " + uTopHeight);
	                
	                //####### calculate Richardson's number and heat transfer coefficient for rural site 
	                double Ri_rur = sfcRi.sfc_ri(z_TaRef-z0m_rur,ref_ta,Tlow_surf,mod_U_TaRef[i]);   //## calculate Richardson's number for Rural site   
	                System.out.println("Ri_rur " + " " +z_TaRef+ " " +z0m_rur+ " " +ref_ta+ " " +Tlow_surf+ " " +mod_U_TaRef[i] + " " + Ri_rur);
	                if (Ri_rur > .25)
	                {
	                	System.out.println("bad value for Ri_rur of " + Ri_rur + " changing to 0.25");
	                	Ri_rur = 0.25;
	                }
	                TreeMap<String,Double> httc_rural = httc.httc(Ri_rur,mod_U_TaRef[i],z_TaRef-z0m_rur,z0m_rur,z0h_rur, met_d,i,Tlow_surf,ref_ta) ;      //## calculate httc for Rural site           
	                double httc_rur = httc_rural.get(Httc.HTTC_KEY);
	                
	                //###### sensible heat flux
	                //#Qh_ = httc_rur*(Tlow_surf-ref_ta)
	                
	               // ###### calculate cd, fm, ustar used for calcuating wind speed    
	                TreeMap<String,Double> cd_out =  cd.cd(Ri_rur,z_TaRef-z0m_rur,z0m_rur,z0h_rur) ; 
	                System.out.println("cd_out " + Ri_rur + " " + (z_TaRef-z0m_rur) + " " + z0m_rur + " " + z0h_rur + " " + cd_out.toString());
	                double modFmI = cd_out.get(CD.FM_KEY);
	                mod_fm[i]=modFmI;
	                double modCdI = cd_out.get(CD.CD_OUT_KEY); 
	                mod_cd[i]=modCdI;	                
	                double ustar=Math.sqrt(mod_cd[i]) * Math.max(mod_U_TaRef[i],0.1) ;  
	                System.out.println("ustar " + mod_cd[i] + " " + mod_U_TaRef[i] + " " + ustar);	                
	                //###### calculate wind speed at Tb height (3 x H) -- accounts for stability (unlike log profile)
	                double UTb=Math.max(ustar/Constants.cs_karman*Math.log(z_Hx2/z0m_rur)/Math.sqrt(mod_fm[i]),0.1) ;
	                System.out.println("UTb " + ustar + " " + z_Hx2 + " " + z0m_rur + " " + mod_fm[i] + " " + UTb);
//	                #UTb=max(met_d['WS'][i] * ((math.log(z_Hx2/z0m_rur))/(math.log(z_Uref/z0m_rur))),0.11)  //## convert to wind speed for Utop height using log profile.    
	                
	                //double Tb_rur = Tb_rur_prev;
	                //###### Solve Richardson's number eq for "high temperature" aka Tb_rur 
//	                String Thi_tb = Symbol('Thi_tb')
	                double dz = z_Hx2 - z_TaRef ;
//	                
//	                Tb_rurCacheKey=str(dz) + '_' + str(ref_ta) + '_' + str(UTb) + '_' + str(mod_U_TaRef[i]) + '_' + str(Ri_rur)
//	                t2 = time()
////	                #str(Tac)+ str(met_d['RH'][i])+ str(Ucan)+ str(met_d['Kd'][i])+ str(Tsurf_can)+ str(met_d['Ld'][i])+ str(lup)+ str(yd_actual)+ str(TM)+ str(lat)
//	                if Tb_rurCacheKey in Tb_rurCache
//	                {
//	                    Tb_rur = Tb_rurCache[Tb_rurCacheKey]
//	                    print (Tb_rurCacheKey,'=',Tb_rur)
//	                }
//	                else
//	                {
//	                    try
//	                    {
//	                double Tb_rur_itr = 9.806*dz*(Thi_tb-ref_ta)*2.0/(Thi_tb+ref_ta)/Math.pow((UTb-mod_U_TaRef[i]),2.0)-Ri_rur;
	                
 //	                        Tb_rur = solve(9.806*dz*(Thi_tb-ref_ta)*2.0/(Thi_tb+ref_ta)/(UTb-mod_U_TaRef[i])**2.0-Ri_rur, Thi_tb,quick=True)[0]
//	                        Tb_rur_prev = Tb_rur
//	                    }
//	                    except ValueError
//	                    {
//	                        print ('Error calculating Tb_rur',9.806*dz*(Thi_tb-ref_ta)*2.0/(Thi_tb+ref_ta)/(UTb-mod_U_TaRef[i])**2.0-Ri_rur, ' using 21.795 instead')
//	                        print (Tb_rurCacheKey)
//	                        Tb_rur = Tb_rur_prev
//	                    }
//	                    print ('Tb_rur=',Tb_rur,i)
//	                                        
//	                    Tb_rurCache[Tb_rurCacheKey] = Tb_rur
//	                    print ('calculated Tb_rur=',Tb_rur)
//	                }
//	               
//	                t3 = time() 
//	                print 'function Tb_rur takes %f' %(t3-t2)
//	                

//	                Tb_rur = Tb_rur - 9.806/1004.67*dz;
	                System.out.println("dz + ref_ta + UTb + mod_U_TaRef[i] + i + Ri_rur = "   + dz + " " + ref_ta + " " + UTb + " " + mod_U_TaRef[i] + " " + i + " " + Ri_rur);
	                double Tb_rur = tbRurSolver.converge(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
	                if (Tb_rur == TbRurSolver.ERROR_RETURN)
	                {
	                	System.out.println("Error with Tb_rur");
	                	Tb_rur = 21.0;
	                	//System.exit(1);
	                }
	                
	                //# always use iterative solution for rural Tb
	                
	                //###### Begin calculating modelled variables for 10 different SVF values... 
	                //t4 = time()    
	                for (int vf=0;vf<=10;vf++)
	                {
	                //for vf in range(0,10):
	                    double svfg = vf+1/10.0;
	                    for (String surf : surfs)
	                    {
	                    //for surf in surfs:      // # cycle through surface type for current timestep
	                    //#################### radiation balance non-water ###########################
	                    	if ( !(surf.equals(WATR_KEY) || surf.equals(VEG_KEY)) )
	                        //if (! (surf in ['watr','Veg']))
	                        {
	                    		ArrayList<Double> prevTsRef = new ArrayList<Double>();	
	                    		if (i<3)
	                    		{
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
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
	        	                
	                    		
	                    		TreeMap<String,Double> rad = rnCalcNew.rn_calc_new(cfm,met_d,surf,Dats,prevTsRef,i,svfg);  // # creates dictionary with radiation variables for current timestep and surface type                             
	                            //##################### ENG BALANCE non-water #######################
	                            TreeMap<String,Double> eng_bals=lumps.lumps(rad,cfm,met_d,surf,Dats,i);            // # creates dictionary with energy balance for current timestep and surface type
	                            //##################### CALC LST non-water #########################

	                            TreeMap<String,Double> Ts_stfs =forceRestore.Ts_calc_surf(eng_bals,cfm,prevTsRef,prevTmRefForce, Dats,surf,i);   // # creates dictionary with surface temperature for current timestep and surface type
	                           // ################################################################################
	                            //### append modelled data to dataframes below... 
	                            //System.out.println(surf + "=" + getSurfIndex(surf) + " " + ForceRestore.TS_KEY + " " + Ts_stfs.get(ForceRestore.TS_KEY) + " " + i + " " + vf);
	                           
	                            mod_data_ts_[i][vf][getSurfIndex(surf)] = Ts_stfs.get(ForceRestore.TS_KEY);
	                            mod_data_tm_[i][vf][getSurfIndex(surf)] = Ts_stfs.get(ForceRestore.TM_KEY) ;                
	                            mod_data_qh_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QH_KEY);
	                            mod_data_qe_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QE_KEY);
	                            mod_data_qg_[i][vf][getSurfIndex(surf)] = eng_bals.get(Lumps.QG_KEY);
	                            mod_data_rn_[i][vf][getSurfIndex(surf)] = rad.get(RnCalcNew.RN_KEY);
	                        }
	                        if (surf.equals(WATR_KEY))
	                        //if (surf == 'watr')
	                        {
	                        	ArrayList<Double> prevTsRef = new ArrayList<Double>();	
	                        	if (i < 3)
	                        	{
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
		        	                prevTsRef.add(0.);
	                        	}
	                        	else
	                        	{
		        	                prevTsRef.add(mod_data_ts_[i-1][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-2][9][getSurfIndex(surf)]);
		        	                prevTsRef.add(mod_data_ts_[i-3][9][getSurfIndex(surf)]);
	                        	}

	        	                
	        	                
	                        	TreeMap<String,Double> rad  = rnCalcNew.rn_calc_new(cfm,met_d,surf,Dats,prevTsRef,i,svfg);  // # creates dictionary with radiation variables for current timestep and surface type                             
	                        	TreeMap<String,Double> wtr_stf = tsEbW.ts_EB_W(met_d,cfm,mod_data_ts_,Dats,i,rad,vf); // # creates dictionary with water surface temperature and energy balance 
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
	                //t5 = time() 
	                //print 'function for vf takes %f' %(t5-t4)
	                            
	                int counter=-1;
	                
	                //# now cycle through each grid point
	                //t6 = time()           
	                for (int grid=0;grid< lc_data.size();grid++)
	                //for grid in range(0,len(lc_data))
	                {
	                    //hk=+1;
	                    counter+=1;
	                    
	                    
	                    long t0 = System.currentTimeMillis();
	                    TreeMap<Integer,Double> for_tab = calcLoop(lc_data,grid,counter,i,met_d,mod_data_ts_,cfm,z_Uref,z_Hx2,Tb_rur,dte,mod_U_TaRef[i],UTb,mod_rslts_prev, httc_rur) ;         
//	                    System.out.println(for_tab.toString());
//	                    System.exit(1);
	                    //System.out.println(for_tab.toString());
	                    long t1 = System.currentTimeMillis();
	                    //System.out.println("calcLoop takes " + (t1-t0));
	                   
	                    
	                    //mod_rslts_prev.set(grid, for_tab.get(FOR_TAB_Tac_INDEX));
	                    mod_rslts_prev.add(for_tab.get(FOR_TAB_Tac_INDEX));
	                      
	                      
	                    //mod_rslts.set(grid, for_tab);
	                    mod_rslts.add(for_tab);
	                    //mod_rslts[0][grid]   = for_tab;  //# # append the main data to the main modelled data frame 
//	                    #print (i,grid,for_tab)
	                    
	                    // # need yd_actual, TM, lat
	                    // # hardcoded for now
	                    double lat = -37.8136;
	                    
	            	    Calendar calendar = Calendar.getInstance();
	            	    calendar.setTime(dte);	
	            	    int yd_actual = calendar.get(Calendar.YEAR);
	            	    int TM = calendar.get(Calendar.HOUR_OF_DAY);
	                    
	                    //int yd_actual = dte.timetuple().tm_yday;
	                    //int TM = dte.timetuple().tm_hour;
	                    

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
	                        
	                        //#t10 = time() 
	                        
	                        double lup = Constants.cs_sb*Math.pow((metTa0  +273.15),4);
////	                        tmrtCacheKey=str(Tac)+ str(met_d['RH'][i])+ str(Ucan)+ str(met_d['Kd'][i])+ str(Tsurf_can)+ str(met_d['Ld'][i])+ str(lup)+ str(yd_actual)+ str(TM)+ str(lat)
//	                        if tmrtCacheKey in tmrtCache: 
//	                            tmrt = tmrtCache[tmrtCacheKey]
//	                            //#print (tmrtCacheKey,'=',tmrt)
//	                        else:
//	                            //#t12 = time() 
	                            tmrt = utciInstance.getTmrtForGrid_RH(Tac,metRH0,Ucan,metKd0,Tsurf_can,metTa0, lup ,yd_actual, TM, lat);
//	                            tmrtCache[tmrtCacheKey] = tmrt
//	                            //#t13 = time() 
//	                            //#print 'getTmrtForGrid_RH takes %f' %(t12-t13)
//	                            //#print ('calculated tmrt=',tmrt)
	                         
//	                        utciCacheKey=str(Tac)+ str(Ucan)+ str(met_d['RH'][i])+ str(tmrt) 
//	                        if utciCacheKey in utciCache:
//	                            utci = utciCache[utciCacheKey]
//	                        else:
//	                            //t14 = time() 
	                            utci = utciInstance.getUTCIForGrid_RH(Tac,Ucan,metRH0,tmrt);
//	                            print (Tac,Ucan,met_d['RH'][i],tmrt,utci)
//	                            utciCache[utciCacheKey]=utci
//	                            //t15 = time() 
//	                            print 'getUTCIForGrid_RH takes %f' %(t15-t14)
	                            
	                            
	                        //#t11 = time() 
	                        //#print 'tmrt/utci takes %f' %(t11-t10)
	                    }
	                         

	                    
	                	TreeMap<Integer,Double> for_tab_tmrt_utci = new TreeMap<Integer,Double>();
	                	double fid = (double)lc_data.get(grid).get(LCData.FID);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_FID_INDEX,fid);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_tmrt_INDEX,tmrt);
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_utci_INDEX,utci);
	                	double dteDouble = (double)dte.getTime();
	                	for_tab_tmrt_utci.put(FOR_TAB_UTCI_dte_INDEX,dteDouble);
	                	
	                	//System.out.println(for_tab_tmrt_utci.toString());
	                	
	                	
	                    //for_tab_tmrt_utci     = (lc_data.ix[grid]['FID'],tmrt,utci,dte) ;
	                    //#print (wS_Ta['Ta_f'],met_d['RH'][i],Ucan,met_d['Kd'][i],tS_can,met_d['Ld'][i], cs.cs['sb']*(met_d['Ta'][i]  +273.15)**4 ,yd_actual, TM, lat,tmrt,utci)
	                	//mod_rslts_tmrt_utci.set(grid, for_tab_tmrt_utci);
	                	mod_rslts_tmrt_utci.add(for_tab_tmrt_utci);	          
	                //t7 = time() 
	                //print 'function for grid takes %f' %(t7-t6)
	                }
	                
	                
	                

	        		
	        		
	        		mod_rslts_all_timesteps.add(mod_rslts);
	        		mod_rslts_tmrt_utci_all_timesteps.add(mod_rslts_tmrt_utci);

	                //System.out.println(mod_rslts); 
	            }
//	            dateString =str(dte)
//	            dateString=dateString.replace(" ", "_")
//	            dateString=dateString.replace(":", "-")
//	            saveFileName = run + '_' + dateString
//	            saveFileName_utci = run + '_utci_' + dateString
////	# commenting out saving for now to try and optimize the model
//	            np.save(os.path.join('..','output',cfM['site_name'], saveFileName), mod_rslts)
//	            np.save(os.path.join('..','output',cfM['site_name'], saveFileName_utci), mod_rslts_tmrt_utci)
	            
	
	            //mod_rslts_prev = mod_rslts;
	            
                long t11 = System.currentTimeMillis();
                System.out.println("int i=0;i<met_data_all.size() loop takes " + (t11-t10) + " " + new Date(System.currentTimeMillis()));
	            
	        }
	        netCdfOutput.outputNetcdf(loc, x, y, mod_rslts_all_timesteps, mod_rslts_tmrt_utci_all_timesteps);
	   // ##########################################################################################
	        //mod_rslts = mod_rslts[1:] ;//### THIS IS THE FINAL DATA ARRAY WITH MODEL OUTPUTS  ######
	        //return mod_rslts   ;                     
	                        
	                        
	                        
	                        
	}                 



	                        
	                        
	                        

//	#                    mod_rslts[i][grid]   = for_tab  ## append the main data to the main modelled data frame 
//	#    ##########################################################################################
//	#        mod_rslts = mod_rslts[1:] ### THIS IS THE FINAL DATA ARRAY WITH MODEL OUTPUTS  ######
//	#    ##########################################################################################



//	#    outdir= os.path.join('..','output',cfM['site_name'])       ## defines a director for outputing plot
//	#    if not os.path.exists(outdir):          
//	#            os.makedirs(outdir)
//	#    np.save(os.path.join('..','output',cfM['site_name'], run), mod_rslts)   ### saves the output array as a numpy array can load with numpy.load
//	#
//	### run Ash's plotting scripts...    
//	#    if cfM['val_ts'] == 'Y':
//	#        if confirm('validate Ts for AWS?') == True:      
//	#            val_ts(cfM,run,stations,mod_rslts)    
//	#    if cfM['val_ta'] == "Y": 
//	#        if confirm('validate Ta for AWS?') == True:
//	#            val_ta(cfM,met_data,stations,obs_data,mod_rslts,Dats)
//	#    if cfM['gis_plot'] == 'Y':
//	#        if confirm('plot GIS for grid?') == True:
//	#            gis(cfM,mod_rslts,run)
//	#        
//	## save the control file....      
//	#    inpt1 = open(ControlFileName, 'r')
//	#    outpt1 = open(os.path.join(figdir,'main_control_file.txt'),'w')
//	#    txt1 = inpt1.read()
//	#    outpt1.write(txt1)
//	#    inpt1.close()
//	#    outpt1.close()
//	#
//	## save the constants file..    
//	#    inpt1 = open(os.path.join('.','constants2.py'),'r')
//	#    outpt1 = open(os.path.join(figdir,'constants.txt'),'w')
//	#    txt1 = inpt1.read()
//	#    outpt1.write(txt1)
//	#    inpt1.close()
//	#    outpt1.close()


	
	


public TreeMap<Integer,Double> calcLoop(ArrayList<ArrayList<Double>> lc_data,int grid,int counter,int i,ArrayList<ArrayList<Object>> met_d,
		double[][][] mod_data_ts_,Cfm cfm, double z_Uref, double z_Hx2, double Tb_rur,
		Date dte, double mod_U_TaRef, double UTb, ArrayList<Double> mod_rslts_prev, double httc_rur)
{
	ArrayList<Object> met0 = met_d.get(i);	
	double metTa0 = (double)met0.get(MetData.Ta);
	double metKd0 = (double)met0.get(MetData.Kd);
	double metWS0 = (double)met0.get(MetData.WS);
	double metRH0 = (double)met0.get(MetData.RH);
	
	   
//    String calcLoopCacheKey = str(i) + '_' + str(lc_data['roof'][grid]) + '_' + str(lc_data['road'][grid]) + '_' + str(lc_data['watr'][grid]) + '_' + str(lc_data['conc'][grid]) + '_' + str(lc_data['Veg'][grid]) + '_' + str(lc_data['dry'][grid]) + '_' + str(lc_data['irr'][grid]) + '_' + str(lc_data['H'][grid]) + '_' + str(lc_data['W'][grid]) 
//    
//
//    //#print ('key=',calcLoopCacheKey)
//    if calcLoopCacheKey in calcLoopCache:
//        values = calcLoopCache[calcLoopCacheKey] 
//        new_for_tab = (lc_data.ix[grid]['FID'],values[0][1],values[0][2], values[0][3],values[0][4],values[0][5],values[0][6],values[0][7],values[0][8],values[0][9],values[0][10],values[0][11]) 
//        //#print (values[0][0])
//        //#  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//        //#print ('found in cache ',values)
//        //#return values
//        //#return for_tab,Tac,Ucan,Tsurf_can
//        return new_for_tab,values[1],values[2],values[3]
//
//       

    //# create dictionary for raw LC inputs
//    LC = {}                                
//    LC['roof']=lc_data.get(grid).get(LCData.ROOF_SURF);
//    LC['road']=lc_data['road'][grid]
//    LC['watr']=lc_data['watr'][grid] 
//    LC['conc']=lc_data['conc'][grid]                
//    LC['Veg']=lc_data['Veg'][grid]
//    LC['dry']=lc_data['dry'][grid]
//    LC['irr']=lc_data['irr'][grid]  

 
    double H      = lc_data.get(grid).get(LCData.H);     //  # building height for grid point
    double W      = lc_data.get(grid).get(LCData.W);      // # street width for grid point 
    //## create dictionary with land cover stuff
    LcSort lcSort = new LcSort();
    ArrayList<Double> LC = lc_data.get(grid);
   
    TreeMap<String,Object> lc_stuff = lcSort.lc_sort(LC,H,W);
    //#print (lc_stuff)
   
    double Wtree  = (double) lc_stuff.get(LcSort.Wtree_KEY);        // # street width (including area below trees) for grid point 
        
    //# define surface temperature of trees as canyon air temperature     
    if (! (i == 0))
    {
        mod_data_ts_[i][9][getSurfIndex("Veg")] = mod_rslts_prev.get(grid);
        //#mod_data_ts_[i][9]['Veg'] = met_d['Ta'][i]
    }
    else
    {
        mod_data_ts_[i][9][getSurfIndex("Veg")] = metTa0;
    }
            
    
    //### below various land cover dictionaries are defined.
    LC  = (ArrayList<Double>) lc_stuff.get(LcSort.LC_KEY);                //  # all surfaces not averaged (can be > 1.0)
    ArrayList<Double> LC_wRoofAvg   = (ArrayList<Double>) lc_stuff.get(LcSort.LC_wRoofavg_KEY);        // # average over the total 3D surface (will be = 1.0)

    double svfgA = Math.pow((1.0+Math.pow((H/Wtree),2)),0.5) - H/Wtree ;     // # average SVF of the ground
    double svfwA = 1./2.*(1.+W/H-Math.pow((1.+Math.pow((W/H),2.)),0.5)) ;    // # average SVF of the wall

     
    int fw   = (int) lc_stuff.get(LcSort.fw_KEY);     //  # integer used to indicate relevant wall SVF - this doesn't get used any more - Ts for walls is stored in "mod_data_ts_[i][9]['wall']"
    int fg   = (int) lc_stuff.get(LcSort.fg_KEY);     //  # integer used to indicate relevant ground SVF

//    if (cfm.getValue("use_obs_ws").equals("Y"))
//    {
//        obs_ws = obs_data_all['WS_ms_Avg_'+cfm['STa'][counter]][i];    // # observed wind speed file  | users will not use observed wind speed, this is just for testing
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
    double LCroof = LC.get(LCData.roof);     //# plan area of roof 
    double LCcan  = LC.get(LCData.road)+LC.get(LCData.watr)+LC.get(LCData.conc)+LC.get(LCData.Veg)+LC.get(LCData.dry)+LC.get(LCData.irr); //# plan area of canyon
//    #LCcan  = LC['road']+LC['watr']+LC['conc']+LC['dry']+LC['irr'] //# plan area of canyon
    double LChorz  = LCroof+LCcan;
    double PlanRoof   =  LCroof/LChorz;
    double PlanCan   = LCcan/LChorz;
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
        Tacprv=mod_rslts_prev.get(grid);
        
        roofTsrfT = mod_data_ts_[i-1][roofIndex][9];

//        if np.isnan(Tacprv)
//        {
//            Tacprv = metTa0;
//        }
    }
        
    else
    {
        Tacprv = metTa0;
        roofTsrfT = metTa0;
    }
        
    double Tac_can_roof = ((LCroof/LChorz)*roofTsrfT) + ((LCcan/LChorz)*Tacprv)  ;  

    double Ri_urb_new = sfcRi.sfc_ri(z_Hx2-H-z0m_urb,Tb_rur,Tac_can_roof,Uz);   //## calculate Richardson's number for roof  
    TreeMap<String,Double> httcReturn = httc.httc(Ri_urb_new,Uz,z_Hx2-H-z0m_urb,z0m_urb,z0h_urb, met_d,i,Tac_can_roof,Tb_rur);       //## calculate httc for Rural site  
    double httc_urb_new =  httcReturn.get(Httc.HTTC_KEY);      

    //## calculate Tsurf of the canyon... this inclues walls
    
//    System.out.println(mod_data_ts_[i][roofIndex][9]*LC.get(LCData.roof));
//    System.out.println((mod_data_ts_[i][concIndex][fg]*LC.get(LCData.conc)));
//    System.out.println((mod_data_ts_[i][roadIndex][fg]*LC.get(LCData.road)) );
//    System.out.println((mod_data_ts_[i][watrIndex][fg]*LC.get(LCData.watr)) );
//    System.out.println((mod_data_ts_[i][dryIndex][fg]*LC.get(LCData.dry))  );
//    System.out.println((mod_data_ts_[i][irrIndex][fg]*LC.get(LCData.irr)) );
//    System.out.println( (mod_data_ts_[i][wallIndex][fw]*LC.get(LCData.wall))  );
//    System.out.println((mod_data_ts_[i][VegIndex][9]*LC.get(LCData.Veg)) );
    
  
  
    		  
 
    		 
  
    		
    		 
    
    
    double Tsurf_can  = (mod_data_ts_[i][roofIndex][9]*LC.get(LCData.roof)) 
    		+ (mod_data_ts_[i][concIndex][fg]*LC.get(LCData.conc)) 
    		+  (mod_data_ts_[i][roadIndex][fg]*LC.get(LCData.road)) 
    		+ (mod_data_ts_[i][watrIndex][fg]*LC.get(LCData.watr)) 
    		+ (mod_data_ts_[i][dryIndex][fg]*LC.get(LCData.dry))  
    		+  (mod_data_ts_[i][irrIndex][fg]*LC.get(LCData.irr)) 
    		+ (mod_data_ts_[i][wallIndex][fw]*LC.get(LCData.wall))  
    		+ (mod_data_ts_[i][VegIndex][9]*LC.get(LCData.Veg)) ; 
    //# calculate average horizontal surface temperature (excludes walls) -- Tsurf = 1.0
    ArrayList<Double> LcH  = LC_wRoofAvg ;
    double Tsurf_horz = (mod_data_ts_[i][roofIndex][9]*LcH.get(LCData.roof)) 
    		+ (mod_data_ts_[i][concIndex][fg]*LcH.get(LCData.conc)) 
    		+  (mod_data_ts_[i][roadIndex][fg]*LcH.get(LCData.road)) 
    		+ (mod_data_ts_[i][watrIndex][fg]*LcH.get(LCData.watr)) 
    		+ (mod_data_ts_[i][dryIndex][fg]*LcH.get(LCData.dry))  
    		+  (mod_data_ts_[i][irrIndex][fg]*LcH.get(LCData.irr)) 
    		+ (mod_data_ts_[i][VegIndex][9]*LcH.get(LCData.Veg));
    double Tsurf_wall = mod_data_ts_[i][wallIndex][fw];

    
//    ################################################
//                    ## calculate the canopy air temperature (Tac)  ##
//    ################################################

    double Tac = -999.0;
    if (cfm.getValue("include roofs").equals("Y"))
    {
        if (cfm.getValue("direct roofs").equals("Y"))
        {
//            ## this connects the roofs directly to the canyon via 2 resistances [this one works best]
            Tac =  ((mod_data_ts_[i][concIndex][fg]*httc_can*LC.get(LCData.conc)) 
            		+ (mod_data_ts_[i][roofIndex][9]/((1./httc_can)+(1./httc_urb_new))*LC.get(LCData.roof)) 
            		+  (mod_data_ts_[i][roadIndex][fg]*httc_can*LC.get(LCData.road)) 
            		+ (mod_data_ts_[i][watrIndex][fg]*httc_can*LC.get(LCData.watr)) 
            		+ (mod_data_ts_[i][dryIndex][fg]*httc_can*LC.get(LCData.dry))  
            		+  (mod_data_ts_[i][irrIndex][fg]*httc_can*LC.get(LCData.irr)) 
            		+ (mod_data_ts_[i][wallIndex][fw]*httc_can*LC.get(LCData.wall))  
            		+ (mod_data_ts_[i][VegIndex][9]*httc_can*LC.get(LCData.Veg)) 
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
        Tac =  ((mod_data_ts_[i][concIndex][fg]*httc_can*LC.get(LCData.conc)) 
        		+  (mod_data_ts_[i][roadIndex][fg]*httc_can*LC.get(LCData.road)) 
        		+ (mod_data_ts_[i][watrIndex][fg]*httc_can*LC.get(LCData.watr)) 
        		+ (mod_data_ts_[i][dryIndex][fg]*httc_can*LC.get(LCData.dry))  
        		+  (mod_data_ts_[i][irrIndex][fg]*httc_can*LC.get(LCData.irr)) 
        		+ (mod_data_ts_[i][wallIndex][fw]*httc_can*LC.get(LCData.wall))  
        		+ (mod_data_ts_[i][VegIndex][9]*httc_can*LC.get(LCData.Veg)) 
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
        Tsurf_horz = mod_data_ts_[i][roofIndex][9]*LcH.get(LCData.roof);
    }
    
    TreeMap<Integer,Double> for_tab = new TreeMap<Integer,Double>();

//    ## append everything to output table #####   
    
//    if (cfm.getValue("gis_plot").equals("Y"))
//    {
//        for_tab     = (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz,Tac,dte);   
//    }
//    else
//    {
//        for_tab     = (,,, ,,,,,,,,) ;
//    }
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
               
//    calcLoopCache[calcLoopCacheKey] = for_tab,Tac,Ucan,Tsurf_can
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
