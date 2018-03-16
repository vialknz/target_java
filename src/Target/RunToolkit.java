package Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class RunToolkit
{
	//TODO all of these need to eventually come from the configuration file
	//domain dimension and resolution of each grid
	public double latEdge = -34.81425315;
	public double latResolution = .00004294;
	public double lonEdge = 138.6066486;
	public double lonResolution = .0021849;	
	//location to save output
    public String loc = "/tmp/testWrite.nc";
    //domain dimensions
	public int y = 344; //lon
	public int x = 235; //lat

	public static void main(String[] args)
	{
		
		RunToolkit runtoolkit = new RunToolkit();
		runtoolkit.run(args);
		
//		This is the main script that runs the toolkit air temperature module 
//
//		Developed by Ashley Broadbent, Andrew Coutts, and Matthias Demuzere.
//
//		This script should be run from the ./scripts directory
//
//		see ./documents/Toolkit-2_tech-description.docx for full description of the module
//
//		These scripts should run in Windows and Linux - however the gis function (arcpy) can't be used in Linux
//
//		Developed in Windows with Python 2.7.12 Anaconda 4.1.1 (32-bit)
//
//		Tested with Python 2.7.9 (Linux)
	}
	 public void run(String[] args)
	 {

		String controlFileName;
		Cfm cfm = null;  //control file data structure
		//### This is the dictionary that contains all the control file information 
		if (args.length > 1)
		{
			controlFileName = args[1];
			cfm = new Cfm(controlFileName);
		}
		else 
		{
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Sunbury3ExtremeB/controlfiles/Sunbury3ExtremeB/Sunbury3ExtremeB.txt";
			cfm = new Cfm(controlFileName);
		}
		
		//handle both unix and windows paths
		String regex = "[/\\\\]";
		String[] controlFileNameSplit = controlFileName.split(regex);
		int lengthOfSplit = controlFileNameSplit.length;
		String controlTextFile = controlFileNameSplit[lengthOfSplit-1];
//		System.out.println(controlFileName);
		String controlTextFileSubpath = File.separator
				+ controlFileNameSplit[lengthOfSplit-3]
				+ File.separator
				+ controlFileNameSplit[lengthOfSplit-2]
				+ File.separator
				+ controlFileNameSplit[lengthOfSplit-1];
//		System.out.println(controlTextFileSubpath);
		String rootDir = controlFileName.replaceAll(controlTextFileSubpath, "");
//		System.out.println(rootDir);
//		System.exit(1);
		
		String outputFile = rootDir + "/output/" + cfm.getValue("site_name") + "/" + cfm.getValue("site_name") + ".nc";

		// # parse dates for input met file using format defined in control file
		//String dateparse = cfm.getValue("date_fmt");
		                       
		//String run = cfm.getValue("run_name"); // # model run name 

		//String tmstp = cfm.getValue("timestep"); // # time step (seconds)
		//int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
		//		######### DEFINE START AND FINISH DATES HERE ########
		
		Date date1A = cfm.getDateValue("date1A");
		Date date1 = cfm.getDateValue("date1");
		Date date2 = cfm.getDateValue("date2");
		//long tD = (date2.getTime() - date1A.getTime()) ;  // to days    / (1000 * 60 * 60 * 24)
		//long nt = (tD / 1000) / tmstpInt ;
		TreeMap<String,Date> Dats = new TreeMap<String,Date>();
		Dats.put("date1A", date1A);
		Dats.put("date1", date1);
		Dats.put("date2", date2);
		
		//String rootDir = "..";
		//rootDir = "/home/kerryn/Documents/Work/Toolkit2-Runs/Sunbury3ExtremeB";
		String lcFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/LC/" + cfm.getValue("inpt_lc_file");
		String domainDim = cfm.getValue("domainDim");
		String[] domainDimArray = domainDim.split(",");
		int xDim = new Integer(domainDimArray[0]).intValue();
		int yDim = new Integer(domainDimArray[1]).intValue();
		LCData lcDataClass = new LCData(lcFilename);
		ArrayList<ArrayList<Double>> lc_data = lcDataClass.getlcData();
		double maxH = lcDataClass.getMaxH();
		double maxW = lcDataClass.getMaxW();

		String metFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/MET/" + cfm.getValue("inpt_met_file");
		MetData metDataClass = new MetData(metFilename, cfm.getValue("mod_ldwn"));
		ArrayList<ArrayList<Object>> met_data = metDataClass.getlcData();
		TargetModule tkmd = new TargetModule();
		tkmd.modelRun(cfm, lc_data, met_data, Dats, maxH, maxW, xDim, yDim,
				x, y, latEdge, latResolution, lonEdge, lonResolution, outputFile);
	}

}
