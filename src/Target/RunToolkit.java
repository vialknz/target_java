package Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class RunToolkit
{
	public double latEdge = 0;
	public double latResolution = 0;
	public double lonEdge = 0;
	public double lonResolution = 0;

	public int y = 0; //lon
	public int x = 0; //lat
	
	Common common = new Common();
	public static String workingDirectory;
	
	
	/*
	config file should include (although none of the validation info section is used in this version):
	
####### Sunbury extreme summer Main Control File #######
####### INPUTS #######
site_name = "Sunbury3ExtremeB"									             # site name (string)
run_name   = "Sunbury3ExtremeB"                                               # run name (string)
inpt_met_file =  "Sunbury3ExtremeB.csv"				       # input meteorolgical file (i.e. forcing file)
inpt_lc_file  =  "Sunbury3ExtremeB_LC.csv"                                    #  input land cover data file
date_fmt = '%d/%m/%Y %H:%M'                                                      # format of datetime in input met files
timestep = '1800S'     # define in seconds
use solve = 'N'                                         # if set to Y it will  solve (iteratively) Tb above canyon and roof
include roofs = 'Y'                                       # turn roofs on and off to affect Tac
direct roofs = 'Y'                                                                 
#---------------------------------------------------------------------------------------------------------
# dates 
#---------------------------------------------------------------------------------------------------------
date1A=2009,2,5,0									# year,month,day,hour	#start date for simulation (should be a minimum of 24 hours prior to date1)
date1 =2009,2,5,12									# year,month,day,hour	## the date/time for period of interest (i.e. before this will not be saved)
date2 =2009,2,7,23
######################
##### Validation Info ####
val_ta = 'N'                                                                    ## generate validation plots for Ta?
val_ts = 'N'                                                                    ## generate validation plots for Ts?
gis_plot = 'N'                                                                  ## generated GIS validation plots?
inpt_obs_file =  'Preston_obs_30min.csv'							      ## oberved AWS data (for validation)
inpt_grid_file = '30mGrid.shp'                                                  ## input grid shapefile
radius=30m                                                                      ## grid resoultion 
date1Ts1=2011,2,16,14									            ## year,month,day,hour # start date/time for obs Ts data 
date1Ts2=2011,2,16,16									            ## year,month,day,hour # end   date/time for obs Ts data
date2Ts1=2011,2,15,2									           ## year,month,day,hour # start date/time for obs Ts data 
date2Ts2=2011,2,15,3									           ## year,month,day,hour # end   date/time for obs Ts data
Ts_prd1 = 'day'										           # names for Ts test periods
Ts_prd2 = 'night'						                                  # names for Ts test periods
STa = '01','02','03','04','05','06','07','08','09','10','11','12','13','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'					                                  ## names for Ts test periods
########################
mod_ldwn = 'N'                                                      # used modelled ldown
use_obs_ws   	= 'N'									# trigger to activate use of observed ws inputs
lat=-37.8136
domainDim=344,235
latEdge=-37.505054
lonEdge=144.647901
latResolution=.00004294
lonResolution=.0021849
disableOutput=Utb,Fid,modUTaRef,TbRur,HttcCan,HttcUrbNew,TsurfWall,TsurfCan,TsurfHorz,Ucan   # these variables will not be output in the netcdf
	 
	 */


	public static void main(String[] args)
	{
		
		RunToolkit runtoolkit = new RunToolkit();
		runtoolkit.run(args);
		
//		This is the main script that runs the toolkit air temperature module 
//
//		Developed by Ashley Broadbent, Andrew Coutts, Matthias Demuzere, and Kerry Nice
//		see ./documents/Toolkit-2_tech-description.docx for full description of the module
//      converted to Java 8
	}
	 public void run(String[] args)
	 {
		 workingDirectory = System.getProperty("user.dir");
 
		String controlFileName=null;
		Cfm cfm = null;  //control file data structure
		//### This is the dictionary that contains all the control file information 
		if (args.length > 0)
		{
			controlFileName = args[0];
			cfm = new Cfm(controlFileName);
		}
		else 
		{
			System.out.println("Usage: Target.RunToolkit /pathto/ControlFile.txt");
			System.exit(1);
		}
		
		//handle both unix and windows paths
		String regex = "[/\\\\]";
		String[] controlFileNameSplit = controlFileName.split(regex);
		int lengthOfSplit = controlFileNameSplit.length;
		String controlTextFile = controlFileNameSplit[lengthOfSplit-1];
		
//		System.out.println(controlTextFile);
		
		for (String filepathelement : controlFileNameSplit)
		{
			System.out.println(filepathelement);
		}
		

		String controlTextFileSubpath = File.separator
				+ controlFileNameSplit[lengthOfSplit-3]
				+ File.separator
				+ controlFileNameSplit[lengthOfSplit-2]
				+ File.separator
				+ controlFileNameSplit[lengthOfSplit-1];
		
		controlTextFileSubpath = controlFileName.replace(controlTextFile, "");
		
//		System.out.println(controlTextFileSubpath);

		String rootDir = controlFileName.replaceAll(controlTextFileSubpath, "");
		rootDir = controlTextFileSubpath + ".."  + File.separator + ".."  + File.separator;
		
//		System.out.println(rootDir);
	
		String outputFile = rootDir + "/output/" + cfm.getValue("site_name") + "/" + cfm.getValue("run_name") + ".nc";
		common.createDirectory(rootDir + "/output/" + cfm.getValue("site_name") + "/");	
		
		lonResolution = cfm.getDoubleValue("lonResolution");
		latResolution = cfm.getDoubleValue("latResolution");
		lonEdge = cfm.getDoubleValue("lonEdge");
		latEdge = cfm.getDoubleValue("latEdge");
		
		String domainDim=cfm.getValue("domainDim");
		String[] domainDimSplit = domainDim.split(",");
		y=new Integer(domainDimSplit[0]).intValue();
		x=new Integer(domainDimSplit[1]).intValue();

		//		######### DEFINE START AND FINISH DATES HERE ########
		
		Date date1A = cfm.getDateValue("date1A");
		Date date1 = cfm.getDateValue("date1");
		Date date2 = cfm.getDateValue("date2");

		TreeMap<String,Date> Dats = new TreeMap<String,Date>();
		Dats.put("date1A", date1A);
		Dats.put("date1", date1);
		Dats.put("date2", date2);
		
		String lcFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/LC/" + cfm.getValue("inpt_lc_file");
	
		LCData lcDataClass = new LCData(lcFilename);
		ArrayList<ArrayList<Double>> lc_data = lcDataClass.getlcData();
		double maxH = lcDataClass.getMaxH();
		double maxW = lcDataClass.getMaxW();

		String metFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/MET/" + cfm.getValue("inpt_met_file");
		MetData metDataClass = new MetData(metFilename, cfm.getValue("mod_ldwn"), date1A, date2);
		ArrayList<ArrayList<Object>> met_data = metDataClass.getMetData();
		TargetModule tkmd = new TargetModule(workingDirectory);
		tkmd.rootDirectory = rootDir;
		tkmd.modelRun(cfm, lc_data, met_data, Dats, maxH, maxW, x, y, latEdge, latResolution, lonEdge, lonResolution, outputFile);
	}

}
