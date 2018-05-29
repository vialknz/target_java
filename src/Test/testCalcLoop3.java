package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import org.junit.Test;

import Target.Cfm;
import Target.LCData;
import Target.MetData;
import Target.TargetModule;

public class testCalcLoop3
{
	
	double eightDigitDelta = 1e8;
	double sevenDigitDelta = 1e7;
	double sixDigitDelta = 1e6;
	double fiveDigitDelta = 1e5;
	double fourDigitDelta = 1e4;
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	double oneDigitDelta = 1e1;
	double halfDigitDelta = 2;
	double quarterDigitDelta = 0.9;
	
	 double actual,expected;
	 //ArrayList<ArrayList<Double>> lc_data;
	 int grid;
	 int counter;
	 int i;
	 ArrayList<ArrayList<Object>> met_d;
	 double[][][] mod_data_ts_;
	 //Cfm cfm;
	 double z_Uref;
	 double z_Hx2;
	 double Tb_rur;
	 Date dte;
	 double mod_U_TaRef;
	 double UTb;
	 ArrayList<ArrayList<Double>> mod_rslts_prev;
	 double httc_rur;
	 
	 
	 TreeMap<Integer,Double> returnValues ;
	 TargetModule targetModule = new TargetModule("");

	 
     double FID ;        
     double Ucan ;

     double Tsurf_horz;
     double Tsurf_can ;
     double Tsurf_wall;
     double Tac ;
     double httc_urb_new;
     double httc_can;
     double Tb_rur_return ;
     double mod_U_TaRef_return;
     double UTb_return ;
	
     ArrayList<Double> item ;
	

	@Test
	public void test()
	{

		
		 String controlFileName;
			Cfm cfm = null;  //control file data structure
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/controlfiles/Target-java-validation/Target-java-validation2.txt";
			cfm = new Cfm(controlFileName);
			//handle both unix and windows paths
			String regex = "[/\\\\]";
			String[] controlFileNameSplit = controlFileName.split(regex);
			int lengthOfSplit = controlFileNameSplit.length;
			String controlTextFile = controlFileNameSplit[lengthOfSplit-1];
			String controlTextFileSubpath = File.separator
					+ controlFileNameSplit[lengthOfSplit-3]
					+ File.separator
					+ controlFileNameSplit[lengthOfSplit-2]
					+ File.separator
					+ controlFileNameSplit[lengthOfSplit-1];
			String rootDir = controlFileName.replaceAll(controlTextFileSubpath, "");
			String metFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/MET/" + cfm.getValue("inpt_met_file");
			MetData metDataClass = new MetData(metFilename, cfm.getValue("mod_ldwn"));
			ArrayList<ArrayList<Object>> met_data = metDataClass.getMetData();
			
	        String tmstp = cfm.getValue("timestep");  
	        int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
	        int timedelta = tmstpInt*1000;
			
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
        
        
		lcFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/LC/" + "Target-java-validation_LC2.csv" ;
		lcDataClass = new LCData(lcFilename);
		lc_data = lcDataClass.getlcData();

        
        
        grid =0;
		counter = 0;
		i = 15;
		mod_data_ts_= new double[16][10][11];
		mod_data_ts_[i][0][0]=24.31229431;
		mod_data_ts_[i][0][1]=22.48292802;
		mod_data_ts_[i][0][2]=23.52727076;
		mod_data_ts_[i][0][3]=19.09834395;
		mod_data_ts_[i][0][4]=24.48659184;
		mod_data_ts_[i][0][5]=0.0;
		mod_data_ts_[i][0][6]=17.37919191;
		mod_data_ts_[i][0][7]=18.84544421;
		mod_data_ts_[i][0][8]=21.87745907;
		mod_data_ts_[i][0][9]=0.0;
		mod_data_ts_[i][0][10]=0.0;

//		( 24.31229431,  22.48292802,  23.52727076,  19.09834395,  24.48659184,  0.,  17.37919191,  18.84544421,  21.87745907,  0., 0),
//      ( 24.06182159,  22.48292802,  23.27523749,  19.05295674,  24.26312145,  0.,  17.27222606,  18.77362668,  21.86388928,  0., 0),
//      ( 23.82489513,  22.48292802,  23.03489231,  19.00795165,  24.04775074,  0.,  17.17331177,  18.70449329,  21.85039236,  0., 0),
//      ( 23.60068967,  22.48292802,  22.805703  ,  18.96332633,  23.84019037,  0.,  17.08195095,  18.63795041,  21.836968  ,  0., 0),
//      ( 23.38843591,  22.48292802,  22.58716188,  18.91907848,  23.64016143,  0.,  16.9976759 ,  18.57390781,  21.82361589,  0., 0),
//      ( 23.18741626,  22.48292802,  22.37878463,  18.87520577,  23.44739509,  0.,  16.92004738,  18.51227854,  21.81033571,  0., 0),
//      ( 22.99696095,  22.48292802,  22.18010915,  18.83170591,  23.26163218,  0.,  16.84865284,  18.45297875,  21.79712715,  0., 0),
//      ( 22.81644446,  22.48292802,  21.99069449,  18.78857661,  23.08262283,  0.,  16.78310478,  18.3959276 ,  21.78398991,  0., 0),
//      ( 22.64528232,  22.48292802,  21.81011986,  18.7458156 ,  22.91012613,  0.,  16.72303922,  18.34104716,  21.77092367,  0., 0),
//      ( 22.48292802,  22.48292802,  21.63798362,  18.7034206 ,  22.74390978,  0.,  16.66811419,  18.28826227,  21.75792813,  0., 0)],
		
		mod_data_ts_[i][1][0]=24.06182159;
		mod_data_ts_[i][1][1]=22.48292802;
		mod_data_ts_[i][1][2]=23.27523749;
		mod_data_ts_[i][1][3]=19.05295674;
		mod_data_ts_[i][1][4]=24.26312145;
		mod_data_ts_[i][1][5]=0.;
		mod_data_ts_[i][1][6]=17.27222606;
		mod_data_ts_[i][1][7]=18.77362668;
		mod_data_ts_[i][1][8]=21.86388928;
		mod_data_ts_[i][1][9]=0.0;
		mod_data_ts_[i][1][10]=0.0;
		
		mod_data_ts_[i][2][0]=23.82489513;
		mod_data_ts_[i][2][1]=  22.48292802;
		mod_data_ts_[i][2][2]=  23.03489231;
		mod_data_ts_[i][2][3]=  19.00795165;
		mod_data_ts_[i][2][4]=  24.04775074;
		mod_data_ts_[i][2][5]=  0.;
		mod_data_ts_[i][2][6]=  17.17331177;
		mod_data_ts_[i][2][7]=  18.70449329;
		mod_data_ts_[i][2][8]=  21.85039236;
		mod_data_ts_[i][2][9]=0.0;
		mod_data_ts_[i][2][10]=0.0;
		
		mod_data_ts_[i][3][0]=23.60068967;
		mod_data_ts_[i][3][1]=  22.48292802;
		mod_data_ts_[i][3][2]=  22.805703  ;
		mod_data_ts_[i][3][3]=  18.96332633;
		mod_data_ts_[i][3][4]=  23.84019037;
		mod_data_ts_[i][3][5]=  0.;
		mod_data_ts_[i][3][6]=  17.08195095;
		mod_data_ts_[i][3][7]=  18.63795041;
		mod_data_ts_[i][3][8]=  21.836968;
		mod_data_ts_[i][3][9]=0.0;
		mod_data_ts_[i][3][10]=0.0;
		
		mod_data_ts_[i][4][0]=23.38843591;
		mod_data_ts_[i][4][1]=  22.48292802;
		mod_data_ts_[i][4][2]=  22.58716188;
		mod_data_ts_[i][4][3]=  18.91907848;
		mod_data_ts_[i][4][4]=  23.64016143;
		mod_data_ts_[i][4][5]=  0.;
		mod_data_ts_[i][4][6]=  16.9976759 ;
		mod_data_ts_[i][4][7]=  18.57390781;
		mod_data_ts_[i][4][8]=  21.82361589;
		mod_data_ts_[i][4][9]=0.0;
		mod_data_ts_[i][4][10]=0.0;
					
		mod_data_ts_[i][5][0]=23.18741626;
		mod_data_ts_[i][5][1]=  22.48292802;
		mod_data_ts_[i][5][2]=  22.37878463;
		mod_data_ts_[i][5][3]=  18.87520577;
		mod_data_ts_[i][5][4]=  23.44739509;
		mod_data_ts_[i][5][5]=  0.;
		mod_data_ts_[i][5][6]=  16.92004738;
		mod_data_ts_[i][5][7]=  18.51227854;
		mod_data_ts_[i][5][8]=  21.81033571;
		mod_data_ts_[i][5][9]=0.0;
		mod_data_ts_[i][5][10]=0.0;
			
		mod_data_ts_[i][6][0]=22.99696095;
		mod_data_ts_[i][6][1]=  22.48292802;
		mod_data_ts_[i][6][2]=  22.18010915;
		mod_data_ts_[i][6][3]=  18.83170591;
		mod_data_ts_[i][6][4]=  23.26163218;
		mod_data_ts_[i][6][5]=  0.;
		mod_data_ts_[i][6][6]=  16.84865284;
		mod_data_ts_[i][6][7]=  18.45297875;
		mod_data_ts_[i][6][8]=  21.79712715;
		mod_data_ts_[i][6][9]=0.0;
		mod_data_ts_[i][6][10]=0.0;

		mod_data_ts_[i][7][0]=22.81644446;
		mod_data_ts_[i][7][1]=  22.48292802;
		mod_data_ts_[i][7][2]=  21.99069449;
		mod_data_ts_[i][7][3]=  18.78857661;
		mod_data_ts_[i][7][4]=  23.08262283;
		mod_data_ts_[i][7][5]=  0.;
		mod_data_ts_[i][7][6]=  16.78310478;
		mod_data_ts_[i][7][7]=  18.3959276 ;
		mod_data_ts_[i][7][8]=  21.78398991;
		mod_data_ts_[i][7][9]=0.0;
		mod_data_ts_[i][7][10]=0.0;

		mod_data_ts_[i][8][0]=22.64528232;
		mod_data_ts_[i][8][1]=  22.48292802;
		mod_data_ts_[i][8][2]=  21.81011986;
		mod_data_ts_[i][8][3]=  18.7458156 ;
		mod_data_ts_[i][8][4]=  22.91012613;
		mod_data_ts_[i][8][5]=  0.;
		mod_data_ts_[i][8][6]=  16.72303922;
		mod_data_ts_[i][8][7]=  18.34104716;
		mod_data_ts_[i][8][8]=  21.77092367;
		mod_data_ts_[i][8][9]=0.0;
		mod_data_ts_[i][8][10]=0.0;
		
		mod_data_ts_[i][9][0]=22.48292802;
		mod_data_ts_[i][9][1]=  22.48292802;
		mod_data_ts_[i][9][2]=  21.63798362;
		mod_data_ts_[i][9][3]=  18.7034206 ;
		mod_data_ts_[i][9][4]=  22.74390978;
		mod_data_ts_[i][9][5]=  0.;
		mod_data_ts_[i][9][6]=  16.66811419;
		mod_data_ts_[i][9][7]=  18.28826227;
		mod_data_ts_[i][9][8]=  21.75792813;
		mod_data_ts_[i][9][9]=0.0;
		mod_data_ts_[i][9][10]=0.0;
				
		///
		
//		('mod_data_ts_[i-1]', array([ 
//         ( 24.17555275,  22.22939669,  23.42184869,  19.10426263,  24.35280063,   0.       ,  17.44227534,  18.87272138,  21.87970829,  0., 0),
//          ( 23.91439793,  22.22939669,  23.16376044,  19.05732503,  24.12668253,   0.       ,  17.31304347,  18.78994503,  21.86734329,  0., 0),
//          ( 23.66598826,  22.22939669,  22.91623229,  19.01072875,  23.9078125 ,   0.       ,  17.19165306,  18.70977009,  21.85503617,  0., 0),
//          ( 23.42961123,  22.22939669,  22.67883036,  18.96447192,  23.6959575 ,   0.       ,  17.07766874,  18.63211465,  21.84278671,  0., 0),
//          ( 23.20459907,  22.22939669,  22.45113866,  18.9185527 ,  23.49089198,   0.       ,  16.97067943,  18.5568995 ,  21.83059471,  0., 0),
//          ( 22.99032562,  22.22939669,  22.23275833,  18.87296922,  23.29239764,   0.       ,  16.87029699,  18.48404812,  21.81845994,  0., 0),
//          ( 22.78620345,  22.22939669,  22.02330694,  18.82771965,  23.10026317,   0.       ,  16.7761549 ,  18.41348647,  21.80638221,  0., 0),
//          ( 22.59168119,  22.22939669,  21.82241773,  18.78280215,  22.91428401,   0.       ,  16.68790709,  18.34514302,  21.79436131,  0., 0),
//          ( 22.40624109,  22.22939669,  21.62973899,  18.7382149 ,  22.73426213,   0.       ,  16.60522674,  18.27894857,  21.78239702,  0., 0),
//          ( 22.22939669,  22.22939669,  21.4449334 ,  18.69395608,  22.56000584,  22.1069891,  16.52780523,  18.21483622,  21.77048914,  0., 0)],


   		
   		mod_data_ts_[i-1][0][0]=24.17555275;
   		mod_data_ts_[i-1][0][1]=22.22939669;
   		mod_data_ts_[i-1][0][2]=23.42184869;
   		mod_data_ts_[i-1][0][3]=19.10426263;
   		mod_data_ts_[i-1][0][4]=24.35280063;
   		mod_data_ts_[i-1][0][5]=0.;
   		mod_data_ts_[i-1][0][6]=17.44227534;
   		mod_data_ts_[i-1][0][7]=18.87272138;
   		mod_data_ts_[i-1][0][8]=21.87970829;
   		mod_data_ts_[i-1][0][9]=0.0;
   		mod_data_ts_[i-1][0][10]=0.0;
   		
//      ( 24.17555275,  22.22939669,  23.42184869,  19.10426263,  24.35280063,   0.       ,  17.44227534,  18.87272138,  21.87970829,  0., 0),

   		
   		mod_data_ts_[i-1][1][0]=23.91439793;
   		mod_data_ts_[i-1][1][1]=22.22939669;
   		mod_data_ts_[i-1][1][2]=23.16376044;
   		mod_data_ts_[i-1][1][3]=19.05732503;
   		mod_data_ts_[i-1][1][4]=24.12668253 ;
   		mod_data_ts_[i-1][1][5]=0.;
   		mod_data_ts_[i-1][1][6]=17.31304347;
   		mod_data_ts_[i-1][1][7]=18.78994503;
   		mod_data_ts_[i-1][1][8]=21.86734329;
   		mod_data_ts_[i-1][1][9]=0.0;
   		mod_data_ts_[i-1][1][10]=0.0;
   		
//      ( 23.91439793,  22.22939669,  23.16376044,  19.05732503,  24.12668253,   0.       ,  17.31304347,  18.78994503,  21.86734329,  0., 0),

   		
   		mod_data_ts_[i-1][2][0]=23.66598826;
   		mod_data_ts_[i-1][2][1]=  22.22939669;
   		mod_data_ts_[i-1][2][2]=  22.91623229;
   		mod_data_ts_[i-1][2][3]=  19.01072875;
   		mod_data_ts_[i-1][2][4]=   23.9078125 ;
   		mod_data_ts_[i-1][2][5]=  0.;
   		mod_data_ts_[i-1][2][6]= 17.19165306;
   		mod_data_ts_[i-1][2][7]=  18.70977009;
   		mod_data_ts_[i-1][2][8]=  21.85503617;
   		mod_data_ts_[i-1][2][9]=0.0;
   		mod_data_ts_[i-1][2][10]=0.0;
   		
//      ( 23.66598826,  22.22939669,  22.91623229,  19.01072875,  23.9078125 ,   0.       ,  17.19165306,  18.70977009,  21.85503617,  0., 0),

   		
   		mod_data_ts_[i-1][3][0]=23.42961123;
   		mod_data_ts_[i-1][3][1]=    22.22939669;
   		mod_data_ts_[i-1][3][2]=    22.67883036  ;
   		mod_data_ts_[i-1][3][3]=    18.96447192;
   		mod_data_ts_[i-1][3][4]=    23.6959575  ;
   		mod_data_ts_[i-1][3][5]=  0.;
   		mod_data_ts_[i-1][3][6]=  17.07766874;
   		mod_data_ts_[i-1][3][7]=    18.63211465;
   		mod_data_ts_[i-1][3][8]=    21.84278671;
   		mod_data_ts_[i-1][3][9]=0.0;
   		mod_data_ts_[i-1][3][10]=0.0;
   		
//      ( 23.42961123,  22.22939669,  22.67883036,  18.96447192,  23.6959575 ,   0.       ,  17.07766874,  18.63211465,  21.84278671,  0., 0),

   		
   		mod_data_ts_[i-1][4][0]=23.20459907;
   		mod_data_ts_[i-1][4][1]=    22.22939669;
   		mod_data_ts_[i-1][4][2]=    22.45113866;
   		mod_data_ts_[i-1][4][3]=    18.9185527 ;
   		mod_data_ts_[i-1][4][4]=    23.49089198 ;
   		mod_data_ts_[i-1][4][5]=  0.;
   		mod_data_ts_[i-1][4][6]=  16.97067943 ;
   		mod_data_ts_[i-1][4][7]=    18.5568995 ;
   		mod_data_ts_[i-1][4][8]=   21.83059471;
   		mod_data_ts_[i-1][4][9]=0.0;
   		mod_data_ts_[i-1][4][10]=0.0;
   		
//      ( 23.20459907,  22.22939669,  22.45113866,  18.9185527 ,  23.49089198,   0.       ,  16.97067943,  18.5568995 ,  21.83059471,  0., 0),

   					
   		mod_data_ts_[i-1][5][0]=22.99032562;
   		mod_data_ts_[i-1][5][1]=    22.22939669;
   		mod_data_ts_[i-1][5][2]=    22.23275833;
   		mod_data_ts_[i-1][5][3]=    18.87296922;
   		mod_data_ts_[i-1][5][4]=    23.29239764  ;
   		mod_data_ts_[i-1][5][5]=  0.;
   		mod_data_ts_[i-1][5][6]=  16.87029699;
   		mod_data_ts_[i-1][5][7]=    18.48404812;
   		mod_data_ts_[i-1][5][8]=   21.81845994;
   		mod_data_ts_[i-1][5][9]=0.0;
   		mod_data_ts_[i-1][5][10]=0.0;
   		
//      ( 22.99032562,  22.22939669,  22.23275833,  18.87296922,  23.29239764,   0.       ,  16.87029699,  18.48404812,  21.81845994,  0., 0),

   			
   		mod_data_ts_[i-1][6][0]=22.78620345;
   		mod_data_ts_[i-1][6][1]=   22.22939669;
   		mod_data_ts_[i-1][6][2]=   22.02330694;
   		mod_data_ts_[i-1][6][3]=  18.82771965;
   		mod_data_ts_[i-1][6][4]=   23.10026317 ;
   		mod_data_ts_[i-1][6][5]=  0.;
   		mod_data_ts_[i-1][6][6]=  16.7761549 ;
   		mod_data_ts_[i-1][6][7]=    18.41348647;
   		mod_data_ts_[i-1][6][8]=    21.80638221;
   		mod_data_ts_[i-1][6][9]=0.0;
   		mod_data_ts_[i-1][6][10]=0.0;
   		
//      ( 22.78620345,  22.22939669,  22.02330694,  18.82771965,  23.10026317,   0.       ,  16.7761549 ,  18.41348647,  21.80638221,  0., 0),


   		mod_data_ts_[i-1][7][0]=22.59168119;
   		mod_data_ts_[i-1][7][1]=   22.22939669;
   		mod_data_ts_[i-1][7][2]=  21.82241773;
   		mod_data_ts_[i-1][7][3]=   18.78280215;
   		mod_data_ts_[i-1][7][4]=   22.91428401;
   		mod_data_ts_[i-1][7][5]=  0.;
   		mod_data_ts_[i-1][7][6]=   16.68790709;
   		mod_data_ts_[i-1][7][7]=    18.34514302 ;
   		mod_data_ts_[i-1][7][8]=  21.79436131;
   		mod_data_ts_[i-1][7][9]=0.0;
   		mod_data_ts_[i-1][7][10]=0.0;
   		
//      ( 22.59168119,  22.22939669,  21.82241773,  18.78280215,  22.91428401,   0.       ,  16.68790709,  18.34514302,  21.79436131,  0., 0),


   		mod_data_ts_[i-1][8][0]=22.40624109;
   		mod_data_ts_[i-1][8][1]=    22.22939669;
   		mod_data_ts_[i-1][8][2]=    21.62973899;
   		mod_data_ts_[i-1][8][3]=    18.7382149  ;
   		mod_data_ts_[i-1][8][4]=    22.73426213;
   		mod_data_ts_[i-1][8][5]=  0.;
   		mod_data_ts_[i-1][8][6]=  16.60522674;
   		mod_data_ts_[i-1][8][7]=    18.27894857;
   		mod_data_ts_[i-1][8][8]=   21.78239702;
   		mod_data_ts_[i-1][8][9]=0.0;
   		mod_data_ts_[i-1][8][10]=0.0;
   		
//      ( 22.40624109,  22.22939669,  21.62973899,  18.7382149 ,  22.73426213,   0.       ,  16.60522674,  18.27894857,  21.78239702,  0., 0),

   		
   		mod_data_ts_[i-1][9][0]=22.22939669;
   		mod_data_ts_[i-1][9][1]=    22.22939669;
   		mod_data_ts_[i-1][9][2]=    21.4449334 ;
   		mod_data_ts_[i-1][9][3]=    18.69395608 ;
   		mod_data_ts_[i-1][9][4]=    22.56000584;
   		mod_data_ts_[i-1][9][5]=    22.1069891;
   		mod_data_ts_[i-1][9][6]=    16.52780523;
   		mod_data_ts_[i-1][9][7]=    18.21483622;
   		mod_data_ts_[i-1][9][8]=    21.77048914;
   		mod_data_ts_[i-1][9][9]=0.0;
   		mod_data_ts_[i-1][9][10]=0.0;
//      ( 22.22939669,  22.22939669,  21.4449334 ,  18.69395608,  22.56000584,  22.1069891,  16.52780523,  18.21483622,  21.77048914,  0., 0)],                       		
		                       		
		
		dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        
		
//		returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, mod_data_ts_, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
//				mod_rslts_prev, httc_rur);
//		System.out.println(returnValues.toString());
		
//('lc_data', FID roof  road  watr  conc  Veg  dry  irr  H    W
//		0    1   1.0   0.0   0.0   0.0  0.0  0.0  0.0  0.5  1
//		1    2   0.0   1.0   0.0   0.0  0.0  0.0  0.0  0.5  1
//		2    3   0.0   0.0   1.0   0.0  0.0  0.0  0.0  0.5  1
//		3    4   0.0   0.0   0.0   1.0  0.0  0.0  0.0  0.5  1
//		4    5   0.0   0.0   0.0   0.0  1.0  0.0  0.0  0.5  1
//		5    6   0.0   0.0   0.0   0.0  0.0  1.0  0.0  0.5  1
//		6    7   0.0   0.0   0.0   0.0  0.0  0.0  1.0  0.5  1
//		7    8   0.1   0.1   0.1   0.1  0.1  0.1  0.4  0.5  1)
		
        grid =0;
		counter = 0;
		i = 15;
		
		z_Uref=10.0;
		z_Hx2=1.0;
		Tb_rur=24.3903759871615;
		//dte=datetime.datetime(2009, 2, 5, 11, 0);			
		mod_U_TaRef=4.89406829;			
		UTb=2.25293948;
		
		mod_rslts_prev=new ArrayList<ArrayList<Double>>();
		item = new ArrayList<Double> ();
		item.add(-999.);
		for (int x = 0;x<i;x++)
		{
			mod_rslts_prev.add(item);
		}				
		httc_rur=0.06318193;
		
		targetModule.mod_data_ts_ = mod_data_ts_;
		returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
				mod_rslts_prev, httc_rur);
		System.out.println(returnValues.toString());
		
        //this is 100% roof
//		('for_tab', (1.0, 1.0457344117123395, 22.482928024954088, 22.482928024954088, 23.824895125098408, -999.0, 
//				datetime.datetime(2009, 2, 5, 7, 30), -5.48128676708857, 0.013430682487111713, 24.3903759871615, 
//				array([ 4.89406829]), array([ 2.25293948])))
//		(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
		
        //double FID = (double)lc_data.get(grid).get(LCData.FID);
        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
        actual = Ucan;
        expected = 1.0457344117123395;
        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
        expected = 22.482928024954088;
	    assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
        expected = 22.482928024954088;
	    assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
        expected = 23.824895125098408;
        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
        expected = -999.0;
        assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
        expected = -5.48128676708857;
        assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
        expected = 0.013430682487111713;
        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
        expected = 24.3903759871615;
        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
        expected = 4.89406829;
        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
        expected = 2.25293948;
        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
        
        
        ///////////////
        
        
        grid =1;
		counter = 1;
		i = 15;
		
		z_Uref=10.0;
		z_Hx2=1.0;
		Tb_rur=24.3903759871615;
		//dte=datetime.datetime(2009, 2, 5, 11, 0);			
		mod_U_TaRef= 4.89406829;			
		UTb=2.25293948;
		
		mod_rslts_prev=new ArrayList<ArrayList<Double>>();
		item = new ArrayList<Double> ();
		item.add(-999.);
		item.add(21.42487154);
		for (int x = 0;x<i;x++)
		{
			mod_rslts_prev.add(item);
		}				
		httc_rur=0.06318193;
		
		targetModule.mod_data_ts_ = mod_data_ts_;
		returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
				mod_rslts_prev, httc_rur);
		System.out.println(returnValues.toString());
		
        //this is 100% road
//		('for_tab', (2.0, 1.0457344117123395, 22.587161876141426, 70.236952126338238, 23.824895125098408, 24.3976536329969, 
//				datetime.datetime(2009, 2, 5, 7, 30), -5.45522771410442, 0.013430682487111713, 24.3903759871615, 
//				array([ 4.89406829]), array([ 2.25293948])))
//		(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
		
        //double FID = (double)lc_data.get(grid).get(LCData.FID);
        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
        actual = Ucan;
        expected = 1.0457344117123395;
        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
        expected = 22.587161876141426;
	    assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
        expected = 70.236952126338238;
	    assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
        expected = 23.824895125098408;
        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
        expected = 24.3976536329969;
        assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
        expected = -5.45522771410442;
        assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
        expected = 0.013430682487111713;
        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
        expected = 24.3903759871615;
        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
        expected = 4.89406829;
        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
        expected = 2.25293948;
        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
        
        
  ///////////////
        
        
        grid =2;
		counter = 2;
		i = 15;
		
		z_Uref=10.0;
		z_Hx2=1.0;
		Tb_rur=24.3903759871615;
		//dte=datetime.datetime(2009, 2, 5, 11, 0);			
		mod_U_TaRef= 4.89406829;			
		UTb=2.25293948;
		
		mod_rslts_prev=new ArrayList<ArrayList<Double>>();
		item = new ArrayList<Double> ();
		item.add(-999.);
		item.add(21.42487154);
		item.add(22.11778586);
		for (int x = 0;x<i;x++)
		{
			mod_rslts_prev.add(item);
		}				
		httc_rur=0.06318193;
		
		targetModule.mod_data_ts_ = mod_data_ts_;
		returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
				mod_rslts_prev, httc_rur);
		System.out.println(returnValues.toString());
		
        //this is 100% watr
//		('for_tab', (3.0, 1.0457344117123395, 18.919078476226751, 66.568868726423574, 23.824895125098408, 24.4066838698951, 
//				datetime.datetime(2009, 2, 5, 7, 30), -5.47771448495890, 0.013430682487111713, 24.3903759871615, 
//				array([ 4.89406829]), array([ 2.25293948])))
//		(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
		
        //double FID = (double)lc_data.get(grid).get(LCData.FID);
        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
        actual = Ucan;
        expected = 1.0457344117123395;
        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
        expected = 18.919078476226751;
	    assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
        expected = 66.568868726423574;
	    assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
        expected = 23.824895125098408;
        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
        expected = 24.4066838698951;
        assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
        expected = -5.47771448495890;
        assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
        expected = 0.013430682487111713;
        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
        expected = 24.3903759871615;
        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
        expected = 4.89406829;
        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
        expected = 2.25293948;
        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
        
        
 ///////////////
        
        
        grid =3;
		counter = 3;
		i = 15;
		
		z_Uref=10.0;
		z_Hx2=1.0;
		Tb_rur=24.3903759871615;
		//dte=datetime.datetime(2009, 2, 5, 11, 0);			
		mod_U_TaRef= 4.89406829;			
		UTb=2.25293948;
		
		mod_rslts_prev=new ArrayList<ArrayList<Double>>();
		item = new ArrayList<Double> ();
		item.add(-999.);
		item.add(21.42487154);
		item.add(22.11778586);
		item.add(21.14918844);
		for (int x = 0;x<i;x++)
		{
			mod_rslts_prev.add(item);
		}				
		httc_rur=0.06318193;
		
		targetModule.mod_data_ts_ = mod_data_ts_;
		returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
				mod_rslts_prev, httc_rur);
		System.out.println(returnValues.toString());
		
        //this is 100% conc
//		('for_tab', (4.0, 1.0457344117123395, 23.640161428479978, 71.2899516786768, 23.824895125098408, 24.3950497268880, 
//				datetime.datetime(2009, 2, 5, 7, 30), -5.44613037718157, 0.013430682487111713, 24.3903759871615, 
//				array([ 4.89406829]), array([ 2.25293948])))
//		(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
		
        //double FID = (double)lc_data.get(grid).get(LCData.FID);
        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
        actual = Ucan;
        expected = 1.0457344117123395;
        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
        expected = 23.640161428479978;
	    assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
        expected = 71.2899516786768;
	    assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
        expected = 23.824895125098408;
        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
        expected = 24.3950497268880;
        assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
        expected = -5.44613037718157;
        assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
        expected = 0.013430682487111713;
        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
        expected = 24.3903759871615;
        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
        expected = 4.89406829;
        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
        expected = 2.25293948;
        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
        
        
        
///////////////
        
        
  grid =4;
	counter = 4;
	i = 15;
	
	z_Uref=10.0;
	z_Hx2=1.0;
	Tb_rur=24.3903759871615;
	//dte=datetime.datetime(2009, 2, 5, 11, 0);			
	mod_U_TaRef= 4.89406829;			
	UTb=2.25293948;
	
	mod_rslts_prev=new ArrayList<ArrayList<Double>>();
	item = new ArrayList<Double> ();
	item.add(-999.);
	item.add(21.42487154);
	item.add(22.11778586);
	item.add(21.14918844);
	item.add(21.09639422);
	for (int x = 0;x<i;x++)
	{
		mod_rslts_prev.add(item);
	}				
	httc_rur=0.06318193;
	
	targetModule.mod_data_ts_ = mod_data_ts_;
	returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
			mod_rslts_prev, httc_rur);
	System.out.println(returnValues.toString());
	
  //this is 100% veg
//	('for_tab', (5.0, 1.0457344117123395, 22.368277825407251, 92.386345901011325, 23.824895125098408, 24.4032697631355, 
//			datetime.datetime(2009, 2, 5, 7, 30), -5.44437824457585, 0.013430682487111713, 24.3903759871615, 
//			array([ 4.89406829]), array([ 2.25293948])))
//	(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
	
  //double FID = (double)lc_data.get(grid).get(LCData.FID);
  FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
  Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
  actual = Ucan;
  expected = 1.0457344117123395;
  assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
  Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
  expected = 22.368277825407251;
  assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
  Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
  expected = 92.386345901011325;
  assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
  Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
  expected = 23.824895125098408;
  assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
  Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
  expected = 24.4032697631355;
  assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
  httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
  expected = -5.44437824457585;
  assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
  httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
  expected = 0.013430682487111713;
  assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
  Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
  expected = 24.3903759871615;
  assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
  mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
  expected = 4.89406829;
  assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
  UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
  expected = 2.25293948;
  assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
       
////////////////  
	  
	grid =5;
	counter = 5;
	i = 15;
	
	z_Uref=10.0;
	z_Hx2=1.0;
	Tb_rur=24.3903759871615;
	//dte=datetime.datetime(2009, 2, 5, 11, 0);			
	mod_U_TaRef= 4.89406829;			
	UTb=2.25293948;
	
	mod_rslts_prev=new ArrayList<ArrayList<Double>>();
	item = new ArrayList<Double> ();
	item.add(-999.);
	item.add(21.42487154);
	item.add(22.11778586);
	item.add(21.14918844);
	item.add(21.09639422);
	item.add(22.42321185);
	for (int x = 0;x<i;x++)
	{
		mod_rslts_prev.add(item);
	}				
	httc_rur=0.06318193;
	
	targetModule.mod_data_ts_ = mod_data_ts_;
	returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
			mod_rslts_prev, httc_rur);
	System.out.println(returnValues.toString());
	
	//this is 100% dry
//	('for_tab', (6.0, 1.0457344117123395, 16.997675899830039, 64.647466150026858, 23.824895125098408, 24.4113921630941, 
//			datetime.datetime(2009, 2, 5, 7, 30), -5.48745787745054, 0.013430682487111713, 24.3903759871615, 
//			array([ 4.89406829]), array([ 2.25293948])))
	//(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
	
	//double FID = (double)lc_data.get(grid).get(LCData.FID);
	FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	actual = Ucan;
	expected = 1.0457344117123395;
	assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	expected = 16.997675899830039;
	assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	expected = 64.647466150026858;
	assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	expected = 23.824895125098408;
	assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	expected = 24.4113921630941;
	assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
	httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	expected = -5.48745787745054;
	assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
	httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	expected = 0.013430682487111713;
	assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	expected = 24.3903759871615;
	assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	expected = 4.89406829;
	assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	expected = 2.25293948;
	assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
       
	
	
////////////////
	  
	grid =6;
	counter = 6;
	i = 15;
	
	z_Uref=10.0;
	z_Hx2=1.0;
	Tb_rur=24.3903759871615;
	//dte=datetime.datetime(2009, 2, 5, 11, 0);			
	mod_U_TaRef= 4.89406829;			
	UTb=2.25293948;
	
	mod_rslts_prev=new ArrayList<ArrayList<Double>>();
	item = new ArrayList<Double> ();
	item.add(-999.);
	item.add(21.42487154);
	item.add(22.11778586);
	item.add(21.14918844);
	item.add(21.09639422);
	item.add(22.42321185);
	item.add(22.17409962);
	for (int x = 0;x<i;x++)
	{
	mod_rslts_prev.add(item);
	}				
	httc_rur=0.06318193;
	
	targetModule.mod_data_ts_ = mod_data_ts_;
	returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
	mod_rslts_prev, httc_rur);
	System.out.println(returnValues.toString());
	
	//this is 100% irr
//	('for_tab', (7.0, 1.0457344117123395, 18.57390781415792, 66.223698064354735, 23.824895125098408, 24.4075307653541, 
//			datetime.datetime(2009, 2, 5, 7, 30), -5.47951860010206, 0.013430682487111713, 24.3903759871615, 
//			array([ 4.89406829]), array([ 2.25293948])))
	//(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
	
	//double FID = (double)lc_data.get(grid).get(LCData.FID);
	FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	actual = Ucan;
	expected = 1.0457344117123395;
	assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	expected = 18.57390781415792;
	assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	expected = 66.223698064354735;
	assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	expected = 23.824895125098408;
	assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	expected = 24.4075307653541;
	assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
	httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	expected = -5.47951860010206;
	assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
	httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	expected = 0.013430682487111713;
	assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	expected = 24.3903759871615;
	assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	expected = 4.89406829;
	assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	expected = 2.25293948;
	assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 

	
////////////////
		  
	grid =7;
	counter = 7;
	i = 15;
	
	z_Uref=10.0;
	z_Hx2=1.0;
	Tb_rur=24.3903759871615;
	//dte=datetime.datetime(2009, 2, 5, 11, 0);			
	mod_U_TaRef= 4.89406829;			
	UTb=2.25293948;
	
	mod_rslts_prev=new ArrayList<ArrayList<Double>>();
	item = new ArrayList<Double> ();
	item.add(-999.);
	item.add(21.42487154);
	item.add(22.11778586);
	item.add(21.14918844);
	item.add(21.09639422);
	item.add(22.42321185);
	item.add(22.17409962);
	item.add(22.00069233);
	for (int x = 0;x<i;x++)
	{
	mod_rslts_prev.add(item);
	}				
	httc_rur=0.06318193;
	
	targetModule.mod_data_ts_ = mod_data_ts_;
	returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
	mod_rslts_prev, httc_rur);
	System.out.println(returnValues.toString());
	
	//this is a mix
//	('for_tab', (8.0, 1.0457344117123395, 20.043481173949033, 64.932640516521076, 23.824895125098408, 24.4061510473430, 
//			datetime.datetime(2009, 2, 5, 7, 30), -5.47462119513880, 0.013430682487111713, 24.3903759871615, 
//			array([ 4.89406829]), array([ 2.25293948])))
	//(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
	
	//double FID = (double)lc_data.get(grid).get(LCData.FID);
	FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	actual = Ucan;
	expected = 1.0457344117123395;
	assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	expected = 20.043481173949033;
	assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	expected = 64.932640516521076;
	assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	expected = 23.824895125098408;
	assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	expected = 24.4061510473430;
	assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
	httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	expected = -5.47462119513880;
	assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); //TODO
	httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	expected = 0.013430682487111713;
	assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	expected = 24.3903759871615;
	assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	expected = 4.89406829;
	assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	expected = 2.25293948;
	assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 

	}
	
//('lc_data', FID roof  road  watr  conc  Veg  dry  irr  H    W
//	0    1   1.0   0.0   0.0   0.0  0.0  0.0  0.0  0.5  1
//	1    2   0.0   1.0   0.0   0.0  0.0  0.0  0.0  0.5  1
//	2    3   0.0   0.0   1.0   0.0  0.0  0.0  0.0  0.5  1
//	3    4   0.0   0.0   0.0   1.0  0.0  0.0  0.0  0.5  1
//	4    5   0.0   0.0   0.0   0.0  1.0  0.0  0.0  0.5  1
//	5    6   0.0   0.0   0.0   0.0  0.0  1.0  0.0  0.5  1
//	6    7   0.0   0.0   0.0   0.0  0.0  0.0  1.0  0.5  1
//	7    8   0.1   0.1   0.1   0.1  0.1  0.1  0.4  0.5  1)

}
