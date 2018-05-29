package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Target.SfcRi;

public class testsfc_ri
{
	double eightDigitDelta = 1e8;
	double sevenDigitDelta = 1e7;
	double sixDigitDelta = 1e6;
	double fiveDigitDelta = 1e5;
	double fourDigitDelta = 1e4;
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;

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
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		 
		 
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
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
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
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		 
//		 ('sfc_ri', 1.25, 23.2257456165725, array([ 20.37642197]), 0.1)
//		 160.887104537979
		 dz =1.25;
		 Thi = 23.2257456165725;
		 Tlo = 20.37642197;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 160.887104537979;
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		 
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
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);

		 
/////////
		 
		 
		 dz =1.05;
		 Thi = 21.9349092163618;
		 Tlo = 22.0421811;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = -4.54319491372981;
		 assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
//		 ('Ri_urb_new', 1.05, 21.9349092163618, array([ 22.0421811]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -4.54319491372981})
		 
		 dz =1.25;
		 Thi =  21.9349092163618;
		 Tlo =  21.85587529;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 5.10749596809942;
		 assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		 
//		 ('Ri_urb_new', )
//		 ('Ri_urb_new_return', {'Ri': 5.10749596809942})
		 

		 dz =1.25;
		 Thi =  21.9349092163618;
		 Tlo =  21.94020728;
		 Uhi = 0.1;
		 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
		 actual = returnValue;
		 expected = 0.385671229042339;
		 assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta); 
		 
//		 ('Ri_urb_new', )
//		 ('Ri_urb_new_return', {'Ri': 0.385671229042339})
//	
//		 ('Ri_urb_new', 0.55000000000000004, 21.9349092163618, array([ 21.9740516]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -0.829690002864237})
//		 ('Ri_urb_new', 1.25, 21.9349092163618, array([ 21.909428]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': 2.10692611823909})
//		 ('Ri_urb_new', 1.05, 21.9349092163618, array([ 22.00577311]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -2.84071571483101})
//		 ('Ri_urb_new', 0.55000000000000004, 21.9349092163618, array([ 21.97161108]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -0.769779388261141})
//		 ('Ri_urb_new', 0.55000000000000004, 21.9349092163618, array([ 21.96047928]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -0.496428312861514})

//		 ('Ri_urb_new', 1.25, 21.9349092163618, array([ 21.94305763]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': 0.226395080491514})
//		 ('Ri_urb_new', 1.05, 21.9349092163618, array([ 22.04724041]), 0.1)
//		 ('Ri_urb_new_return', {'Ri': -4.77955108565702})
		 
		 
		 
		 
//			('Ri_urb_new', 0.049999999999999989, 24.3903759871615, array([ 22.22939669]), 5.9400611320096042)
//			('Ri_urb_new_return', {'Ri': 0.00128851234047300})
			
			 dz =0.049999999999999989;
			 Thi =  24.3903759871615;
			 Tlo =  22.22939669;
			 Uhi = 5.9400611320096042;
			 returnValue = sfcRi.sfc_ri(dz, Thi, Tlo, Uhi);
			 actual = returnValue;
			 expected = 0.00128851234047300;
			 assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta); 
		 
	 }

}
