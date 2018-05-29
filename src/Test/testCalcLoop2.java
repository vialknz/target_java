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

public class testCalcLoop2
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

	@Test
	public void test()
	{
		//fail("Not yet implemented");
	}
	

	 @Test 
	 public void testCalcLoop2_()
	 {
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
		 
		 
			
			
			grid =0;
			counter = 0;
			i = 0;
			mod_data_ts_= new double[8][10][11];
			mod_data_ts_[0][0][0]=20.0;
			mod_data_ts_[0][0][1]=20.0;
			mod_data_ts_[0][0][2]=20.0;
			mod_data_ts_[0][0][3]=20.0;
			mod_data_ts_[0][0][4]=20.0;
			mod_data_ts_[0][0][5]=0.0;
			mod_data_ts_[0][0][6]=20.0;
			mod_data_ts_[0][0][7]=20.0;
			mod_data_ts_[0][0][8]=20.0;
			mod_data_ts_[0][0][9]=0.0;
			mod_data_ts_[0][0][10]=0.0;
			
			mod_data_ts_[0][1][0]=20.0;
			mod_data_ts_[0][1][1]=20.0;
			mod_data_ts_[0][1][2]=20.0;
			mod_data_ts_[0][1][3]=20.0;
			mod_data_ts_[0][1][4]=20.0;
			mod_data_ts_[0][1][5]=0.0;
			mod_data_ts_[0][1][6]=20.0;
			mod_data_ts_[0][1][7]=20.0;
			mod_data_ts_[0][1][8]=20.0;
			mod_data_ts_[0][1][9]=0.0;
			mod_data_ts_[0][1][10]=0.0;
			
			mod_data_ts_[0][2][0]=20.0;
			mod_data_ts_[0][2][1]=20.0;
			mod_data_ts_[0][2][2]=20.0;
			mod_data_ts_[0][2][3]=20.0;
			mod_data_ts_[0][2][4]=20.0;
			mod_data_ts_[0][2][5]=0.0;
			mod_data_ts_[0][2][6]=20.0;
			mod_data_ts_[0][2][7]=20.0;
			mod_data_ts_[0][2][8]=20.0;
			mod_data_ts_[0][2][9]=0.0;
			mod_data_ts_[0][2][10]=0.0;
			
			mod_data_ts_[0][3][0]=20.0;
			mod_data_ts_[0][3][1]=20.0;
			mod_data_ts_[0][3][2]=20.0;
			mod_data_ts_[0][3][3]=20.0;
			mod_data_ts_[0][3][4]=20.0;
			mod_data_ts_[0][3][5]=0.0;
			mod_data_ts_[0][3][6]=20.0;
			mod_data_ts_[0][3][7]=20.0;
			mod_data_ts_[0][3][8]=20.0;
			mod_data_ts_[0][3][9]=0.0;
			mod_data_ts_[0][3][10]=0.0;
			
			mod_data_ts_[0][4][0]=20.0;
			mod_data_ts_[0][4][1]=20.0;
			mod_data_ts_[0][4][2]=20.0;
			mod_data_ts_[0][4][3]=20.0;
			mod_data_ts_[0][4][4]=20.0;
			mod_data_ts_[0][4][5]=0.0;
			mod_data_ts_[0][4][6]=20.0;
			mod_data_ts_[0][4][7]=20.0;
			mod_data_ts_[0][4][8]=20.0;
			mod_data_ts_[0][4][9]=0.0;
			mod_data_ts_[0][4][10]=0.0;
			
			mod_data_ts_[0][5][0]=20.0;
			mod_data_ts_[0][5][1]=20.0;
			mod_data_ts_[0][5][2]=20.0;
			mod_data_ts_[0][5][3]=20.0;
			mod_data_ts_[0][5][4]=20.0;
			mod_data_ts_[0][5][5]=0.0;
			mod_data_ts_[0][5][6]=20.0;
			mod_data_ts_[0][5][7]=20.0;
			mod_data_ts_[0][5][8]=20.0;
			mod_data_ts_[0][5][9]=0.0;
			mod_data_ts_[0][5][10]=0.0;
			
			mod_data_ts_[0][6][0]=20.0;
			mod_data_ts_[0][6][1]=20.0;
			mod_data_ts_[0][6][2]=20.0;
			mod_data_ts_[0][6][3]=20.0;
			mod_data_ts_[0][6][4]=20.0;
			mod_data_ts_[0][6][5]=0.0;
			mod_data_ts_[0][6][6]=20.0;
			mod_data_ts_[0][6][7]=20.0;
			mod_data_ts_[0][6][8]=20.0;
			mod_data_ts_[0][6][9]=0.0;
			mod_data_ts_[0][6][10]=0.0;
			
			mod_data_ts_[0][7][0]=20.0;
			mod_data_ts_[0][7][1]=20.0;
			mod_data_ts_[0][7][2]=20.0;
			mod_data_ts_[0][7][3]=20.0;
			mod_data_ts_[0][7][4]=20.0;
			mod_data_ts_[0][7][5]=0.0;
			mod_data_ts_[0][7][6]=20.0;
			mod_data_ts_[0][7][7]=20.0;
			mod_data_ts_[0][7][8]=20.0;
			mod_data_ts_[0][7][9]=0.0;
			mod_data_ts_[0][7][10]=0.0;
			
			mod_data_ts_[0][8][0]=20.0;
			mod_data_ts_[0][8][1]=20.0;
			mod_data_ts_[0][8][2]=20.0;
			mod_data_ts_[0][8][3]=20.0;
			mod_data_ts_[0][8][4]=20.0;
			mod_data_ts_[0][8][5]=0.0;
			mod_data_ts_[0][8][6]=20.0;
			mod_data_ts_[0][8][7]=20.0;
			mod_data_ts_[0][8][8]=20.0;
			mod_data_ts_[0][8][9]=0.0;
			mod_data_ts_[0][8][10]=0.0;
			
			mod_data_ts_[0][9][0]=20.0;
			mod_data_ts_[0][9][1]=20.0;
			mod_data_ts_[0][9][2]=20.0;
			mod_data_ts_[0][9][3]=20.0;
			mod_data_ts_[0][9][4]=20.0;
			mod_data_ts_[0][9][5]=0.0;
			mod_data_ts_[0][9][6]=20.0;
			mod_data_ts_[0][9][7]=20.0;
			mod_data_ts_[0][9][8]=20.0;
			mod_data_ts_[0][9][9]=0.0;
			mod_data_ts_[0][9][10]=0.0;
			
			z_Uref=10.0;
			z_Hx2=2.0;
			Tb_rur=21.8093381484548;
			//dte=datetime.datetime(2009, 2, 5, 11, 0);			
			mod_U_TaRef=3.05879268;
			UTb=2.63038178;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			ArrayList<Double> item = new ArrayList<Double> ();
			item.add(3.05879268);
			mod_rslts_prev.add(item);
			httc_rur=0.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        
	        targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
//			(lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
//			('for_tab', (1.0, 0.65358400732021216, 20.037254901960782, 58.838000000000001, 20.0, 21.6149098471060, 
//			datetime.datetime(2009, 2, 5, 0, 0), 0.304020953422442, 0.012064535975946408, 21.8093381484548, 
//			array([ 3.05879268]), array([ 2.63038178])))


			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        double FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        double Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 0.65358400732021216;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        double Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        assertEquals(20.037254901960782, Tsurf_horz, Math.abs(20.981818181818181) / eightDigitDelta);
	        double Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        assertEquals(58.838000000000001, Tsurf_can, Math.abs(59.079999999999998) / eightDigitDelta);
	        double Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        assertEquals(20.0, Tsurf_wall, Math.abs(20.0) / eightDigitDelta);
	        double Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        assertEquals(21.6149098471060, Tac, Math.abs(26.3333468389778) / eightDigitDelta);
//	        //double dteDouble = (double)dte.getTime();
//	        double dteDouble = returnValues.get(TargetModule.FOR_TAB_dte_INDEX);
	        double httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        assertEquals(0.304020953422442, httc_urb_new, Math.abs(-10.3628248419620) / eightDigitDelta);
	        double httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        assertEquals(0.012064535975946408, httc_can, Math.abs(0.016618357679830758) / eightDigitDelta);
	        double Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        assertEquals(21.8093381484548, Tb_rur_return, Math.abs(26.3028510110944) / eightDigitDelta);
	        double mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        assertEquals(3.05879268, mod_U_TaRef_return, Math.abs(9.17637804) / eightDigitDelta);
	        double UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        assertEquals(2.63038178, UTb_return, Math.abs(4.22426152) / eightDigitDelta);
			
			
			
			
			
			
			
			
			
			
			
			

//('pre_calcloop', 0, 0, 15, 10.0, 2.0, 25.2686468248213, datetime.datetime(2009, 2, 5, 7, 30), array([[ 3.05879268],
//       [ 5.50582682],
//       [ 6.7293439 ],
//       [ 0.1       ],
//       [ 0.1       ],
//       [ 0.1       ],
//       [ 4.89406829],
//       [ 2.44703414],
//       [ 0.1       ],
//       [ 3.05879268],
//       [ 4.89406829],
//       [ 0.1       ],
//       [ 3.05879268],
//       [ 4.89406829],
//       [ 0.1       ],
//       [ 4.89406829],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ],
//       [ 0.        ]]), array([ 4.20861085]), 
	        //array([[[ ( 1,  0.01760478,  22.16006737,  68.04196619,  23.66598826,  22.7597952 , 
//	        datetime.datetime(2009, 2, 5, 7, 0),   1.41281771e-02,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 2,  0.01760478,  16.97067943,  64.30265596,  23.66598826,  21.43439113, datetime.datetime(2009, 2, 5, 7, 0),   1.01819734e-05,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 3,  0.01760478,  19.72295176,  46.22885862,  23.66598826,  21.8716416 , datetime.datetime(2009, 2, 5, 7, 0),   4.63187978e-02,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 4,  0.01760478,  18.39053309,  52.94287595,  23.66598826,  21.43565294, datetime.datetime(2009, 2, 5, 7, 0),   5.35595978e-05,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 5,  0.01760478,  21.15056879,  64.38386472,  23.66598826,  22.51391542, datetime.datetime(2009, 2, 5, 7, 0),   1.28451305e-02,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 6,  0.01760478,  19.28451502,  45.79042188,  23.66598826,  21.77763915, datetime.datetime(2009, 2, 5, 7, 0),   4.61971238e-02,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 7,  0.01760478,  18.83904278,  50.26653772,  23.66598826,  21.77390225, datetime.datetime(2009, 2, 5, 7, 0),   4.55695899e-02,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 8,  0.01760478,  19.21681122,  56.32792102,  23.66598826,  21.89183668, datetime.datetime(2009, 2, 5, 7, 0),   8.04749730e-04,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ ( 9,  0.01760478,  18.39053309,  52.94287595,  23.66598826,  21.43565294, datetime.datetime(2009, 2, 5, 7, 0),   5.35595978e-05,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ (10,  0.01760478,  18.39053309,  52.94287595,  23.66598826,  21.43565294, datetime.datetime(2009, 2, 5, 7, 0),   5.35595978e-05,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ (11,  0.01760478,  19.29622064,  56.60108674,  23.66598826,  21.91607682, datetime.datetime(2009, 2, 5, 7, 0),   1.49434329e-03,  0.00984896,  21.93490922,  0.1,  0.1)],
//        [ (12,  0.01760478,  22.41997872,  68.35683421,  23.66598826,  22.7965937 , datetime.datetime(2009, 2, 5, 7, 0),   1.42856573e-02,  0.00984896,  21.93490922,  0.1,  0.1)]]],
//      dtype=[('ID', '<i4'), ('Ws', '<f8'), ('Ts', '<f8'), ('Tsurf_can', '<f8'), ('Tsurf_wall', '<f8'), ('Ta', '<f8'), ('date', 'O'), ('httc_urb', '<f8'), ('httc_can', '<f8'), ('Tb_rur', '<f8'), ('mod_U_TaRef', '<f8'), ('UTb', '<f8')]))
//('mod_data_ts_[i]', array([ ( 24.31229431,  22.48292802,  23.52727076,  19.09834395,  24.48659184,  0.,  17.37919191,  18.84544421,  21.87745907,  0., 0),
//       ( 24.06182159,  22.48292802,  23.27523749,  19.05295674,  24.26312145,  0.,  17.27222606,  18.77362668,  21.86388928,  0., 0),
//       ( 23.82489513,  22.48292802,  23.03489231,  19.00795165,  24.04775074,  0.,  17.17331177,  18.70449329,  21.85039236,  0., 0),
//       ( 23.60068967,  22.48292802,  22.805703  ,  18.96332633,  23.84019037,  0.,  17.08195095,  18.63795041,  21.836968  ,  0., 0),
//       ( 23.38843591,  22.48292802,  22.58716188,  18.91907848,  23.64016143,  0.,  16.9976759 ,  18.57390781,  21.82361589,  0., 0),
//       ( 23.18741626,  22.48292802,  22.37878463,  18.87520577,  23.44739509,  0.,  16.92004738,  18.51227854,  21.81033571,  0., 0),
//       ( 22.99696095,  22.48292802,  22.18010915,  18.83170591,  23.26163218,  0.,  16.84865284,  18.45297875,  21.79712715,  0., 0),
//       ( 22.81644446,  22.48292802,  21.99069449,  18.78857661,  23.08262283,  0.,  16.78310478,  18.3959276 ,  21.78398991,  0., 0),
//       ( 22.64528232,  22.48292802,  21.81011986,  18.7458156 ,  22.91012613,  0.,  16.72303922,  18.34104716,  21.77092367,  0., 0),
//       ( 22.48292802,  22.48292802,  21.63798362,  18.7034206 ,  22.74390978,  0.,  16.66811419,  18.28826227,  21.75792813,  0., 0)],
//      dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]))
//('mod_data_ts_[i-1]', array([ ( 24.17555275,  22.22939669,  23.42184869,  19.10426263,  24.35280063,   0.        ,  17.44227534,  18.87272138,  21.87970829,  0., 0),
//       ( 23.91439793,  22.22939669,  23.16376044,  19.05732503,  24.12668253,   0.        ,  17.31304347,  18.78994503,  21.86734329,  0., 0),
//       ( 23.66598826,  22.22939669,  22.91623229,  19.01072875,  23.9078125 ,   0.        ,  17.19165306,  18.70977009,  21.85503617,  0., 0),
//       ( 23.42961123,  22.22939669,  22.67883036,  18.96447192,  23.6959575 ,   0.        ,  17.07766874,  18.63211465,  21.84278671,  0., 0),
//       ( 23.20459907,  22.22939669,  22.45113866,  18.9185527 ,  23.49089198,   0.        ,  16.97067943,  18.5568995 ,  21.83059471,  0., 0),
//       ( 22.99032562,  22.22939669,  22.23275833,  18.87296922,  23.29239764,   0.        ,  16.87029699,  18.48404812,  21.81845994,  0., 0),
//       ( 22.78620345,  22.22939669,  22.02330694,  18.82771965,  23.10026317,   0.        ,  16.7761549 ,  18.41348647,  21.80638221,  0., 0),
//       ( 22.59168119,  22.22939669,  21.82241773,  18.78280215,  22.91428401,   0.        ,  16.68790709,  18.34514302,  21.79436131,  0., 0),
//       ( 22.40624109,  22.22939669,  21.62973899,  18.7382149 ,  22.73426213,   0.        ,  16.60522674,  18.27894857,  21.78239702,  0., 0),
//       ( 22.22939669,  22.22939669,  21.4449334 ,  18.69395608,  22.56000584,  22.04895493,  16.52780523,  18.21483622,  21.77048914,  0., 0)],
//      dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]))
//('for_tab', (1.0, 1.0457344117123395, 22.306635162414288, 68.496566505851519, 23.824895125098408, 25.0703227913955, datetime.datetime(2009, 2, 5, 7, 30), 0.366982271181424, 0.013430682487111713, 25.2686468248213, array([ 4.89406829]), array([ 4.20861085])))
//2009-02-05 08:00:00 16	
			
		
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
			
//		       ( ),

			
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
			
//		       ( ,  0., 0)],
			
			z_Uref=10.0;
			z_Hx2=2.0;
			Tb_rur=25.2686468248213;
			//dte=datetime.datetime(2009, 2, 5, 11, 0);			
			mod_U_TaRef=4.89406829;			
			UTb=4.20861085;
			

			
			//('mod_data_ts_[i-1]', array([ 
//			( 24.17555275,  22.22939669,  23.42184869,  19.10426263,  24.35280063,   0.        ,  17.44227534,  18.87272138,  21.87970829,  0., 0),
//	       ( 23.91439793,  22.22939669,  23.16376044,  19.05732503,  24.12668253,   0.        ,  17.31304347,  18.78994503,  21.86734329,  0., 0),
//	       ( 23.66598826,  22.22939669,  22.91623229,  19.01072875,  23.9078125 ,   0.        ,  17.19165306,  18.70977009,  21.85503617,  0., 0),
//	       ( 23.42961123,  22.22939669,  22.67883036,  18.96447192,  23.6959575 ,   0.        ,  17.07766874,  18.63211465,  21.84278671,  0., 0),
//	       ( 23.20459907,  22.22939669,  22.45113866,  18.9185527 ,  23.49089198,   0.        ,  16.97067943,  18.5568995 ,  21.83059471,  0., 0),
//	       ( 22.99032562,  22.22939669,  22.23275833,  18.87296922,  23.29239764,   0.        ,  16.87029699,  18.48404812,  21.81845994,  0., 0),
//	       ( 22.78620345,  22.22939669,  22.02330694,  18.82771965,  23.10026317,   0.        ,  16.7761549 ,  18.41348647,  21.80638221,  0., 0),
//	       ( 22.59168119,  22.22939669,  21.82241773,  18.78280215,  22.91428401,   0.        ,  16.68790709,  18.34514302,  21.79436131,  0., 0),
//	       ( 22.40624109,  22.22939669,  21.62973899,  18.7382149 ,  22.73426213,   0.        ,  16.60522674,  18.27894857,  21.78239702,  0., 0),
//	       ( 22.22939669,  22.22939669,  21.4449334 ,  18.69395608,  22.56000584,  22.04895493,  16.52780523,  18.21483622,  21.77048914,  0., 0)],
			
//			('mod_rslts_prev[0][grid][Ta]', array([ 22.7597952]))
			
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}				
			httc_rur=0.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        
	        targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			// this is roof
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
			//('for_tab', (1.0, 1.0457344117123395, 22.306635162414288, 68.496566505851519, 23.824895125098408, 25.0703227913955, 
//			datetime.datetime(2009, 2, 5, 7, 30), 0.366982271181424, 0.013430682487111713, 25.2686468248213, array([ 4.89406829]), array([ 4.20861085])))
			//2009-02-05 08:00:00 16	
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 22.306635162414288;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 68.496566505851519;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 25.0703227913955;
	        assertEquals(expected, Tac, Math.abs(expected) / threeDigitDelta);  //TODO
//	        //double dteDouble = (double)dte.getTime();
//	        double dteDouble = returnValues.get(TargetModule.FOR_TAB_dte_INDEX);
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 0.366982271181424;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / oneDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
			
			
			
			
	        
	        grid =1;
			counter = 1;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			//  this is road
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (2.0, 1.0457344117123395, 16.997675899830039, 64.647466150026858, 23.824895125098408, 24.7239701694931, 
//					datetime.datetime(2009, 2, 5, 7, 30), 0.234854544973042, 0.013430682487111713, 25.2686468248213,
//					array([ 4.89406829]), array([ 4.20861085])))
			
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
	        expected = 24.7239701694931;
	        assertEquals(expected, Tac, Math.abs(expected) / eightDigitDelta);  
//	        //double dteDouble = (double)dte.getTime();
//	        double dteDouble = returnValues.get(TargetModule.FOR_TAB_dte_INDEX);
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 0.234854544973042;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / eightDigitDelta); 
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
			
	        //////////////////
	        
	        
	        grid =2;
			counter = 2;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			// this is watr
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (3.0, 1.0457344117123395, 19.858345712989532, 46.542228253099751, 23.824895125098408, 25.1910391755193, 
//					datetime.datetime(2009, 2, 5, 7, 30), 2.11850735983298, 0.013430682487111713, 25.2686468248213, 
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 19.858345712989532;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 46.542228253099751;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 25.1910391755193;
	        assertEquals(expected, Tac, Math.abs(expected) / twoDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 2.11850735983298;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	        
	        

	        grid =3;
			counter = 3;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			item.add(21.43565294);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			// this is conc
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (4.0, 1.0457344117123395, 18.478693973613531, 53.263040856257206, 23.824895125098408, 24.6987702942643, 
//					datetime.datetime(2009, 2, 5, 7, 30), 0.240983040119928, 0.013430682487111713, 25.2686468248213,
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 18.478693973613531;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 53.263040856257206;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 24.6987702942643;
	        assertEquals(expected, Tac, Math.abs(expected) / oneDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 0.240983040119928;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	       /////////////////////////
	        
	        grid =4;
			counter = 4;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			item.add(21.43565294);
			item.add(22.51391542);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			//this is Veg
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (5.0, 1.0457344117123395, 21.283731818433274, 64.807054998163409, 23.824895125098408, 25.0193882052902, 
//					datetime.datetime(2009, 2, 5, 7, 30), 0.357379311642202, 0.013430682487111713, 25.2686468248213, 
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 21.283731818433274;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 64.807054998163409;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 25.0193882052902;
	        assertEquals(expected, Tac, Math.abs(expected) / twoDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 0.357379311642202;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	        
	        ////////////////
	        
	        grid =5;
			counter = 5;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			item.add(21.43565294);
			item.add(22.51391542);
			item.add(21.77763915);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
			//this is dry
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (6.0, 1.0457344117123395, 19.411186834884621, 46.095069374994836, 23.824895125098408, 25.1858444321011, 
//					datetime.datetime(2009, 2, 5, 7, 30), 2.11192463210414, 0.013430682487111713, 25.2686468248213,
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 19.411186834884621;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 46.095069374994836;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 25.1858444321011;
	        assertEquals(expected, Tac, Math.abs(expected) / twoDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 2.11192463210414;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	        
	        

	        
	        ////////////////
	        
	        grid =6;
			counter = 6;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			item.add(21.43565294);
			item.add(22.51391542);
			item.add(21.77763915);
			item.add(21.77390225);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
	        //this is irr
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (7.0, 1.0457344117123395, 18.938775156741226, 50.577024473438541, 23.824895125098408, 25.1906594595663,
//					datetime.datetime(2009, 2, 5, 7, 30), 2.10578981239196, 0.013430682487111713, 25.2686468248213, 
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 18.938775156741226;
	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 50.577024473438541;
	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 25.1906594595663;
	        assertEquals(expected, Tac, Math.abs(expected) / twoDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 2.10578981239196;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	        
	        
	        ////////////////
	        
	        grid =7;
			counter = 7;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			item = new ArrayList<Double> ();
			item.add(22.7597952);
			item.add(21.43439113);
			item.add(21.8716416);
			item.add(21.43565294);
			item.add(22.51391542);
			item.add(21.77763915);
			item.add(21.77390225);
			item.add(21.89183668);
			for (int x = 0;x<i;x++)
			{
				mod_rslts_prev.add(item);
			}
			
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, 
					mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
	        //this is a mix
			//  (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb) 
//			('for_tab', (8.0, 1.0457344117123395, 19.312107277517665, 56.672064745446356, 23.824895125098408, 24.7879200265041, 
//					datetime.datetime(2009, 2, 5, 7, 30), 0.250282274520443, 0.013430682487111713, 25.2686468248213, 
//					array([ 4.89406829]), array([ 4.20861085])))
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.0457344117123395;
	        assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
	        Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        expected = 19.312107277517665;
//	        assertEquals(expected, Tsurf_horz, Math.abs(expected) / eightDigitDelta);
	        Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        expected = 56.672064745446356;
//	        assertEquals(expected, Tsurf_can, Math.abs(expected) / eightDigitDelta);
	        Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        expected = 23.824895125098408;
	        assertEquals(expected, Tsurf_wall, Math.abs(expected) / eightDigitDelta);
	        Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        expected = 24.7879200265041;
	        assertEquals(expected, Tac, Math.abs(expected) / oneDigitDelta);  
	        httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        expected = 0.250282274520443;
	        assertEquals(expected, httc_urb_new, Math.abs(expected) / halfDigitDelta); //TODO
	        httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        expected = 0.013430682487111713;
	        assertEquals(expected, httc_can, Math.abs(expected) / eightDigitDelta);
	        Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        expected = 25.2686468248213;
	        assertEquals(expected, Tb_rur_return, Math.abs(expected) / eightDigitDelta); 
	        mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        expected = 4.89406829;
	        assertEquals(expected, mod_U_TaRef_return, Math.abs(expected) / eightDigitDelta); 
	        UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        expected = 4.20861085;
	        assertEquals(expected, UTb_return, Math.abs(expected) / eightDigitDelta); 
	        
	        
	        
	        
//	        ('pre_calcloop', 0, 0, 15, 10.0, 1.0, 24.3903759871615, datetime.datetime(2009, 2, 5, 7, 30), 
//	        		array([[ 3.05879268],
//                     [ 5.50582682],
//                     [ 6.7293439 ],
//                     [ 0.1       ],
//                     [ 0.1       ],
//                     [ 0.1       ],
//                     [ 4.89406829],
//                     [ 2.44703414],
//                     [ 0.1       ],
//                     [ 3.05879268],
//                     [ 4.89406829],
//                     [ 0.1       ],
//                     [ 3.05879268],
//                     [ 4.89406829],
//                     [ 0.1       ],
//                     [ 4.89406829],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ],
//                     [ 0.        ]]), array([ 2.25293948]), array([[[ (1,  0.01760478,  22.22939669,  22.22939669,  23.66598826, -999.        , datetime.datetime(2009, 2, 5, 7, 0), -0.02880961,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (2,  0.01760478,  22.45113866,  69.78311518,  23.66598826,   21.42487154, datetime.datetime(2009, 2, 5, 7, 0), -0.07954706,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (3,  0.01760478,  18.9185527 ,  66.25052922,  23.66598826,   22.11778586, datetime.datetime(2009, 2, 5, 7, 0), -0.09299225,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (4,  0.01760478,  23.49089198,  70.82286851,  23.66598826,   21.14918844, datetime.datetime(2009, 2, 5, 7, 0), -0.07584685,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (5,  0.01760478,  22.79629125,  92.92455903,  23.66598826,   21.09639422, datetime.datetime(2009, 2, 5, 7, 0), -0.0832261 ,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (6,  0.01760478,  16.97067943,  64.30265596,  23.66598826,   22.42321185, datetime.datetime(2009, 2, 5, 7, 0), -0.09238099,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (7,  0.01760478,  18.5568995 ,  65.88887603,  23.66598826,   22.17409962, datetime.datetime(2009, 2, 5, 7, 0), -0.09282944,  0.00984896,  22.10689515,  0.1,  0.1)],
//                      [ (8,  0.01760478,  19.99114583,  64.58903929,  23.66598826,   22.00069233, datetime.datetime(2009, 2, 5, 7, 0), -0.08061056,  0.00984896,  22.10689515,  0.1,  0.1)]]],
//                    dtype=[('ID', '<i4'), ('Ws', '<f8'), ('Ts', '<f8'), ('Tsurf_can', '<f8'), ('Tsurf_wall', '<f8'), ('Ta', '<f8'), ('date', 'O'), ('httc_urb', '<f8'), ('httc_can', '<f8'), ('Tb_rur', '<f8'), ('mod_U_TaRef', '<f8'), ('UTb', '<f8')]))
//              ('mod_data_ts_[i]', array([ ( 24.31229431,  22.48292802,  23.52727076,  19.09834395,  24.48659184,  0.,  17.37919191,  18.84544421,  21.87745907,  0., 0),
//                     ( 24.06182159,  22.48292802,  23.27523749,  19.05295674,  24.26312145,  0.,  17.27222606,  18.77362668,  21.86388928,  0., 0),
//                     ( 23.82489513,  22.48292802,  23.03489231,  19.00795165,  24.04775074,  0.,  17.17331177,  18.70449329,  21.85039236,  0., 0),
//                     ( 23.60068967,  22.48292802,  22.805703  ,  18.96332633,  23.84019037,  0.,  17.08195095,  18.63795041,  21.836968  ,  0., 0),
//                     ( 23.38843591,  22.48292802,  22.58716188,  18.91907848,  23.64016143,  0.,  16.9976759 ,  18.57390781,  21.82361589,  0., 0),
//                     ( 23.18741626,  22.48292802,  22.37878463,  18.87520577,  23.44739509,  0.,  16.92004738,  18.51227854,  21.81033571,  0., 0),
//                     ( 22.99696095,  22.48292802,  22.18010915,  18.83170591,  23.26163218,  0.,  16.84865284,  18.45297875,  21.79712715,  0., 0),
//                     ( 22.81644446,  22.48292802,  21.99069449,  18.78857661,  23.08262283,  0.,  16.78310478,  18.3959276 ,  21.78398991,  0., 0),
//                     ( 22.64528232,  22.48292802,  21.81011986,  18.7458156 ,  22.91012613,  0.,  16.72303922,  18.34104716,  21.77092367,  0., 0),
//                     ( 22.48292802,  22.48292802,  21.63798362,  18.7034206 ,  22.74390978,  0.,  16.66811419,  18.28826227,  21.75792813,  0., 0)],
//                    dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]))
//              ('mod_data_ts_[i-1]', array([ ( 24.17555275,  22.22939669,  23.42184869,  19.10426263,  24.35280063,   0.       ,  17.44227534,  18.87272138,  21.87970829,  0., 0),
//                     ( 23.91439793,  22.22939669,  23.16376044,  19.05732503,  24.12668253,   0.       ,  17.31304347,  18.78994503,  21.86734329,  0., 0),
//                     ( 23.66598826,  22.22939669,  22.91623229,  19.01072875,  23.9078125 ,   0.       ,  17.19165306,  18.70977009,  21.85503617,  0., 0),
//                     ( 23.42961123,  22.22939669,  22.67883036,  18.96447192,  23.6959575 ,   0.       ,  17.07766874,  18.63211465,  21.84278671,  0., 0),
//                     ( 23.20459907,  22.22939669,  22.45113866,  18.9185527 ,  23.49089198,   0.       ,  16.97067943,  18.5568995 ,  21.83059471,  0., 0),
//                     ( 22.99032562,  22.22939669,  22.23275833,  18.87296922,  23.29239764,   0.       ,  16.87029699,  18.48404812,  21.81845994,  0., 0),
//                     ( 22.78620345,  22.22939669,  22.02330694,  18.82771965,  23.10026317,   0.       ,  16.7761549 ,  18.41348647,  21.80638221,  0., 0),
//                     ( 22.59168119,  22.22939669,  21.82241773,  18.78280215,  22.91428401,   0.       ,  16.68790709,  18.34514302,  21.79436131,  0., 0),
//                     ( 22.40624109,  22.22939669,  21.62973899,  18.7382149 ,  22.73426213,   0.       ,  16.60522674,  18.27894857,  21.78239702,  0., 0),
//                     ( 22.22939669,  22.22939669,  21.4449334 ,  18.69395608,  22.56000584,  22.1069891,  16.52780523,  18.21483622,  21.77048914,  0., 0)],
//                    dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]))
//              ('mod_rslts_prev[0][grid][Ta]', array([-999.]))
//              ('for_tab', (1.0, 1.0457344117123395, 22.482928024954088, 22.482928024954088, 23.824895125098408, -999.0, datetime.datetime(2009, 2, 5, 7, 30), -5.48128676708857, 0.013430682487111713, 24.3903759871615, array([ 4.89406829]), array([ 2.25293948])))
//              2009-02-05 08:00:00 16
//			
	        
	        
	        
	 }
	 

}
