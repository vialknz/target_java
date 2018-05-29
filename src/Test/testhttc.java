package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;

import Target.Cfm;
import Target.Httc;
import Target.MetData;

public class testhttc
{
	
	double eightDigitDelta = 1e8;
	double fiveDigitDelta = 1e5;
	double fourDigitDelta = 1e4;
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;

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
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.0742655098079834;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
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
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 5.33902092963932e-7;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);

		 
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
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 7.82391137408372e-7;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
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
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.205226330711048;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
//		 ('httc', 0.0308223561221598, 5.9400611320096042, 1.05, 0.45, 0.045, 15, array([ 22.74326748]), 25.2686468248213)
//		 (0.367147046070227, 0.205226330711048)

		
		
		

		
		///////////////////////
		
		Ri= 0.0209735635990656;
		u= 5.9400611320096042;
		z= 0.55000000000000004;
		z0m= 0.45;
		z0h= 0.045;
		i= 15;
		Tlow= 22.02905384;
		Thi=25.2686468248213;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 2.11850735983298;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.0664230343520993;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);

//		('Ri_urb_new calcuation', 0.0209735635990656, 5.9400611320096042, 0.55000000000000004, 0.45, 0.045, 15, array([ 22.02905384]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.0664230343520993, 'httc': 2.11850735983298})
		
	///////////////////////
		
		Ri= 0.0308223561221598;
		u= 5.9400611320096042;
		z= 1.05;
		z0m= 0.45;
		z0h= 0.045;
		i= 15;
		Tlow= 22.74326748 ;
		Thi=25.2686468248213;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = 0.367147046070227;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = 0.205226330711048;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		
//		('Ri_urb_new calcuation', 0.0308223561221598, 5.9400611320096042, 1.05, 0.45, 0.045, 15, array([ 22.74326748]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.205226330711048, 'httc': 0.367147046070227})


//		('Ri_urb_new calcuation', 0.0537671790912694, 5.9400611320096042, 1.25, 0.45, 0.045, 15, array([ 21.64996375]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.195845068745237, 'httc': 0.240983040119928})
//		('Ri_urb_new calcuation', 0.0341287406709907, 5.9400611320096042, 1.05, 0.45, 0.045, 15, array([ 22.48629224]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.199766403094907, 'httc': 0.357379311642202})
//		('Ri_urb_new calcuation', 0.0213375554067640, 5.9400611320096042, 0.55000000000000004, 0.45, 0.045, 15, array([ 21.97641247]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.0662166415123322, 'httc': 2.11192463210414})
//		('Ri_urb_new calcuation', 0.0216783155211266, 5.9400611320096042, 0.55000000000000004, 0.45, 0.045, 15, array([ 21.92723701]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.0660242922440635, 'httc': 2.10578981239196})
//		('Ri_urb_new calcuation', 0.0487687934107217, 5.9400611320096042, 1.25, 0.45, 0.045, 15, array([ 21.9653646]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.203402485232060, 'httc': 0.250282274520443})
//		('Ri_urb_new calcuation', 0.0484809695117265, 5.9400611320096042, 1.25, 0.45, 0.045, 15, array([ 21.98365561]), 25.2686468248213)
//		('httc_urb_new', {'Fh': 0.203850921866140, 'httc': 0.250834065913886})

		
		
//		('Ri_urb_new calcuation', 0.00128851234047300, 5.9400611320096042, 0.049999999999999989, 0.45, 0.045, 15, array([ 22.22939669]), 24.3903759871615)
//		('httc_urb_new', {'Fh': -20.6040332882029, 'httc': -5.48128676708857})
		
	///////////////////////
		
		Ri= 0.00128851234047300;
		u= 5.9400611320096042;
		z= 0.049999999999999989;
		z0m= 0.45;
		z0h= 0.045;
		i= 15;
		Tlow= 22.22939669 ;
		Thi=24.3903759871615;
		returnValues = httc.httc(Ri, u, z, z0m, z0h, met_data, i, Tlow, Thi);		
		httc_out = returnValues.get(Httc.HTTC_KEY);
		actual = httc_out;
		expected = -5.48128676708857;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);				
		Fh = returnValues.get(Httc.FH_KEY);
		actual = Fh;
		expected = -20.6040332882029;
		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);

		

	 }

}
