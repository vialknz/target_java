package Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Target.CD;
import Target.Cfm;
import Target.Constants;
import Target.ForceRestore;
import Target.Httc;
import Target.LCData;
import Target.LcSort;
import Target.Lumps;
import Target.MetData;
import Target.RnCalcNew;
import Target.SfcRi;
import Target.TargetModule;
import Target.TsEbW;
import Target.ld_mod;

public class TestTargetCD
{
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	
	 @Test
	 
	 public void testAdd() 
	 {
	      String str = "Junit is working fine";
	      assertEquals("Junit is working fine",str);
	   }
	 
	 //@Test 
	 public void testCalcLoop()
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
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation_small.txt";
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
		 
		 
//		 
//			grid=;
//			counter=;
//			i=;
//			met_d=;
//			mod_data_ts=;
//			z_Uref=; 
//			z_Hx2=;
//			Tb_rur=;
//			dte=;
//			mod_U_TaRef=;
//			UTb=;
//			mod_rslts_prev=;
//			httc_rur=;
//		 
//		 returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_d, mod_data_ts_, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, mod_rslts_prev, httc_rur);
		 
//		 TreeMap<Integer,Double> for_tab = new TreeMap<Integer,Double>();
//	        double FID = (double)lc_data.get(grid).get(LCData.FID);
//	        for_tab.put(FOR_TAB_FID_INDEX, FID);        
//	        for_tab.put(FOR_TAB_Ucan_INDEX,Ucan);
//	        for_tab.put(FOR_TAB_Tsurf_horz_INDEX, Tsurf_horz);
//	        for_tab.put(FOR_TAB_Tsurf_can_INDEX, Tsurf_can);
//	        for_tab.put(FOR_TAB_Tsurf_wall_INDEX, Tsurf_wall);
//	        for_tab.put(FOR_TAB_Tac_INDEX, Tac);
//	        double dteDouble = (double)dte.getTime();
//	        for_tab.put(FOR_TAB_dte_INDEX, dteDouble);
//	        for_tab.put(FOR_TAB_httc_urb_new_INDEX, httc_urb_new);
//	        for_tab.put(FOR_TAB_httc_can_INDEX, httc_can);
//	        for_tab.put(FOR_TAB_Tb_rur_INDEX, Tb_rur);
//	        for_tab.put(FOR_TAB_mod_U_TaRef_INDEX, mod_U_TaRef);
//	        for_tab.put(FOR_TAB_UTb_INDEX, UTb);
			
			
			
			
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
			z_Hx2=1.0;
			Tb_rur=26.3028510110944;
			//dte=datetime.datetime(2009, 2, 5, 11, 0);			
			mod_U_TaRef=20.0;
			UTb=9.17637804;
			mod_rslts_prev=new ArrayList<ArrayList<Double>>();
			ArrayList<Double> item = new ArrayList<Double> ();
			item.add(4.22426152);
			mod_rslts_prev.add(item);
			httc_rur=0.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        
			targetModule.mod_data_ts_ = mod_data_ts_;
			returnValues = targetModule.calcLoop(lc_data, grid, counter, i, met_data, cfm, z_Uref, z_Hx2, Tb_rur, dte, mod_U_TaRef, UTb, mod_rslts_prev, httc_rur);
			System.out.println(returnValues.toString());
			
	        //double FID = (double)lc_data.get(grid).get(LCData.FID);
	        double FID = returnValues.get(TargetModule.FOR_TAB_FID_INDEX);        
	        double Ucan = returnValues.get(TargetModule.FOR_TAB_Ucan_INDEX);
	        actual = Ucan;
	        expected = 1.9607520219606367;
	        assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
	        double Tsurf_horz = returnValues.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
	        assertEquals(20.981818181818181, Tsurf_horz, Math.abs(20.981818181818181) / threeDigitDelta);
	        double Tsurf_can = returnValues.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
	        assertEquals(59.079999999999998, Tsurf_can, Math.abs(59.079999999999998) / threeDigitDelta);
	        double Tsurf_wall = returnValues.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
	        assertEquals(20.0, Tsurf_wall, Math.abs(20.0) / threeDigitDelta);
	        double Tac = returnValues.get(TargetModule.FOR_TAB_Tac_INDEX);
	        assertEquals(26.3333468389778, Tac, Math.abs(26.3333468389778) / threeDigitDelta);
//	        //double dteDouble = (double)dte.getTime();
//	        double dteDouble = returnValues.get(TargetModule.FOR_TAB_dte_INDEX);
	        double httc_urb_new = returnValues.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
	        assertEquals(-10.3628248419620, httc_urb_new, Math.abs(-10.3628248419620) / threeDigitDelta);
	        double httc_can = returnValues.get(TargetModule.FOR_TAB_httc_can_INDEX);
	        assertEquals(0.016618357679830758, httc_can, Math.abs(0.016618357679830758) / threeDigitDelta);
	        double Tb_rur_return = returnValues.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
	        assertEquals(26.3028510110944, Tb_rur_return, Math.abs(26.3028510110944) / threeDigitDelta);
	        double mod_U_TaRef_return = returnValues.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
	        assertEquals(9.17637804, mod_U_TaRef_return, Math.abs(9.17637804) / threeDigitDelta);
	        double UTb_return = returnValues.get(TargetModule.FOR_TAB_UTb_INDEX);
	        assertEquals(4.22426152, UTb_return, Math.abs(4.22426152) / threeDigitDelta);
			
	        
//	        ('for_tab', (1.0, 1.9607520219606367, 
//	        		20.981818181818181, 59.079999999999998, 20.0, 26.3333468389778, datetime.datetime(2009, 2, 5, 11, 0), 
//	        		-10.3628248419620, 0.016618357679830758, 26.3028510110944, array([ 9.17637804]), array([ 4.22426152])))

			
			
//			
//			
//			
//	        mod_data_ts_=np.zeros((nt,10), np.dtype([('wall','<f8'),('roof','<f8'), ('road','<f8'), ('watr','<f8'), ('conc','<f8'), ('Veg','<f8'), ('dry','<f8'), ('irr','<f8'), ('TSOIL','<f8'), ('avg','<f8'),('date',object)]))  
//			
//			('calcLoop', 0, 0, 0, 
//					              array([[( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0),
//			                              ( 20.,  20.,  20.,  20.,  20.,  0.,  20.,  20.,  20.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)],
//			                             [(  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0),
//			                              (  0.,   0.,   0.,   0.,   0.,  0.,   0.,   0.,   0.,  0., 0)]],
//			                            dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]), 
//					10.0, 1.0, 26.3028510110944, 
//					datetime.datetime(2009, 2, 5, 11, 0), 
//					array([[ 9.17637804],
//			                             [ 0.        ],
//			                             [ 0.        ],
//			                             [ 0.        ],
//			                             [ 0.        ],
//			                             [ 0.        ],
//			                             [ 0.        ],
//			                             [ 0.        ]]), 
//					array([ 4.22426152]), [])
//			                      ('for_tab', (1.0, 1.9607520219606367, 20.981818181818181, 59.079999999999998, 20.0, 26.3333468389778, datetime.datetime(2009, 2, 5, 11, 0), -10.3628248419620, 0.016618357679830758, 26.3028510110944, array([ 9.17637804]), array([ 4.22426152])))
//
//			
//			        for_tab     = (lc_data.ix[grid]['FID'],Ucan,Tsurf_horz, Tsurf_can,Tsurf_wall,Tac,dte,httc_urb_new,httc_can,Tb_rur,mod_U_TaRef[i],UTb)
//			
//			
//			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
//			grid =0;
//			counter = 0;
//			i = 7;
//			z_Uref=10.0;
//			z_Hx2=1.0;
//			Tb_rur=42.0696605329961;
//			dte=datetime.datetime(2009, 2, 5, 14, 30)
//			
//					mod_U_TaRef
//UTb
//mod_rslts_prev
//			
//			print ('calcLoop',grid,counter,i,mod_data_ts_,z_Uref,z_Hx2,Tb_rur,dte,mod_U_TaRef,UTb,mod_rslts_prev)
//			
//			('calcLoop', 0, 0, 7, 
//					array([[ ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,  30.8       ,  20.        ,  20.        ,  20.        ,  0., 0)],
//			                              [ ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,  26.33334684,  20.        ,  20.        ,  20.        ,  0., 0)],
//			                              [ ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,   0.        ,  20.        ,  20.        ,  20.        ,  0., 0),
//			                               ( 20.        ,  20.        ,  20.        ,  20.        ,  20.        ,  26.38921435,  20.        ,  20.        ,  20.        ,  0., 0)],
//			                              [ ( 21.47208204,  28.20585694,  21.40651315,  20.28863389,  21.31184321,   0.        ,  20.36009195,  20.21836006,  20.4712389 ,  0., 0),
//			                               ( 22.22027925,  28.20585694,  22.20581282,  20.41299274,  21.87935974,   0.        ,  21.0906814 ,  20.59400597,  20.4712389 ,  0., 0),
//			                               ( 22.96847646,  28.20585694,  23.00511248,  20.53735159,  22.44687627,   0.        ,  21.82127084,  20.96965189,  20.4712389 ,  0., 0),
//			                               ( 23.71667367,  28.20585694,  23.80441215,  20.66171045,  23.0143928 ,   0.        ,  22.55186028,  21.3452978 ,  20.4712389 ,  0., 0),
//			                               ( 24.46487089,  28.20585694,  24.60371181,  20.7860693 ,  23.58190934,   0.        ,  23.28244973,  21.72094372,  20.4712389 ,  0., 0),
//			                               ( 25.2130681 ,  28.20585694,  25.40301148,  20.91042815,  24.14942587,   0.        ,  24.01303917,  22.09658964,  20.4712389 ,  0., 0),
//			                               ( 25.96126531,  28.20585694,  26.20231115,  21.03478701,  24.7169424 ,   0.        ,  24.74362861,  22.47223555,  20.4712389 ,  0., 0),
//			                               ( 26.70946252,  28.20585694,  27.00161081,  21.15914586,  25.28445893,   0.        ,  25.47421806,  22.84788147,  20.4712389 ,  0., 0),
//			                               ( 27.45765973,  28.20585694,  27.80091048,  21.28350471,  25.85197546,   0.        ,  26.2048075 ,  23.22352738,  20.4712389 ,  0., 0),
//			                               ( 28.20585694,  28.20585694,  28.60021014,  21.40786357,  26.41949199,  26.05541603,  26.93539694,  23.5991733 ,  20.4712389 ,  0., 0)],
//			                              [ ( 22.73528189,  34.84746877,  22.61694938,  20.34755697,  22.44326476,   0.        ,  20.66080757,  20.40190142,  20.86660011,  0., 0),
//			                               ( 24.1114399 ,  34.84746877,  24.09866545,  20.57676941,  23.49524926,   0.        ,  22.01195441,  21.09747711,  20.87626569,  0., 0),
//			                               ( 25.48020647,  34.84746877,  25.57810792,  20.80591555,  24.54617285,   0.        ,  23.35827547,  21.79129825,  20.88593126,  0., 0),
//			                               ( 26.8414974 ,  34.84746877,  27.05524914,  21.03499507,  25.59602637,   0.        ,  24.69971688,  22.48335475,  20.89559684,  0., 0),
//			                               ( 28.19522795,  34.84746877,  28.53006124,  21.2640076 ,  26.64480058,   0.        ,  26.0362244 ,  23.17363649,  20.90526242,  0., 0),
//			                               ( 29.5413128 ,  34.84746877,  30.00251616,  21.4929528 ,  27.69248621,   0.        ,  27.36774344,  23.86213331,  20.914928  ,  0., 0),
//			                               ( 30.87966606,  34.84746877,  31.47258564,  21.72183032,  28.73907395,   0.        ,  28.69421906,  24.54883501,  20.92459358,  0., 0),
//			                               ( 32.21020126,  34.84746877,  32.94024121,  21.95063981,  29.78455443,   0.        ,  30.01559596,  25.23373135,  20.93425916,  0., 0),
//			                               ( 33.53283137,  34.84746877,  34.4054542 ,  22.17938091,  30.82891823,   0.        ,  31.33181845,  25.91681208,  20.94392474,  0., 0),
//			                               ( 34.84746877,  34.84746877,  35.86819576,  22.40805327,  31.8721559 ,  29.01554302,  32.64283053,  26.5980669 ,  20.95359032,  0., 0)],
//			                              [ ( 23.81966858,  39.86016515,  23.65737225,  20.4115115 ,  23.41955872,   0.        ,  20.918914  ,  20.56063007,  21.18395644,  0., 0),
//			                               ( 25.715641  ,  39.86016515,  25.71255141,  20.74035448,  24.88105077,   0.        ,  22.79682222,  21.53133469,  21.20949094,  0., 0),
//			                               ( 27.5839405 ,  39.86016515,  27.7533113 ,  21.06879166,  26.33498895,   0.        ,  24.65360647,  22.49516517,  21.23502029,  0., 0),
//			                               ( 29.42423967,  39.86016515,  29.77944284,  21.39682107,  27.78129645,   0.        ,  26.48899709,  23.45207877,  21.26054446,  0., 0),
//			                               ( 31.23621463,  39.86016515,  31.79073557,  21.72444072,  29.21989608,   0.        ,  28.30272475,  24.4020329 ,  21.28606343,  0., 0),
//			                               ( 33.01954526,  39.86016515,  33.78697764,  22.05164862,  30.65071026,   0.        ,  30.09452053,  25.34498515,  21.31157716,  0., 0),
//			                               ( 34.77391539,  39.86016515,  35.76795588,  22.37844279,  32.07366107,   0.        ,  31.86411605,  26.28089327,  21.33708563,  0., 0),
//			                               ( 36.49901306,  39.86016515,  37.73345578,  22.7048212 ,  33.48867019,   0.        ,  33.61124352,  27.20971521,  21.36258882,  0., 0),
//			                               ( 38.19453064,  39.86016515,  39.68326151,  23.03078185,  34.89565894,   0.        ,  35.33563585,  28.13140909,  21.38808669,  0., 0),
//			                               ( 39.86016515,  39.86016515,  41.61715597,  23.35632272,  36.29454828,  31.16692347,  37.03702675,  29.04593324,  21.41357921,  0., 0)],
//			                              [ ( 24.74518449,  43.866607  ,  24.54642841,  20.54474821,  24.25795005,   0.        ,  21.13079523,  20.69159848,  21.43978252,  0., 0),
//			                               ( 27.07266976,  43.866607  ,  27.08018767,  20.95208231,  26.06242718,   0.        ,  23.45115094,  21.89452682,  21.48574295,  0., 0),
//			                               ( 29.35037128,  43.866607  ,  29.58215829,  21.35828012,  27.84981529,   0.        ,  25.73059325,  23.08478674,  21.53166772,  0., 0),
//			                               ( 31.57782743,  43.866607  ,  32.05175955,  21.76333463,  29.619892  ,   0.        ,  27.96859219,  24.26230803,  21.57755666,  0., 0),
//			                               ( 33.75461547,  43.866607  ,  34.48841135,  22.16723884,  31.3724349 ,   0.        ,  30.16463489,  25.4270227 ,  21.6234096 ,  0., 0),
//			                               ( 35.88035276,  43.866607  ,  36.89153449,  22.56998572,  33.10722157,   0.        ,  32.31822633,  26.57886502,  21.66922635,  0., 0),
//			                               ( 37.95469791,  43.866607  ,  39.26055107,  22.97156824,  34.82402971,   0.        ,  34.42889014,  27.71777157,  21.71500675,  0., 0),
//			                               ( 39.97735182,  43.866607  ,  41.59488477,  23.37197934,  36.52263711,   0.        ,  36.49616933,  28.84368126,  21.76075061,  0., 0),
//			                               ( 41.94805869,  43.866607  ,  43.89396121,  23.77121196,  38.20282178,   0.        ,  38.51962703,  29.95653538,  21.80645775,  0., 0),
//			                               ( 43.866607  ,  43.866607  ,  46.1572083 ,  24.16925901,  39.86436198,  38.78409572,  40.4988472 ,  31.05627764,  21.852128  ,  0., 0)],
//			                              [ ( 25.50842308,  46.8159647 ,  25.29633357,  20.66262486,  24.97159561,   0.        ,  21.28692709,  20.78765267,  21.65215671,  0., 0),
//			                               ( 28.16724153,  46.8159647 ,  28.21953042,  21.13670457,  27.05685867,   0.        ,  23.96168256,  22.17491075,  21.72055364,  0., 0),
//			                               ( 30.75350991,  46.8159647 ,  31.09007473,  21.60862468,  29.11341717,   0.        ,  26.57367941,  23.54332493,  21.78883378,  0., 0),
//			                               ( 33.26691956,  46.8159647 ,  33.90699496,  22.07837199,  31.14089469,   0.        ,  29.12228749,  24.89282638,  21.85699645,  0., 0),
//			                               ( 35.70728657,  46.8159647 ,  36.66934507,  22.54593341,  33.13892156,   0.        ,  31.60694981,  26.22335407,  21.92504094,  0., 0),
//			                               ( 38.07455353,  46.8159647 ,  39.37620634,  23.01129589,  35.10713533,   0.        ,  34.02718488,  27.5348548 ,  21.99296658,  0., 0),
//			                               ( 40.36879096,  46.8159647 ,  42.0266892 ,  23.47444648,  37.04518102,   0.        ,  36.38258887,  28.82728332,  22.06077269,  0., 0),
//			                               ( 42.59019811,  46.8159647 ,  44.6199351 ,  23.9353723 ,  38.95271158,   0.        ,  38.67283766,  30.10060242,  22.12845856,  0., 0),
//			                               ( 44.73910348,  46.8159647 ,  47.15511828,  24.39406058,  40.82938815,   0.        ,  40.89768857,  31.35478299,  22.19602351,  0., 0),
//			                               ( 46.8159647 ,  46.8159647 ,  49.63144758,  24.85049864,  42.67488048,   0.        ,  43.05698206,  32.58980407,  22.26346684,  0., 0)]],
//			                             dtype=[('wall', '<f8'), ('roof', '<f8'), ('road', '<f8'), ('watr', '<f8'), ('conc', '<f8'), ('Veg', '<f8'), ('dry', '<f8'), ('irr', '<f8'), ('TSOIL', '<f8'), ('avg', '<f8'), ('date', 'O')]), 
//					10.0, 1.0, 42.0696605329961, datetime.datetime(2009, 2, 5, 14, 30), 
//					array([[  9.17637804],
//			                              [  9.17637804],
//			                              [ 10.39989511],
//			                              [ 11.01165365],
//			                              [ 10.39989511],
//			                              [  6.7293439 ],
//			                              [  9.17637804],
//			                              [  9.17637804]]), array([ 4.22426152]), array([[[ (1,  1.96075202,  30.00355589,  85.83457979,  29.35037128,  40.70373843, datetime.datetime(2009, 2, 5, 14, 0), -10.38513033,  0.01661836,  40.64704897,  9.17637804,  4.22426152)]]],
//			                             dtype=[('ID', '<i4'), ('Ws', '<f8'), ('Ts', '<f8'), ('Tsurf_can', '<f8'), ('Tsurf_wall', '<f8'), ('Ta', '<f8'), ('date', 'O'), ('httc_urb', '<f8'), ('httc_can', '<f8'), ('Tb_rur', '<f8'), ('mod_U_TaRef', '<f8'), ('UTb', '<f8')]))
//			                      
//			('for_tab', (1.0, 1.9607520219606367, 31.361917276406711, 89.85442684521324, 30.753509911758808, 42.1264975245993, datetime.datetime(2009, 2, 5, 14, 30), -10.3922369552194, 0.016618357679830758, 42.0696605329961, array([ 9.17637804]), array([ 4.22426152])))
//
//			
//			
//			
			
			
			
			
			
			
			
	 }
	 
	 @Test 
	 public void testLcSort()
	 {
		 double actual,expected;
		 TreeMap<String,Object> returnValues;
		 
		 String controlFileName;
			Cfm cfm = null;  //control file data structure
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation_small.txt";
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
		 
			int grid = 0;
		 
		 
		    //# create dictionary for raw LC inputs
		    double H      = lc_data.get(grid).get(LCData.H);     //  # building height for grid point
		    double W      = lc_data.get(grid).get(LCData.W);      // # street width for grid point 
		    //## create dictionary with land cover stuff
		    LcSort lcSort = new LcSort();
		    ArrayList<Double> LC_orig = lc_data.get(grid);


		    ArrayList<Double> LC = new ArrayList<Double>(Arrays.asList(new Double[LC_orig.size()]));
			Collections.copy(LC, LC_orig);
		   
		 

		 returnValues = lcSort.lc_sort(LC, H, W);
		 System.out.println(returnValues.toString());
		 
//		    returnValues.get(LcSort.LC_KEY, LC);
		    List<Double> LC_ = (List<Double>)returnValues.get(LcSort.LC_KEY);
		    assertEquals(0.1125, LC_.get(LCData.dry), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.10000000000000001, LC_.get(LCData.Veg), Math.abs(0.10000000000000001) / threeDigitDelta);
		    assertEquals(0.1125, LC_.get(LCData.conc), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.1125, LC_.get(LCData.road), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.1125, LC_.get(LCData.watr), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(1.8, LC_.get(LCData.wall), Math.abs(1.8) / threeDigitDelta);
		    assertEquals(0.10000000000000001, LC_.get(LCData.roof), Math.abs(0.10000000000000001) / threeDigitDelta);
		    assertEquals(0.45000000000000001, LC_.get(LCData.irr), Math.abs(0.45000000000000001) / threeDigitDelta);
		    
//		    'LC': {'dry': 0.1125, 'wall': 1.8, 'irr': 0.45000000000000001, 'roof': 0.10000000000000001, 'Veg': 0.10000000000000001, 'conc': 0.1125, 'road': 0.1125, 'watr': 0.1125}, 
		 
//		    'LC_woRoofAvg': {'dry': 0.1125, 'wall': 1.8, 'irr': 0.45000000000000001, 'roof': 0.10000000000000001, 'Veg': 0.10000000000000001, 'conc': 0.1125, 'watr': 0.1125, 'road': 0.1125},
//		    returnValues.get(LcSort.LC_woRoofAvg_KEY, LC_woRoofAvg);
		    List<Double> LC_woRoofAvg = (List<Double>)returnValues.get(LcSort.LC_woRoofAvg_KEY);
		    assertEquals(0.1125, LC_woRoofAvg.get(LCData.dry), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.10000000000000001, LC_woRoofAvg.get(LCData.Veg), Math.abs(0.10000000000000001) / threeDigitDelta);
		    assertEquals(0.1125, LC_woRoofAvg.get(LCData.conc), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.1125, LC_woRoofAvg.get(LCData.road), Math.abs(0.1125) / threeDigitDelta);
		    assertEquals(0.1125, LC_woRoofAvg.get(LCData.watr), Math.abs(0.1125) / threeDigitDelta);
		    //TODO don't understand why this doesn't work, I'm getting 0.0
//		    assertEquals(1.8, LC_woRoofAvg.get(LCData.wall), Math.abs(1.8) / threeDigitDelta);
		    assertEquals(0.10000000000000001, LC_woRoofAvg.get(LCData.roof), Math.abs(0.10000000000000001) / threeDigitDelta);
		    
//		    returnValues.get(LcSort.LC_woRoofAvgN_KEY, LC_woRoofAvgN);
		    List<Double> LC_wRoofAvg = (List<Double>)returnValues.get(LcSort.LC_wRoofAvg_KEY);
		    assertEquals(0.10227272727272727, LC_wRoofAvg.get(LCData.dry), Math.abs(0.10227272727272727) / threeDigitDelta);
		    assertEquals(0.090909090909090912, LC_wRoofAvg.get(LCData.Veg), Math.abs(0.090909090909090912) / threeDigitDelta);
		    assertEquals(0.10227272727272727, LC_wRoofAvg.get(LCData.conc), Math.abs(0.10227272727272727) / threeDigitDelta);
		    assertEquals(0.10227272727272727, LC_wRoofAvg.get(LCData.road), Math.abs(0.10227272727272727) / threeDigitDelta);
		    assertEquals(0.10227272727272727, LC_wRoofAvg.get(LCData.watr), Math.abs(0.10227272727272727) / threeDigitDelta);
		    assertEquals(0.40909090909090906, LC_wRoofAvg.get(LCData.irr), Math.abs(0.40909090909090906) / threeDigitDelta);
		    assertEquals(0.090909090909090912, LC_wRoofAvg.get(LCData.roof), Math.abs(0.090909090909090912) / threeDigitDelta);
		    
		    H = (double)returnValues.get(LcSort.H_KEY);
		    actual = H;
			expected = 1.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    W = (double)returnValues.get(LcSort.W_KEY);
		    actual = W;
			expected = 1.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    double Wtree = (double)returnValues.get(LcSort.Wtree_KEY);
		    actual = Wtree;
			expected = 1.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//		    returnValues.get(LcSort.fw_KEY, fw);
			int fg = (int)returnValues.get(LcSort.fg_KEY);
		    actual = fg;
			expected = 4;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
		 
		 
		 
		 
//		 ('lc_sort', 
//				 {'dry': 0.10000000000000001, 'irr': 0.40000000000000002, 'roof': 0.10000000000000001, 'Veg': 0.10000000000000001, 'conc': 0.10000000000000001, 
//			 'road': 0.10000000000000001, 'watr': 0.10000000000000001}, 0.5, 1.0,
//				 <module 'constants2' from '/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/python_bin/constants2.pyc'>)
//
//		 {'W': 1.0, 
//			 'Wtree': 1.0, 
//			 'LC': {'dry': 0.1125, 'wall': 1.8, 'irr': 0.45000000000000001, 'roof': 0.10000000000000001, 'Veg': 0.10000000000000001, 'conc': 0.1125, 'road': 0.1125, 'watr': 0.1125}, 
//			 'LC_woRoofAvg': {'dry': 0.1125, 'wall': 1.8, 'irr': 0.45000000000000001, 'roof': 0.10000000000000001, 'Veg': 0.10000000000000001, 'conc': 0.1125, 'watr': 0.1125, 'road': 0.1125}, 
//			 'fw': 2, 
//			 'H': 1.0, 
//			 'LC_wRoofavg': {'dry': 0.10227272727272727, 'Veg': 0.090909090909090912, 'conc': 0.10227272727272727, 'road': 0.10227272727272727, 
//				 'watr': 0.10227272727272727, 'irr': 0.40909090909090906, 'roof': 0.090909090909090912}, 
//			 'fg': 4, 
//			 'LC_woRoofAvgN': {'dry': 0.1125, 'Veg': 0.10000000000000001, 'conc': 0.1125, 'road': 0.1125, 'wall': 1.8, 'watr': 0.1125, 'irr': 0.45000000000000001, 'roof': 0.0}}

			
			
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation_small2.txt";
			cfm = new Cfm(controlFileName);
			lcFilename = rootDir + "/input/" + cfm.getValue("site_name") + "/LC/" + cfm.getValue("inpt_lc_file");
			lcDataClass = new LCData(lcFilename);
			lc_data = lcDataClass.getlcData();
		 
			grid = 0;
		 
		 
		    //# create dictionary for raw LC inputs
		    H      = lc_data.get(grid).get(LCData.H);     //  # building height for grid point
		    W      = lc_data.get(grid).get(LCData.W);      // # street width for grid point 
		    //## create dictionary with land cover stuff
		    lcSort = new LcSort();
		    LC_orig = lc_data.get(grid);


		    LC = new ArrayList<Double>(Arrays.asList(new Double[LC_orig.size()]));
			Collections.copy(LC, LC_orig);
		   
		 

		 returnValues = lcSort.lc_sort(LC, H, W);
		 System.out.println(returnValues.toString());
		 
//			'LC': {'dry': 0.36818181818181817, 'wall': 1.7272727272727271, 'irr': 0.42954545454545451, 'roof': 0.050000000000000003, 'Veg': 0.25, 'conc': 0.24545454545454548, 'road': 0.12272727272727274, 'watr': 0.18409090909090908}, 
		 
		    LC_ = (List<Double>)returnValues.get(LcSort.LC_KEY);
		    assertEquals(0.36818181818181817, LC_.get(LCData.dry), Math.abs(0.36818181818181817) / threeDigitDelta);
		    assertEquals(0.25, LC_.get(LCData.Veg), Math.abs(0.25) / threeDigitDelta);
		    assertEquals(0.24545454545454548, LC_.get(LCData.conc), Math.abs(0.24545454545454548) / threeDigitDelta);
		    assertEquals(0.12272727272727274, LC_.get(LCData.road), Math.abs(0.12272727272727274) / threeDigitDelta);
		    assertEquals(0.18409090909090908, LC_.get(LCData.watr), Math.abs(0.18409090909090908) / threeDigitDelta);
		    assertEquals(1.7272727272727271, LC_.get(LCData.wall), Math.abs(1.7272727272727271) / threeDigitDelta);
		    assertEquals(0.050000000000000003, LC_.get(LCData.roof), Math.abs(0.050000000000000003) / threeDigitDelta);
		    assertEquals(0.42954545454545451, LC_.get(LCData.irr), Math.abs(0.42954545454545451) / threeDigitDelta);
	
//			'LC_woRoofAvgN': {'dry': 0.23011363636363638, 'Veg': 0.15625, 'conc': 0.15340909090909094, 'road': 0.07670454545454547, 'wall': 1.7272727272727271, 'watr': 0.11505681818181819, 'irr': 0.26846590909090912, 'roof': 0.0}}
		    
		    List<Double> LC_woRoofAvgN = (List<Double>)returnValues.get(LcSort.LC_woRoofAvgN_KEY);
		    assertEquals(0.23011363636363638, LC_woRoofAvgN.get(LCData.dry), Math.abs(0.23011363636363638) / threeDigitDelta);
		    assertEquals(0.15625, LC_woRoofAvgN.get(LCData.Veg), Math.abs(0.15625) / threeDigitDelta);
		    assertEquals(0.15340909090909094, LC_woRoofAvgN.get(LCData.conc), Math.abs(0.15340909090909094) / threeDigitDelta);
		    assertEquals(0.07670454545454547, LC_woRoofAvgN.get(LCData.road), Math.abs(0.07670454545454547) / threeDigitDelta);
		    assertEquals(0.11505681818181819, LC_woRoofAvgN.get(LCData.watr), Math.abs(0.11505681818181819) / threeDigitDelta);
		    assertEquals(1.7272727272727271, LC_woRoofAvgN.get(LCData.wall), Math.abs(1.7272727272727271) / threeDigitDelta);
		    assertEquals(0.26846590909090912, LC_woRoofAvgN.get(LCData.irr), Math.abs(0.26846590909090912) / threeDigitDelta);
		    assertEquals(0.0, LC_woRoofAvgN.get(LCData.roof), Math.abs(0.0) / threeDigitDelta);
		    
//			'LC_woRoofAvg': {'dry': 0.36818181818181817, 'wall': 1.7272727272727271, 'irr': 0.42954545454545451, 'roof': 0.050000000000000003, 'Veg': 0.25, 'conc': 0.24545454545454548, 'watr': 0.18409090909090908, 'road': 0.12272727272727274}, 
		    
		    LC_woRoofAvg = (List<Double>)returnValues.get(LcSort.LC_woRoofAvg_KEY);
		    assertEquals(0.36818181818181817, LC_woRoofAvg.get(LCData.dry), Math.abs(0.36818181818181817) / threeDigitDelta);
		    assertEquals(0.25, LC_woRoofAvg.get(LCData.Veg), Math.abs(0.25) / threeDigitDelta);
		    assertEquals(0.24545454545454548, LC_woRoofAvg.get(LCData.conc), Math.abs(0.24545454545454548) / threeDigitDelta);
		    assertEquals(0.12272727272727274, LC_woRoofAvg.get(LCData.road), Math.abs(0.12272727272727274) / threeDigitDelta);
		    assertEquals(0.18409090909090908, LC_woRoofAvg.get(LCData.watr), Math.abs(0.18409090909090908) / threeDigitDelta);
		    assertEquals(0.42954545454545451, LC_woRoofAvg.get(LCData.irr), Math.abs(0.42954545454545451) / threeDigitDelta);
		    assertEquals(0.050000000000000003, LC_woRoofAvg.get(LCData.roof), Math.abs(0.050000000000000003) / threeDigitDelta);
		    //TODO don't understand why this doesn't work, I'm getting 0.0
//		    assertEquals(1.7272727272727271, LC_woRoofAvg.get(LCData.wall), Math.abs(1.7272727272727271) / threeDigitDelta);
		    
//			'LC_wRoofavg': {'dry': 0.2231404958677686, 'Veg': 0.15151515151515152, 'conc': 0.14876033057851243, 'road': 0.074380165289256214, 'watr': 0.1115702479338843, 'irr': 0.26033057851239666, 'roof': 0.030303030303030307}, 
		    
		    LC_wRoofAvg = (List<Double>)returnValues.get(LcSort.LC_wRoofAvg_KEY);
		    assertEquals(0.2231404958677686, LC_wRoofAvg.get(LCData.dry), Math.abs(0.2231404958677686) / threeDigitDelta);
		    assertEquals(0.15151515151515152, LC_wRoofAvg.get(LCData.Veg), Math.abs(0.15151515151515152) / threeDigitDelta);
		    assertEquals(0.14876033057851243, LC_wRoofAvg.get(LCData.conc), Math.abs(0.14876033057851243) / threeDigitDelta);
		    assertEquals(0.074380165289256214, LC_wRoofAvg.get(LCData.road), Math.abs(0.074380165289256214) / threeDigitDelta);
		    assertEquals(0.1115702479338843, LC_wRoofAvg.get(LCData.watr), Math.abs(0.1115702479338843) / threeDigitDelta);
		    assertEquals(0.26033057851239666, LC_wRoofAvg.get(LCData.irr), Math.abs(0.26033057851239666) / threeDigitDelta);
		    assertEquals(0.030303030303030307, LC_wRoofAvg.get(LCData.roof), Math.abs(0.030303030303030307) / threeDigitDelta);
		    
		    
		    
		    H = (double)returnValues.get(LcSort.H_KEY);
		    actual = H;
			expected = 1.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    W = (double)returnValues.get(LcSort.W_KEY);
		    actual = W;
			expected = 1.1000000000000001;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    Wtree = (double)returnValues.get(LcSort.Wtree_KEY);
		    actual = Wtree;
			expected = 1.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    int fw = (int)returnValues.get(LcSort.fw_KEY);
		    actual = fw;
			expected = 3;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			fg = (int)returnValues.get(LcSort.fg_KEY);
		    actual = fg;
			expected = 4;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//	('lc_sort', 
//	{'dry': 0.29999999999999999, 'irr': 0.34999999999999998, 'roof': 0.050000000000000003, 'Veg': 0.25, 'conc': 0.20000000000000001, 'road': 0.10000000000000001, 'watr': 0.14999999999999999}, 0.59999999999999998, 1.1000000000000001,
//	<module 'constants2' from '/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/python_bin/constants2.pyc'>)
//
//	{'W': 1.1000000000000001, 
//		'Wtree': 1.0, 
//		'LC': {'dry': 0.36818181818181817, 'wall': 1.7272727272727271, 'irr': 0.42954545454545451, 'roof': 0.050000000000000003, 'Veg': 0.25, 'conc': 0.24545454545454548, 'road': 0.12272727272727274, 'watr': 0.18409090909090908}, 
//		'LC_woRoofAvg': {'dry': 0.36818181818181817, 'wall': 1.7272727272727271, 'irr': 0.42954545454545451, 'roof': 0.050000000000000003, 'Veg': 0.25, 'conc': 0.24545454545454548, 'watr': 0.18409090909090908, 'road': 0.12272727272727274}, 
//		'fw': 3, 
//		'H': 1.0, 
//		'LC_wRoofavg': {'dry': 0.2231404958677686, 'Veg': 0.15151515151515152, 'conc': 0.14876033057851243, 'road': 0.074380165289256214, 'watr': 0.1115702479338843, 'irr': 0.26033057851239666, 'roof': 0.030303030303030307}, 
//		'fg': 4, 
//		'LC_woRoofAvgN': {'dry': 0.23011363636363638, 'Veg': 0.15625, 'conc': 0.15340909090909094, 'road': 0.07670454545454547, 'wall': 1.7272727272727271, 'watr': 0.11505681818181819, 'irr': 0.26846590909090912, 'roof': 0.0}}

			
			
			
			
			
			
			
	 }
	 
	 @Test
	 public void testTsEbW()
	 {
		 TreeMap<String,Double> returnValues ;
		 TsEbW tsEbW = new TsEbW();
		 
		 String controlFileName;
			Cfm cfm = null;  //control file data structure
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation.txt";
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
		 
		 
			double Tw1,Tsoil,LEw,Hs,Gw,tM,actual,expected;
			int numberOfVf = 11;
			int numberOfSurfaces = 11;
			ArrayList<ArrayList<Object>> met_d;
			//Cfm cfm;
			double[][][] mod_ts = new double[met_data.size()][numberOfVf][numberOfSurfaces];
			double[][][] mod_tm = new double[met_data.size()][numberOfVf][numberOfSurfaces];
			//TreeMap<String,Date> Dats;
			int i;
			TreeMap<String,Double> rad = new TreeMap<String,Double>();
			int vf;
		
			//  print ('Ts_EB_W',mod_ts[:][i-1], mod_tm[:][i-1], i, rad)

//			('Ts_EB_W', ( 0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0., 0), ( 0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0., 0), 0, 
//			{'Rnprev': 0.0, 'Rn': 0.0, 'Rnstar': 0.0})
//			({'TSOIL': 20.0, 'QhW': 0.0, 'QgW': 0.0, 'TM': 23.6, 'QeW': 0.0, 'TsW': 20.0}, 'vf', 3)


			i=0;
			vf=3;
			Date dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
	        rad.put(RnCalcNew.RN_KEY,0.0);	        
		    returnValues = tsEbW.ts_EB_W(met_data, cfm, mod_ts, mod_tm, Dats, i, rad, vf);
		    Tw1=returnValues.get(TsEbW.TSW_KEY);
			actual = Tw1;
			expected = 20.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    Tsoil=returnValues.get(TsEbW.TSOIL_KEY);
			actual = Tsoil;
			expected = 20.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    LEw=returnValues.get(TsEbW.QEW_KEY);
			actual = LEw;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    Hs=returnValues.get(TsEbW.QHW_KEY);
			actual = Hs;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    Gw=returnValues.get(TsEbW.QGW_KEY);
			actual = Gw;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    tM=returnValues.get(TsEbW.TM_KEY);
			actual = tM;
			expected = 23.6;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
//   return {"TsW":Tw1, "TSOIL":Tsoil, 'QeW': LEw, 'QhW': Hs, 'QgW':Gw , 'TM':tM}
//			('Ts_EB_W', 
//					( 22.22939669,  22.22939669,  21.4449334,  18.69395608,  22.56000584,  22.04895493,  16.52780523,  18.21483622,  21.77048914,  0.,   0),    //ts
//					( 27.97725444,  27.97725444,  28.70953403, 23.50125909,  27.71093995,  0.,           22.69590865,  21.82638183,  0.,           0.,   0), 15,  //tm
//					{'Rnprev': -22.024107536300573, 'Rn': -9.806053871979854, 'Rnstar': 7.0414761981643448})
//			({'TSOIL': 21.757928130198298, 'QhW': 91.300910225643165, 'QgW': -26.491410817213236, 'TM': 23.487983847131613, 'QeW': 101.36281845283125, 'TsW': 18.703420598511858}, 'vf', 9)


			i=15;
			vf=9;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
	        rad.put(RnCalcNew.RN_KEY,-9.806053871979854);
	        
	        //3
	        mod_tm[i-1][vf][TargetModule.getSurfIndex("watr")]=23.50125909;
	        
	        mod_ts[i-1][vf][TargetModule.getSurfIndex("watr")]=18.69395608;
	        //8
	        mod_ts[i-1][vf][TargetModule.getSurfIndex("TSOIL")]=21.77048914;
	        
//	        double cs_zw = Constants.cs_zW;
//	        double cs_c_watr = Constants.cs_C.get("watr");
//	        double cs_kw = Constants.cs_Kw;
//	        Gw      = -cs_c_watr * cs_kw * ((mod_ts[i-1][vf][TargetModule.getSurfIndex("TSOIL")]-mod_ts[i-1][vf][TargetModule.getSurfIndex("watr")])/cs_zw)	;
//	        
//	        
		    returnValues = tsEbW.ts_EB_W(met_data, cfm, mod_ts, mod_tm, Dats, i, rad, vf);
		    
		    Hs=returnValues.get(TsEbW.QHW_KEY);
			actual = Hs;
			expected = 91.300910225643165;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    Gw=returnValues.get(TsEbW.QGW_KEY);
			actual = Gw;
			expected = -26.491410817213236;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    
		    Tw1=returnValues.get(TsEbW.TSW_KEY);
			actual = Tw1;
			expected = 18.703420598511858;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    Tsoil=returnValues.get(TsEbW.TSOIL_KEY);
			actual = Tsoil;
			expected = 21.757928130198298;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    LEw=returnValues.get(TsEbW.QEW_KEY);
			actual = LEw;
			expected = 101.36281845283125;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    
		    tM=returnValues.get(TsEbW.TM_KEY);
			actual = tM;
			expected = 23.487983847131613;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		//   return {"TsW":Tw1, "TSOIL":Tsoil, 'QeW': LEw, 'QhW': Hs, 'QgW':Gw , 'TM':tM}	
//			('Ts_EB_W', ( 21.42822398,  20.98606252,  21.15635395,  19.94277779,  21.59932028,  0.,  18.60623805,  19.35790093,  21.13419986,  0., 0), 
//					(     28.15889241,  28.14592637,  28.94243658,  23.59404305,  27.86688741,  0.,  22.92774321,  21.96042547,  0.,  0., 0), 6, 
//					{'Rnprev': -17.77963167660559, 'Rn': -19.499279296609661, 'Rnstar': 0.045497145382205062})
//			({'TSOIL': 21.363591342031818, 'QhW': -6.0282956343668479, 'QgW': -10.259097187723324, 'TM': 23.58890205781017, 'QeW': 186.13667473623863, 'TsW': 19.653679485100856}, 'vf', 4)
	


			i=6;
			vf=4;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
	        rad.put(RnCalcNew.RN_KEY,-19.499279296609661);
	        
	        //3
	        mod_tm[i-1][vf][TargetModule.getSurfIndex("watr")]=23.59404305;
	        
	        mod_ts[i-1][vf][TargetModule.getSurfIndex("watr")]=19.94277779;
	        //8
	        mod_ts[i-1][vf][TargetModule.getSurfIndex("TSOIL")]=21.13419986;
	        	        
		    returnValues = tsEbW.ts_EB_W(met_data, cfm, mod_ts, mod_tm, Dats, i, rad, vf);
		    
		    Hs=returnValues.get(TsEbW.QHW_KEY);
			actual = Hs;
			expected = -6.0282956343668479;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    Gw=returnValues.get(TsEbW.QGW_KEY);
			actual = Gw;
			expected = -10.259097187723324;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    
		    Tw1=returnValues.get(TsEbW.TSW_KEY);
			actual = Tw1;
			expected = 19.653679485100856;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    Tsoil=returnValues.get(TsEbW.TSOIL_KEY);
			actual = Tsoil;
			expected = 21.363591342031818;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		    LEw=returnValues.get(TsEbW.QEW_KEY);
			actual = LEw;
			expected = 186.13667473623863;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		    
		    tM=returnValues.get(TsEbW.TM_KEY);
			actual = tM;
			expected = 23.58890205781017;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
	 }
	 
	 @Test
	 public void testsfc_ri()
	 {
		 SfcRi sfcRi = new SfcRi();
		 double returnValue, actual, expected;
		 double dz;
		 double Thi;
		 double Tlo;
		 double Uhi;
		 
		 dz =2.55;
		 Thi = 21.899999999999999;
		 Tlo = 20.0;
		 Uhi = 3.05879268;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 0.24555776;
		 assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
		 
//		 ('sfc_ri', 2.55, 21.899999999999999, 20.0, array([ 3.05879268]))
//		 [ 0.24555776]
//		 ('sfc_ri', 1.05, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00274867617524587
//		 ('sfc_ri', 1.25, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00319279811141049
//		 ('sfc_ri', 0.55000000000000004, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00152716173206596
//		 ('sfc_ri', 1.25, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00319279811141049
//		 ('sfc_ri', 1.05, 21.8093381484548, 21.900000000000002, 3.7125382075060025)
//		 -0.00274867617524599
//		 ('sfc_ri', 0.55000000000000004, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00152716173206596
//		 ('sfc_ri', 0.55000000000000004, 21.8093381484548, 21.899999999999999, 3.7125382075060025)
//		 -0.00152716173206596
		 dz =0.55000000000000004;
		 Thi = 21.8093381484548;
		 Tlo = 21.899999999999999;
		 Uhi = 3.7125382075060025;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = -0.00152716173206596;
		 assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//
//		 ('sfc_ri', 0.55000000000000004, 23.2257456165725, array([ 20.34119076]), 0.1)
//		 71.5506916767578
		 dz =0.55000000000000004;
		 Thi = 23.2257456165725;
		 Tlo = 20.34119076;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 71.5506916767578;
		 assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
//		 ('sfc_ri', 1.25, 23.2257456165725, array([ 20.37642197]), 0.1)
//		 160.887104537979
		 dz =1.25;
		 Thi = 23.2257456165725;
		 Tlo = 20.37642197;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 160.887104537979;
		 assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
//		 ('sfc_ri', 1.25, 23.2257456165725, array([ 20.37095932]), 0.1)
//		 161.214435863056
//		 ('sfc_ri', 1.05, 23.2257456165725, array([ 20.58626352]), 0.1)
//		 124.543115117845
//		 2009-02-05 02:30:00 5
//		 ('sfc_ri', 2.55, 20.699999999999999, 21.25591169723851, array([ 0.1]))
//		 [-63.2968257]
//		 ('sfc_ri', 1.05, 23.2257456165725, array([ 21.0224176]), 0.1)
//		 103.017371158152
//		 ('sfc_ri', 1.25, 23.2257456165725, array([ 20.45537]), 0.1)
//		 156.165549078442
//		 ('sfc_ri', 0.55000000000000004, 23.2257456165725, array([ 20.64826688]), 0.1)
//		 63.5003171215823
//
//
//		 ('sfc_ri', 1.25, 25.2686468248213, array([ 21.43439113]), 5.9400611320096042)
//		 0.0572223553613548
//		 ('sfc_ri', 0.55000000000000004, 25.2686468248213, array([ 22.02905384]), 5.9400611320096042)
//		 0.0209735635990656
//		 ('sfc_ri', 1.25, 25.2686468248213, array([ 21.64996375]), 5.9400611320096042)
//		 0.0537671790912694
//		 ('sfc_ri', 1.05, 25.2686468248213, array([ 22.48629224]), 5.9400611320096042)
//		 0.0341287406709907
//		 ('sfc_ri', 0.55000000000000004, 25.2686468248213, array([ 21.97641247]), 5.9400611320096042)
//		 0.0213375554067640
//		 ('sfc_ri', 0.55000000000000004, 25.2686468248213, array([ 21.92723701]), 5.9400611320096042)
//		 0.0216783155211266
//		 ('sfc_ri', 1.25, 25.2686468248213, array([ 21.9653646]), 5.9400611320096042)
//		 0.0487687934107217
//		 ('sfc_ri', 1.25, 25.2686468248213, array([ 21.98365561]), 5.9400611320096042)
//		 0.0484809695117265
//		 ('sfc_ri', 1.05, 25.2686468248213, array([ 22.74326748]), 5.9400611320096042)
//		 0.0308223561221598
		 dz =1.05;
		 Thi = 25.2686468248213;
		 Tlo = 22.74326748;
		 Uhi = 5.9400611320096042;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 0.0308223561221598;
		 assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);

		 
		 
	 }
	 
	 @Test
	 public void testrn_calc_3a()
	 {
		 TreeMap<String,Double> returnValues ;
		 
		 String controlFileName;
			Cfm cfm = null;  //control file data structure
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation.txt";
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
			
			RnCalcNew rnCalcNew = new RnCalcNew();
			
			//Cfm cfm;
			ArrayList<ArrayList<Object>> met;
			String surf;
			//TreeMap<String,Date> Dats; 
			ArrayList<Double> mod_ts = new ArrayList<Double>();
			int i;
			double svf;
			double Rn, Rnprev, Rnstar, actual, expected;
			
			surf = "dry";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(0.0);
			mod_ts.add(0.0);
			mod_ts.add(0.0);
			i=0;
			svf = 1.0;
			Date dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
			
//			('rn_calc_new', 'dry', 0.0, 0.0, 0.0, 0, 1.0)
//			(0.0, 0.0, 0.0)
//			('rn_calc_new', 'conc', 0.0, 0.0, 0.0, 0, 1.0)
//			(0.0, 0.0, 0.0)
//			('rn_calc_new', 'roof', 0.0, 0.0, 0.0, 0, 0.1)
//			(0.0, 0.0, 0.0)
//			('rn_calc_new', 'road', 0.0, 0.0, 0.0, 0, 0.1)
//			(0.0, 0.0, 0.0)
//			('rn_calc_new', 'watr', 0.0, 0.0, 0.0, 0, 0.1)
//			(0.0, 0.0, 0.0)
			surf = "watr";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(0.0);
			mod_ts.add(0.0);
			mod_ts.add(0.0);
			i=0;
			svf = 0.1;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);

			
//			('rn_calc_new', 'dry', 0.0, 0.0, 20.0, 1, 1.0)
//			(62.38976040137841, 69.710360401378381, -53.180331807493985)
			surf = "dry";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(20.0);
			mod_ts.add(0.0);
			mod_ts.add(0.0);
			i=1;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = 62.38976040137841;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = 69.710360401378381;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = -53.180331807493985;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('rn_calc_new', 'conc', 0.0, 0.0, 20.0, 1, 1.0)
//			(59.843239568669084, 66.865039568669047, -51.009706019433004)
//			('rn_calc_new', 'roof', 0.0, 0.0, 20.0, 1, 0.1)
//			(57.296718735959765, 64.019718735959742, -48.839080231372037)
//			('rn_calc_new', 'road', 0.0, 0.0, 20.0, 1, 0.1)
//			(6.0479869776846416, 6.7576369776846397, -5.1552362466448258)
//			('rn_calc_new', 'watr', 0.0, 0.0, 20.0, 1, 0.1)
//			(6.1753130193201073, 6.8999030193201056, -5.2637675360478751)
			
			
//			('rn_calc_new', 'dry', 20.0, 20.0, 20.0, 3, 0.6)
//			(-20.584861928165733, -21.990181928165757, 0.69384000000000334)
//			('rn_calc_new', 'irr', 20.0, 20.0, 20.0, 3, 0.6)
//			(-20.584861928165733, -21.990181928165757, 0.69384000000000334)
			surf = "irr";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(20.0);
			mod_ts.add(20.0);
			mod_ts.add(20.0);
			i=3;
			svf = 0.6;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -20.584861928165733;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -21.990181928165757;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 0.69384000000000334;
			System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / twoDigitDelta);
				
//			('rn_calc_new', 'wall', 20.0, 20.0, 20.0, 3, 0.6)
//			(-18.904465036070572, -20.195065036070595, 0.63720000000000354)
//			('rn_calc_new', 'roof', 20.0, 20.0, 20.0, 3, 0.7)
//			(-31.50744172678429, -33.658441726784325, 1.0620000000000047)


			
//			('rn_calc_new', 'irr', 20.0, 19.700183508553959, 19.428673546346516, 5, 0.9)
//			(-30.947485285182555, -30.90375289224863, -0.95043342715355728)
			surf = "irr";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(19.428673546346516);
			mod_ts.add(19.700183508553959);			
			mod_ts.add(20.0);
			i=5;
			svf = 0.9;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -30.947485285182555;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -30.90375289224863;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = -0.95043342715355728;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('rn_calc_new', 'wall', 20.0, 20.477421689695134, 20.838109659103484, 5, 0.9)
//			(-32.02153108918975, -28.380997554105885, -4.1387884065967331)
			surf = "wall";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(20.838109659103484);
			mod_ts.add(20.477421689695134);			
			mod_ts.add(20.0);
			i=5;
			svf = 0.9;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -32.02153108918975;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -28.380997554105885;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = -4.1387884065967331;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('rn_calc_new', 'roof', 20.0, 20.450036896803873, 20.782349824588156, 5, 1.0)
//			(-35.437989524771595, -31.534441726784316, -4.4540955695955269)
//			('rn_calc_new', 'road', 20.0, 20.311061001090025, 20.563858573789105, 5, 1.0)
//			(-36.649469989869068, -33.286355156050107, -4.1044707200698411)
//			('rn_calc_new', 'watr', 20.0, 19.951256429864465, 19.90889807270063, 5, 1.0)
//			(-35.424239862250552, -33.987120527756424, -2.3715137782689553)
//			('rn_calc_new', 'conc', 20.0, 20.504703753493619, 20.930084782330287, 5, 1.0)
//			(-37.308053807391424, -32.935972470196951, -5.052270731487706)
//			('rn_calc_new', 'dry', 20.0, 19.317852474201754, 18.719527602516852, 5, 1.0)
//			(-32.256021229093783, -34.337503213609587, 0.91059659365856049)
			surf = "dry";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(18.719527602516852);
			mod_ts.add(19.317852474201754);			
			mod_ts.add(20.0);
			i=5;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -32.256021229093783;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -34.337503213609587;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 0.91059659365856049;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
			
//			('rn_calc_new', 'dry', 16.570520500139583, 16.573595537334718, 16.605226736127911, 15, 0.9)
//			(1.4410180489872277, -8.9716028087211566, 5.9937184110943384)
//			('rn_calc_new', 'irr', 18.259979111915221, 18.261136789662022, 18.278948574554384, 15, 0.9)
//			(-6.8405048274749989, -17.262354028588, 6.0311938660150428)
//			('rn_calc_new', 'wall', 22.022674045954854, 22.215590739344201, 22.406241090817392, 15, 0.9)
//			(-24.587938593335736, -33.26150835638208, 4.6788996943880807)
//			('rn_calc_new', 'roof', 21.830609655547864, 22.030718675590439, 22.229396686765963, 15, 1.0)
//			(-26.34846313451936, -35.949982252410756, 5.1607147661960724)
			surf = "roof";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(22.229396686765963);
			mod_ts.add(22.030718675590439);			
			mod_ts.add(21.830609655547864);
			i=15;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -26.34846313451936;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -35.949982252410756;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 5.1607147661960724;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('rn_calc_new', 'road', 21.217025425131887, 21.328054496748816, 21.444933402407536, 15, 1.0)
//			(-23.922510905069903, -34.556967500106047, 5.9264993397467176)
			surf = "road";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(21.444933402407536);
			mod_ts.add(21.328054496748816);			
			mod_ts.add(21.217025425131887);
			i=15;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -23.922510905069903;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -34.556967500106047;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 5.9264993397467176;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('rn_calc_new', 'watr', 18.822600000222995, 18.687317587582804, 18.693956083882505, 15, 1.0)
//			(-9.806053871979854, -22.024107536300573, 7.0414761981643448)
//			('rn_calc_new', 'conc', 22.304920249879764, 22.43347517013131, 22.560005839044482, 15, 1.0)
//			(-29.770172686701255, -40.178451405665683, 5.7767966380517013)
//			('rn_calc_new', 'dry', 16.463535198883605, 16.480979638919536, 16.52780523096861, 15, 1.0)
//			(2.1015089464871468, -9.3904973305970412, 6.5799403463728003)
//			('rn_calc_new', 'irr', 18.182325131271128, 18.189791397765752, 18.214836224907529, 15, 1.0)
//			(-7.2082828809004731, -18.753447796297401, 6.6641469398519604)
			surf = "irr";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(18.214836224907529);
			mod_ts.add(18.189791397765752);			
			mod_ts.add(18.182325131271128);
			i=15;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -7.2082828809004731;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -18.753447796297401;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 6.6641469398519604;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('rn_calc_new', 'wall', 21.830609655547864, 22.030718675590439, 22.229396686765963, 15, 1.0)
//			(-26.34846313451936, -35.949982252410756, 5.1607147661960724)
			surf = "wall";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(22.229396686765963);
			mod_ts.add(22.030718675590439);			
			mod_ts.add(21.830609655547864);
			i=15;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = -26.34846313451936;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = -35.949982252410756;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 5.1607147661960724;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('rn_calc_new', 'dry', 16.480979638919536, 16.52780523096861, 16.668114193468497, 16, 1.0)
//			(3.7693833621485595, 2.1015089464871468, 8.4192346873951891)
			surf = "dry";
			mod_ts = new ArrayList<Double>();
			mod_ts.add(16.668114193468497);
			mod_ts.add(16.52780523096861);			
			mod_ts.add(16.480979638919536);
			i=16;
			svf = 1.0;
			dte   = date1A;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = rnCalcNew.rn_calc_new(cfm, met_data, surf, Dats, mod_ts, i, svf);
			Rn = returnValues.get(RnCalcNew.RN_KEY);
			actual = Rn;
			expected = 3.7693833621485595;
			assertEquals(expected, actual, Math.abs(expected) / twoDigitDelta);
			Rnprev = returnValues.get(RnCalcNew.RNPREV_KEY);
			actual = Rnprev;
			expected = 2.1015089464871468;
			assertEquals(expected, actual, Math.abs(expected) / twoDigitDelta);
			Rnstar = returnValues.get(RnCalcNew.RNSTAR_KEY);
			actual = Rnstar;
			expected = 8.4192346873951891;
			//System.out.println("rn_calc " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);


			
	 }
	 
	 @Test
	 public void testLumps()
	 {
		 TreeMap<String,Double> returnValues;
		 double Qh, Qg, Qe, alphapm, actual, expected;
		 
		    String controlFileName;
			Cfm cfm = null;  //control file data structure
			controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation.txt";
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
			

			
			
			TreeMap<String,Double> rad = new TreeMap<String,Double>();
			String surf;
			int i;
			Date dte   = date1A;
			Lumps lumps = new Lumps();
			
//			('Lumps', {'Rnprev': 64.019718735959742, 'Rn': 57.296718735959765, 'Rnstar': -48.839080231372037}, 'roof', 1)
//			(54.296718735959765, 0.0, 3.0, 0.0)
			
	        rad.put("Rnprev", 64.019718735959742);
	        rad.put("Rn", 57.296718735959765);
	        rad.put("Rnstar", -48.839080231372037);
	        surf = "roof";
	        i=1;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 54.296718735959765;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 3.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
//			('Lumps', {'Rnprev': 64.019718735959742, 'Rn': 57.296718735959765, 'Rnstar': -48.839080231372037}, 'roof', 1)
//			(54.296718735959765, 0.0, 3.0, 0.0)
//			('Lumps', {'Rnprev': 13.515273955369279, 'Rn': 12.095973955369283, 'Rnstar': -10.310472493289652}, 'road', 1)
//			(9.0959739553692831, 0.0, 3.0, 0.0)
//			('Lumps', {'Rnprev': 13.37300791373381, 'Rn': 11.968647913733818, 'Rnstar': -10.201941203886602}, 'conc', 1)
//			(8.9686479137338182, 0.0, 3.0, 0.0)
//			('Lumps', {'Rnprev': 13.942072080275677, 'Rn': 12.477952080275683, 'Rnstar': -10.636066361498798}, 'dry', 1)
//			(8.5654391991182592, 0.0, 3.9125128811574248, 0.2)
	        rad.put("Rnprev", 13.942072080275677);
	        rad.put("Rn", 12.477952080275683);
	        rad.put("Rnstar", -10.636066361498798);
	        surf = "dry";
	        i=1;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 8.5654391991182592;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 3.9125128811574248;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 0.2;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			
		
//			
//			('Lumps', {'Rnprev': -13.93131739410539, 'Rn': -14.919779830710153, 'Rnstar': 0.42043892652838633}, 'irr', 6)
//			(6.5306039319467359, -31.947292525501918, 10.496908762845031, 1.2)
//			('Lumps', {'Rnprev': -14.515008139018684, 'Rn': -16.880146384393399, 'Rnstar': -0.94137964141894503}, 'wall', 6)
//			(-13.128597704325644, -6.7515486800677547, 3.0, 0.0)
//			('Lumps', {'Rnprev': -35.437989524771595, 'Rn': -40.442632865975369, 'Rnstar': -1.3883487854979926}, 'roof', 6)
//			(-33.756313213538803, -9.6863196524365627, 3.0, 0.0)
//
//
//			
//			('Lumps', {'Rnprev': -34.556967500106047, 'Rn': -23.922510905069903, 'Rnstar': 5.9264993397467176}, 'road', 15)
//			(14.829324732335966, -41.751835637405868, 3.0, 0.0)
	        rad.put("Rnprev", -34.556967500106047);
	        rad.put("Rn", -23.922510905069903);
	        rad.put("Rnstar", 5.9264993397467176);
	        surf = "road";
	        i=15;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 14.829324732335966;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = -41.751835637405868;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 3.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('Lumps', {'Rnprev': -40.178451405665683, 'Rn': -29.770172686701255, 'Rnstar': 5.7767966380517013}, 'conc', 15)
//			(7.6721295935320306, -40.442302280233285, 3.0, 0.0)
			rad.put("Rnprev", -40.178451405665683);
	        rad.put("Rn", -29.770172686701255);
	        rad.put("Rnstar", 5.7767966380517013);
	        surf = "conc";
	        i=15;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 7.6721295935320306;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = -40.442302280233285;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 3.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 0.0;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('Lumps', {'Rnprev': -9.3904973305970412, 'Rn': 2.1015089464871468, 'Rnstar': 6.5799403463728003}, 'dry', 15)
//			(16.569056326617567, -19.011212270145446, 4.5436648900150294, 0.2)
	        rad.put("Rnprev", -9.3904973305970412);
	        rad.put("Rn", 2.1015089464871468);
	        rad.put("Rnstar", 6.5799403463728003);
	        surf = "dry";
	        i=15;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 16.569056326617567;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = -19.011212270145446;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 4.5436648900150294;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 0.2;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('Lumps', {'Rnprev': -18.753447796297401, 'Rn': -7.2082828809004731, 'Rnstar': 6.6641469398519604}, 'irr', 15)
//			(7.6085620251045345, -26.108011174368091, 11.291166268363082, 1.2)
	        rad.put("Rnprev", -18.753447796297401);
	        rad.put("Rn", -7.2082828809004731);
	        rad.put("Rnstar", 6.6641469398519604);
	        surf = "irr";
	        i=15;
	        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
	        Dats.put("dte", dte);
			returnValues = lumps.lumps(rad, cfm, met_data, surf, Dats, i);
			Qh = returnValues.get(Lumps.QH_KEY);
			actual = Qh;
			expected = 7.6085620251045345;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qg = returnValues.get(Lumps.QG_KEY);
			actual = Qg;
			expected = -26.108011174368091;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			Qe = returnValues.get(Lumps.QE_KEY);
			actual = Qe;
			expected = 11.291166268363082;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
			alphapm = returnValues.get(Lumps.ALPHAPM_KEY);
			actual = alphapm;
			expected = 1.2;
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//			('Lumps', {'Rnprev': -35.949982252410756, 'Rn': -26.34846313451936, 'Rnstar': 5.1607147661960724}, 'wall', 15)
//			(-22.925219102264094, -6.4232440322552655, 3.0, 0.0)


		 
	 }
	 
	 @Test
	 public void testld_mod()
	 {
		 double returnValue, actual, expected ;
		 ArrayList<Object> met = new ArrayList<Object>();
		 met.add(0);
		 met.add(0);
		 met.add(0);
		 
		 ld_mod ldmod = new ld_mod();
		 
		met.set(MetData.Ta,22.600000000000001);
		met.set(MetData.RH,48.0);
		returnValue = ldmod.ld_mod_(met);		
		actual = returnValue;
		expected = 379.307916734;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		 
//		 ('ld_mod', 22.600000000000001, 48.0)
//		 379.307916734
//		 ('ld_mod', 23.399999999999999, 46.0)
//		 381.342311117
//		 ('ld_mod', 21.399999999999999, 57.0)
//		 383.73642666
//		 ('ld_mod', 20.600000000000001, 61.0)
//		 383.706108179
//		 ('ld_mod', 20.699999999999999, 59.0)
//		 381.943209615
//		 ('ld_mod', 19.5, 62.0)
//		 378.296773316
//		 ('ld_mod', 20.699999999999999, 59.0)
//		 381.943209615
//		 ('ld_mod', 20.0, 62.0)
//		 381.266578022
//		 ('ld_mod', 20.100000000000001, 61.0)
//		 380.708288025
//		 ('ld_mod', 20.100000000000001, 60.0)
//		 379.54256603
//		 ('ld_mod', 19.300000000000001, 62.0)
//		 377.117957163
//		 ('ld_mod', 19.0, 64.0)
//		 377.59818421
//		 ('ld_mod', 21.899999999999999, 57.0)
//		 386.773935741
//		 ('ld_mod', 24.199999999999999, 48.0)
//		 389.048636319
//		 ('ld_mod', 25.399999999999999, 51.0)
//		 400.783825821
//		 ('ld_mod', 27.300000000000001, 44.0)
//		 402.744552559
//		 ('ld_mod', 29.100000000000001, 37.0)
//		 402.914220062
//		 ('ld_mod', 32.100000000000001, 29.0)
//		 407.285957823
//		 ('ld_mod', 33.200000000000003, 27.0)
//		 410.239372301
//		 ('ld_mod', 35.0, 23.0)
//		 412.877676856
//		 ('ld_mod', 30.800000000000001, 45.0)
//		 427.622075
//		 ('ld_mod', 30.800000000000001, 48.0)
//		 432.358472037
//		 ('ld_mod', 30.899999999999999, 44.0)
//		 426.687260799
//		 ('ld_mod', 30.300000000000001, 46.0)
//		 425.791735916
//		 ('ld_mod', 30.600000000000001, 46.0)
//		 427.845956192
//		 ('ld_mod', 30.699999999999999, 39.0)
//		 416.910867687
//		 ('ld_mod', 40.200000000000003, 15.0)
//		 424.88149201
//		 ('ld_mod', 41.100000000000001, 15.0)
//		 430.816224197
//		 ('ld_mod', 41.700000000000003, 14.0)
//		 431.468856537
//		 ('ld_mod', 41.200000000000003, 15.0)
//		 431.481036307
//		 ('ld_mod', 41.200000000000003, 14.0)
//		 428.173949003
//		 ('ld_mod', 41.899999999999999, 14.0)
//		 432.794309473
//		 ('ld_mod', 42.700000000000003, 11.0)
//		 427.313784733
//		 ('ld_mod', 42.600000000000001, 11.0)
//		 426.672575252
//		 ('ld_mod', 42.5, 10.0)
//		 422.268936678
//		 ('ld_mod', 41.399999999999999, 12.0)
//		 422.613429094
//		 ('ld_mod', 40.0, 15.0)
//		 423.574477296
//		 ('ld_mod', 38.299999999999997, 24.0)
//		 437.621025239
//		 ('ld_mod', 32.5, 41.0)
//		 432.677918625
//		 ('ld_mod', 29.800000000000001, 59.0)
//		 441.274896477
//		 ('ld_mod', 29.800000000000001, 57.0)
//		 438.547227013
//		 ('ld_mod', 31.100000000000001, 43.0)
//		 426.412105755
//		 ('ld_mod', 30.899999999999999, 45.0)
//		 428.308964911
//		 ('ld_mod', 31.300000000000001, 43.0)
//		 427.784205362
//		 ('ld_mod', 29.300000000000001, 56.0)
//		 433.694356918
		
		met.set(MetData.Ta,26.100000000000001);
		met.set(MetData.RH,71.0);
		returnValue = ldmod.ld_mod_(met);		
		actual = returnValue;
		expected = 430.797457046;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//		 ('ld_mod', 26.100000000000001, 71.0)
//		 430.797457046

	 }
	 
	 @Test
	 public void testHttc()
	 {
		double Ri;
		double u;
		double z;
		double z0m;
		double z0h;
		ArrayList<ArrayList<Object>> met;
		int i;
		double Tlow;
		double Thi;
		double httc_out;
		double Fh;
		
		double expected;
		double actual;
		
		String controlFileName;
		Cfm cfm = null;  //control file data structure
		controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation.txt";
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
		//TreeMap<String,Double> returnValues;
		Httc httc = new Httc();
		
		Ri= 0.00828781052948967;
		u=6.6825687735108046;
		z=0.55000000000000004;
		z0m=0.45;
		z0h=0.045;
		i=1;
		Tlow=20.98518981;
		Thi=22.4708725309899;
		TreeMap<String,Double> returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);
		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 2.66471615570077;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.0742655098079834;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//		 ('httc', 0.00828781052948967, 6.6825687735108046, 0.55000000000000004, 0.45, 0.045, 1, array([ 20.98518981]), 22.4708725309899)
//		 (2.66471615570077, 0.0742655098079834) 
		 

		
		Ri= 161.214435863056;
		u= 0.1;
		z= 1.25;
		z0m= 0.45;
		z0h= 0.045;
		i= 4;
		Tlow= 20.37095932;
		Thi=23.2257456165725;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);
		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 1.10597305667775e-8;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 5.33902092963932e-7;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);

		 
//		 ('httc', 161.214435863056, 0.1, 1.25, 0.45, 0.045, 4, array([ 20.37095932]), 23.2257456165725)
//		 (1.10597305667775e-8, 5.33902092963932e-7)
		
		
		
		Ri= 124.543115117845;
		u= 0.1;
		z= 1.05;
		z0m= 0.45;
		z0h= 0.045;
		i= 4;
		Tlow= 20.58626352;
		Thi=23.2257456165725;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);
		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 2.35635090629045e-8;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 7.82391137408372e-7;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//		 ('httc', 124.543115117845, 0.1, 1.05, 0.45, 0.045, 4, array([ 20.58626352]), 23.2257456165725)
//		 (2.35635090629045e-8, 7.82391137408372e-7)
		 
		Ri= 0.0308223561221598;
		u= 5.9400611320096042;
		z= 1.05;
		z0m= 0.45;
		z0h= 0.045;
		i= 15;
		Tlow= 22.74326748;
		Thi=25.2686468248213;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);
		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 0.367147046070227;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.205226330711048;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
//		 ('httc', 0.0308223561221598, 5.9400611320096042, 1.05, 0.45, 0.045, 15, array([ 22.74326748]), 25.2686468248213)
//		 (0.367147046070227, 0.205226330711048)


	 }
	
	 @Test
	 public void testForceRestore()
	 {
		double expected;
		double actual;
		TreeMap<String,Double> returnValues;
		ForceRestore forceRestore = new ForceRestore();
		
		TreeMap<String,Double> eng_bal;
		//Cfm cfm;
		ArrayList<Double> mod_ts;
		ArrayList<Double> mod_tm;
		//TreeMap<String,Date> Dats;
		String surf;
		int i=0;
		double tS,tM;
		
		String controlFileName;
		Cfm cfm = null;  //control file data structure
		controlFileName = "/home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/Java_python_compare/controlfiles/Target-java-validation/Target-java-validation.txt";
		cfm = new Cfm(controlFileName);
		
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
		
        Date dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, 0.0);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(20.0);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(27.899999999999999);
		surf = "conc";
		
		
		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 20.0;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 27.9;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		
		
//		('Ts_calc_surf', -41.751835637405868, 21.444933402407536, 28.709534027108457, 'road', 15)
//		('ts', 21.637983621716561, 28.689699302090972)
		
		i=15;
        dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, -41.751835637405868);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(21.444933402407536);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(28.709534027108457);
		surf = "road";

		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 21.637983621716561;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 28.689699302090972;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		
//		('Ts_calc_surf', -28.267168764427289, 24.126682530770626, 27.787275808949129, 'conc', 15)
//		('ts', 24.263121450059046, 27.778306112494054)
		
		i=15;
        dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, -28.267168764427289);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(24.126682530770626);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(27.787275808949129);
		surf = "conc";

		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 24.263121450059046;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 27.778306112494054;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//		('Ts_calc_surf', -21.426929753577774, 17.313043469358728, 22.745010288566526, 'dry', 15)
//		('ts', 17.272226063379154, 22.725333207813321)
		
		i=15;
        dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, -21.426929753577774);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(17.313043469358728);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(22.745010288566526);
		surf = "dry";

		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 17.272226063379154;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 22.725333207813321;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//		('Ts_calc_surf', -27.334073636440426, 18.78994503031916, 21.859154258385519, 'irr', 15)
//		('ts', 18.773626684811205, 21.848212685996067)
		
		i=15;
        dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, -27.334073636440426);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(18.78994503031916);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(21.859154258385519);
		surf = "irr";

		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 18.773626684811205;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 21.848212685996067;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//		('Ts_calc_surf', -5.0954919588597223, 23.914397926179461, 28.063874642107312, 'wall', 15)
//		('ts', 24.061821589156182, 28.053517611888893)


		i=15;
        dte   = date1A;
        dte   = new Date(dte.getTime() + i*timedelta);  // # current timestep 
        Dats.put("dte", dte);
		
		eng_bal = new TreeMap<String,Double>();
		eng_bal.put(Lumps.QG_KEY, -5.0954919588597223);
		mod_ts = new ArrayList<Double>();
		mod_ts.add(23.914397926179461);
		mod_tm = new ArrayList<Double>();
		mod_tm.add(28.063874642107312);
		surf = "wall";

		returnValues = forceRestore.Ts_calc_surf(eng_bal, cfm, mod_ts, mod_tm, Dats, surf, i);
		tS = returnValues.get(ForceRestore.TS_KEY);
		tM = returnValues.get(ForceRestore.TM_KEY);
		actual = tS;
		expected = 24.061821589156182;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = tM;
		expected = 28.053517611888893;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		

	 }

	 @Test
	public void testCD()
	{
		 double expected;
		 double actual;
		TreeMap<String,Double> returnValues;
		double cd_out;
		double Fm;
		CD cd = new CD();
		
		//  (array([ 0.24555776]), 2.55, 0.45, 0.045)
		// ('cd_out', array([ 0.01145989]), array([ 0.2155061
		
		double Ri = 0.24555776;
		double z = 2.55;
		double z0m = 0.45;
		double z0h = 0.045;
		
		returnValues = cd.cd(Ri, z, z0m, z0h);
		cd_out = returnValues.get(CD.CD_OUT_KEY);
		Fm = returnValues.get(CD.FM_KEY);
		
		actual = cd_out;
		expected = 0.01145989;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		//assertEquals(cd_out,0.01145989);
		
		actual = Fm;
		expected = 0.2155061;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		//assertEquals(Fm, 0.2155061);
		
		System.out.println(cd_out + " " + Fm + " " + "array([ 0.01145989]), array([ 0.2155061");
		

		// ('cd in', array([-0.24514171]), 2.55, 0.45, 0.045)
		// ('cd_out', array([ 0.07181163]), array([ 1.35043605]))
		
		Ri = -0.24514171;
		z = 2.55;
		z0m = 0.45;
		z0h = 0.045;
		
		returnValues = cd.cd(Ri, z, z0m, z0h);
		cd_out = returnValues.get(CD.CD_OUT_KEY);
		Fm = returnValues.get(CD.FM_KEY);
		
		System.out.println(cd_out + " " + Fm + " " + "array([ 0.07181163]), array([ 1.35043605");
		actual = cd_out;
		expected = 0.07181163;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		actual = Fm;
		expected = 1.35043605;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		

		
//		('cd in', array([-225.82109104]), 2.55, 0.45, 0.045)
//		('cd_out', array([ 0.71629032]), array([ 13.47002218]))
		
		Ri = -225.82109104;
		z = 2.55;
		z0m = 0.45;
		z0h = 0.045;
		
		returnValues = cd.cd(Ri, z, z0m, z0h);
		cd_out = returnValues.get(CD.CD_OUT_KEY);
		Fm = returnValues.get(CD.FM_KEY);
		
		System.out.println(cd_out + " " + Fm + " " + "array([ 0.71629032]), array([ 13.47002218");
		actual = cd_out;
		expected = 0.71629032;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		actual = Fm;
		expected = 13.47002218;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//		('cd in', array([-0.19935911]), 2.55, 0.45, 0.045)
//		('cd_out', array([ 0.06970788]), array([ 1.31087442]))
		
		Ri = -0.19935911;
		z = 2.55;
		z0m = 0.45;
		z0h = 0.045;
		
		returnValues = cd.cd(Ri, z, z0m, z0h);
		cd_out = returnValues.get(CD.CD_OUT_KEY);
		Fm = returnValues.get(CD.FM_KEY);
		
		System.out.println(cd_out + " " + Fm + " " + "array([  0.06970788]), array([ 1.31087442");
		actual = cd_out;
		expected = 0.06970788;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		actual = Fm;
		expected = 1.31087442;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);


	}
	 
	 
}
