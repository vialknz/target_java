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
	
#---------------------------------------------------------------------------------------------------------
####### Example Main Control File #######
#--------------------------------------------------------------------------------------------------------
####### INPUTS #######
site_name = "Mawson"					 # site name (string)
run_name   = "MawsonExample"                             # run name (string)
inpt_met_file =  "Mawson-meteorology_KentTown_30min.csv"	# input meteorolgical file (i.e. forcing file)
inpt_lc_file  =  "100m_lc_grid.csv"                      #  input land cover data file
date_fmt = '%d/%m/%Y %H:%M'                              # format of datetime in input met files
timestep = '1800S'                                       # define in seconds 
include roofs = 'Y'                                      # turn roofs on and off to affect Tac
direct roofs = 'Y'                                       # turn roofs on and off to affect Tac
#--------------------------------------------------------------------------------------------------------
# dates
#---------------------------------------------------------------------------------------------------------
SpinUp=2011,2,14,0					# year,month,day,hour	#start date for simulation (should be a minimum of 24 hours prior to date1)
StartDate =2011,2,15,0					# year,month,day,hour	## the date/time for period of interest (i.e. before this will not be saved)
EndDate =2011,2,16,18 					# year,month,day,hour	# end date for validation period
######################

mod_ldwn = 'N'             # use modelled ldown
lat=-37.8136
domainDim=9,3
latEdge=-34.79829
lonEdge=138.79829
latResolution=0.00088
lonResolution=0.00110
disableOutput=Utb,Fid,modUTaRef,TbRur,HttcCan,HttcUrbNew,TsurfWall,TsurfCan,TsurfHorz,Ucan

#override default parameters, remove '#' comment to use
#z0m_rur=0.45
#z_URef=10.0
#z_TaRef=2.0
#zavg=4.5
#### options for reference surfaces are Veg, road, watr, conc, dry, irr, and roof
#ref_surf1=dry
#ref_surf2=conc

	 
	 
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
		
//		for (String filepathelement : controlFileNameSplit)
//		{
//			System.out.println(filepathelement);
//		}
		

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
		
		Date spinUp = cfm.getDateValue("SpinUp");
		Date startDate = cfm.getDateValue("StartDate");
		Date endDate = cfm.getDateValue("EndDate");

		TreeMap<String,Date> Dats = new TreeMap<String,Date>();
		Dats.put("SpinUp", spinUp);
		Dats.put("StartDate", startDate);
		Dats.put("EndDate", endDate);
		
		String lcFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/LC/" + cfm.getValue("inpt_lc_file");
	
		LCData lcDataClass = new LCData(lcFilename);
		ArrayList<ArrayList<Double>> lc_data = lcDataClass.getlcData();
		double maxH = lcDataClass.getMaxH();
		double maxW = lcDataClass.getMaxW();

		String metFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/MET/" + cfm.getValue("inpt_met_file");
		MetData metDataClass = new MetData(metFilename, cfm.getValue("mod_ldwn"), spinUp, endDate);
		ArrayList<ArrayList<Object>> met_data = metDataClass.getMetData();
		TargetModule tkmd = new TargetModule(workingDirectory);
		tkmd.rootDirectory = rootDir;
		tkmd.modelRun(cfm, lc_data, met_data, Dats, maxH, maxW, x, y, latEdge, latResolution, lonEdge, lonResolution, outputFile);
	}

}
