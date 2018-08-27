package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Target.TbRurSolver;
import Target.TbRurSolver_python;

public class TestTbRurSolver
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
	
	
	TbRurSolver solver = new TbRurSolver();
	TbRurSolver_python solverP = new TbRurSolver_python();
	
	double dz;
	double ref_ta ;
	double UTb;
	double[] mod_U_TaRef = new double[1];
//	mod_U_TaRef[0];
	int i=0;
	double Ri_rur;


	double value;
	
	//lots of test cases:
	
//	Thu Feb 05 00:08:00 AEDT 2009 16
//ERROR result=-27.49970305597543 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 16 0.01 27.3 1.1602874265131378 0.9620188612007577 4.985440933505587 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:08:30 AEDT 2009 17
//ERROR result=-29.25011135390482 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 17 0.01 29.1 6.381580845822257 5.2911037366041676 0.19807380438759148 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:09:00 AEDT 2009 18
//ERROR result=-32.50011135290482 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 18 0.01 32.1 7.541868272335397 6.253122597804925 0.17746039653265824 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:09:30 AEDT 2009 19
//ERROR result=-33.500111352404815 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 19 0.01 33.2 8.702155698848536 7.215141459005683 0.14097704630491084 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:10:00 AEDT 2009 20
//ERROR result=-35.0000000005 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 20 0.01 35.0 7.541868272335396 6.253122597804925 0.20503217586271136 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:10:30 AEDT 2009 21
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 21 0.01 30.8 7.541868272335395 6.253122597804925 0.15613700524653532 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:11:00 AEDT 2009 22
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 22 0.01 30.8 8.702155698848534 7.215141459005683 0.11581041913191047 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:11:30 AEDT 2009 23
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 23 0.01 30.9 8.702155698848534 7.215141459005683 0.11527835182661979 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:12:00 AEDT 2009 24
//ERROR result=-30.499703058975427 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 24 0.01 30.3 9.862443125361672 8.177160320206442 0.08434209468461015 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:12:30 AEDT 2009 25
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 25 0.01 30.6 10.442586838618242 8.65816975080682 0.07617709071758444 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:13:00 AEDT 2009 26
//ERROR result=-30.99970305797543 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 26 0.01 30.7 9.862443125361672 8.177160320206442 0.0850413108983807 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:13:30 AEDT 2009 27
//ERROR result=-40.0001113496548 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 27 0.01 40.2 6.381580845822258 5.2911037366041676 0.3376734070455994 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:14:00 AEDT 2009 28
//ERROR result=-41.000111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 28 0.01 41.1 8.702155698848532 7.215141459005683 0.18616121249750844 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:14:30 AEDT 2009 29
//ERROR result=-41.499703068920525 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 29 0.01 41.7 8.702155698848536 7.215141459005683 0.18874116192706014 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:15:00 AEDT 2009 30
//ERROR result=-41.500111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 30 0.01 41.2 9.86244312536167 8.177160320206442 0.14363063065867138 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:15:30 AEDT 2009 31
//ERROR result=-41.500111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 31 0.01 41.2 8.702155698848534 7.215141459005683 0.1837656907924875 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:16:00 AEDT 2009 32
//ERROR result=-41.99970306842052 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 32 0.01 41.9 9.862443125361672 8.177160320206442 0.1461972766580597 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:16:30 AEDT 2009 33
//ERROR result=-42.999703069420526 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 33 0.01 42.7 9.862443125361674 8.177160320206442 0.14911867420258956 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:17:00 AEDT 2009 34
//ERROR result=-42.999703068920525 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 34 0.01 42.6 8.702155698848534 7.215141459005683 0.190173766232043 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:17:30 AEDT 2009 35
//ERROR result=-42.49970306992053 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 35 0.01 42.5 12.183017978387948 10.101198042607956 0.09676466414991208 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:18:00 AEDT 2009 36
//ERROR result=-41.49970306892053 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 36 0.01 41.4 8.702155698848534 7.215141459005683 0.18268091444732085 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:18:30 AEDT 2009 37
//ERROR result=-40.0 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 37 0.01 40.0 5.22129341930912 4.32908487540341 0.48182079683176393 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:19:00 AEDT 2009 38
//ERROR result=-38.49970306592052 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 38 0.01 38.3 4.641149706052551 3.848075444803031 0.5684744701326649 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:19:30 AEDT 2009 39
//ERROR result=-32.99970305942049 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 39 0.01 32.5 6.381580845822259 5.2911037366041676 0.21643777407207876 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:20:00 AEDT 2009 40
//ERROR result=-29.99970305747543 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 40 0.01 29.8 9.862443125361672 8.177160320206442 0.0714333908179105 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:20:30 AEDT 2009 41
//ERROR result=-29.99970305747543 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 41 0.01 29.8 6.381580845822259 5.2911037366041676 0.17038268744120127 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:21:00 AEDT 2009 42
//ERROR result=-31.250111352904817 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 42 0.01 31.1 5.22129341930912 4.32908487540341 0.28787843394490265 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:21:30 AEDT 2009 43
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 43 0.01 30.9 4.64114970605255 3.848075444803031 0.3575336084376941 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:22:00 AEDT 2009 44
//ERROR result=-31.49970305997543 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 44 0.01 31.3 0.12060963389687687 0.1 547.7671583320314 
//using previous Tb_rur=0.0
//
//	Thu Feb 05 00:36:30 AEDT 2009 73
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 73 0.01 30.6 10.44258683861824 8.65816975080682 0.06100185356329677 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:37:00 AEDT 2009 74
//ERROR result=-30.999703058475433 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 74 0.01 30.7 9.862443125361674 8.177160320206442 0.0681505309808107 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:37:30 AEDT 2009 75
//ERROR result=-40.0001113496548 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 75 0.01 40.2 6.381580845822258 5.2911037366041676 0.3000128834457065 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:38:00 AEDT 2009 76
//ERROR result=-41.000111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 76 0.01 41.1 8.702155698848534 7.215141459005683 0.16616113769742308 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:38:30 AEDT 2009 77
//ERROR result=-41.499703068920525 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 77 0.01 41.7 8.702155698848534 7.215141459005683 0.16894539842595155 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:39:00 AEDT 2009 78
//ERROR result=-41.500111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 78 0.01 41.2 9.862443125361674 8.177160320206442 0.12824808367798085 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:39:30 AEDT 2009 79
//ERROR result=-41.500111349404804 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 79 0.01 41.2 8.702155698848532 7.215141459005683 0.16409846698541222 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:40:00 AEDT 2009 80
//ERROR result=-41.99970306842052 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 80 0.01 41.9 9.862443125361672 8.177160320206442 0.13102062547070784 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:40:30 AEDT 2009 81
//ERROR result=-42.999703069420526 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 81 0.01 42.7 9.862443125361672 8.177160320206442 0.13411727431008957 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:41:00 AEDT 2009 82
//ERROR result=-42.999703068920525 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 82 0.01 42.6 8.702155698848534 7.215141459005683 0.17098111670783198 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:41:30 AEDT 2009 83
//ERROR result=-42.49970306992053 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 83 0.01 42.5 12.183017978387948 10.101198042607956 0.086989939590447 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:42:00 AEDT 2009 84
//ERROR result=-41.49970306892053 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 84 0.01 41.4 8.702155698848534 7.215141459005683 0.1634281739034404 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:42:30 AEDT 2009 85
//ERROR result=-40.0 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 85 0.01 40.0 5.22129341930912 4.32908487540341 0.4279729504537863 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:43:00 AEDT 2009 86
//ERROR result=-38.49970306642052 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 86 0.01 38.3 4.641149706052552 3.848075444803031 0.49973069820197613 
//using previous Tb_rur=0.0
//	Thu Feb 05 00:43:30 AEDT 2009 87
//ERROR result=-32.99970305942049 iterations=900000
//Error with java Tb_rur, returned value=-9999.0
//Called with 87 0.01 32.5 6.381580845822257 5.2911037366041676 0.17884765530967225 
//using previous Tb_rur=0.0
	

	@Test
	public void test1()
	{
		
		dz=-1.0;
		ref_ta = 21.9;
		UTb = 2.63038178;
//		double[] mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 3.05879268;
		i=0;
		Ri_rur = 0.24555776;
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	

	@Test
	public void Test2()
	{
		//  ('Tb_rur=', 31.4718836061206, 26, '-1.0_30.7_[ 8.94329807]_[ 10.39989511]_[-0.11332934]', 31.4621231872766)
		dz=-1.0;
		ref_ta = 30.7;
		UTb = 8.94329807;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 10.39989511;
		Ri_rur = -0.11332934;
		//TODO this one
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	
	@Test
	public void Test3()
	{
//		('Ok calculating Tb_rur', 0.471325730671747*(-19.612*Thi_tb + 594.2436)/(Thi_tb + 30.3) + 0.103263339976444)
//		('Tb_rur=', 30.9943884684212, 24, '-1.0_30.3_[ 8.94329807]_[ 10.39989511]_[-0.10326334]', 30.9846280495772)
		dz=-1.0;
		ref_ta = 30.3;
		UTb = 8.94329807;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 10.39989511;
		Ri_rur = -0.10326334;
		//TODO this one
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);

	}
	@Test
	public void Test4()
	{
		
		dz=15.0 ;
		ref_ta =16.5 ;
		UTb =  3.975255797000494 ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  1.8692621937604166  ;
		Ri_rur = -1.3626911282063752;
		//  15.8356754268024
		
		//15.0 16.5 3.975255797000494 1.8692621937604166 0 -1.3626911282063752
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	
	@Test
	public void Test5()
	{
		
		
		
		dz=15.0 ;
		ref_ta =16.8 ;
		UTb =  6.143577139636226 ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  2.8888597534372273 ;
		Ri_rur = -0.5326357056213218;
		//   16.1676869029464
		//  dz + ref_ta + UTb + mod_U_TaRef[i] + i + Ri_rur = 15.0 16.8 6.143577139636226 2.8888597534372273 7 -0.5326357056213218
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);

	}
	
	@Test
	public void Test6()
	{
		
		


		dz=15.0 ;
		ref_ta =16.6 ;
		UTb =  1.8069344530637708 ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  0.8496646334718477 ;
		Ri_rur = -6.956094923672547;
		// 15.8958756964009
		// dz + ref_ta + UTb + mod_U_TaRef[i] + i + Ri_rur = 15.0 16.6 1.8069344530637708 0.8496646334718477 12 -6.956094923672547
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);

	}
	
	@Test
	public void Test7()
	{
		
		
		dz=15.0 ;
		ref_ta =23.5 ;
		UTb =  0.21266443039771887 ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  0.1  ;
		Ri_rur = -370.55369919381894;
//		//TODO this should be 22.760359533246
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	
	@Test
	public void Test8()
	{
		
		
		dz=15.0 ;
		ref_ta =24.4 ;
		UTb =  0.21266443039771887 ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  0.1 ;
		Ri_rur = -602.7585857212;
		//  23.1629904161214
		//  15.0 24.4 0.21266443039771887 0.1 38 -602.7585857212
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
	}
	
	@Test
	public void Test9()
	{
		
		dz=15.0 ;
		ref_ta =18.4  ;
		UTb =  1.445547562190818  ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  0.6797317066551265  ;
		Ri_rur = -9.705888769990096;
//		 15.0 18.4 1.445547562190818 0.6797317066551265 59 -9.705888769990096
//		 Python Tb_rur=	17.7014531204627
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
		
	}
	
	@Test
	public void Test10()
	{
		
		
		
		
		
		
		dz=15.0 ;
		ref_ta =20.4  ;
		UTb =   0.7227737817459052  ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =  0.33986585363344246   ;
		Ri_rur = -22.889685313478864;		
//		15.0 20.4 0.7227737817459052 0.33986585363344246 50 -22.889685313478864
//		Python Tb_rur=	19.9397973578936
		//TODO
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	
	@Test
	public void Test11()
	{
		
		
		dz=15.0 ;
		ref_ta =19.6   ;
		UTb =   0.7227737817459055   ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 0.33986585363344246  ;
		Ri_rur = -29.853963929463763;	
//		15.0 19.6 0.7227737817459055 0.33986585363344246 51 -29.853963929463763
//		Python Tb_rur=	19.0252892485087
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
	}
	
	@Test
	public void Test12()
	{
		
		
		dz=15.0 ;
		ref_ta =18.7   ;
		UTb =   0.7227737817459053   ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 0.33986585363344246   ;
		Ri_rur =  -36.639661688103196;	
//		15.0 18.7 0.7227737817459053 0.33986585363344246 56 -36.639661688103196
//		Python Tb_rur=	18.0292827501739
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
		
	}
	
	@Test
	public void Test13()
	{
		
		
		dz=15.0 ;
		ref_ta =18.7   ;
		UTb =   0.7227737817459053   ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 0.33986585363344246   ;
		Ri_rur =  -900.639661688103196;	
//		15.0 18.7 0.7227737817459053 0.33986585363344246 56 -36.639661688103196
//		Python Tb_rur=	18.0292827501739
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
		
	}
	
	@Test
	public void Test14()
	{

		dz=0.01 ;
		ref_ta =32.1  ;
		UTb =   7.541868272335397    ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] = 6.253122597804925   ;
		Ri_rur =   0.17746039653265824;	
//		15.0 18.7 0.7227737817459053 0.33986585363344246 56 -36.639661688103196
//		Python Tb_rur=	18.0292827501739
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
		
	}
	@Test
	public void Test15()
	{

		dz=0.01  ;
		ref_ta =29.1   ;
		UTb =   6.381580845822257    ;
		mod_U_TaRef = new double[1];
		mod_U_TaRef[0] =5.2911037366041676   ;
		Ri_rur =    0.19807380438759148;	
//		15.0 18.7 0.7227737817459053 0.33986585363344246 56 -36.639661688103196
//		Python Tb_rur=	18.0292827501739
		
		solverP.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double Tb_rur = solverP.converge(" "+i+" ", dz+" ", ref_ta+" ", UTb+" ", mod_U_TaRef[i]+" ", Ri_rur+" ");
		System.out.println(Tb_rur);		
		value = solver.convergeNewVersion(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
		System.out.println(value);		
		assertEquals(Tb_rur, value, Math.abs(Tb_rur) / eightDigitDelta);
		
		
	}
	
	
	
	public static void main(String[] args)
	{


		

		

		
//		15.0 18.6 0.7227737817459053 0.33986585363344246 57 -38.06643036395199
//		Python Tb_rur=	17.9073747333698
		
//		15.0 18.4 0.7227737817459053 0.33986585363344246 58 -39.80777369502297
//		Python Tb_rur=	17.6840887203085
		
//		15.0 18.2 0.21266443039771887 0.1 60 -477.5169573455006
//		Python Tb_rur=	17.4651588495703
		
//		 15.0 18.2 0.7227737817459051 0.33986585363344246 61 -45.041233210330724
//		 Python Tb_rur=	17.4008177671521
		
//		15.0 20.2 0.7227737817459055 0.33986585363344246 100 -27.733286267304386
//		Python Tb_rur=	19.649196768224
		
//		15.0 19.6 0.7227737817459053 0.33986585363344246 107 -28.76141635260905
//		Python Tb_rur=	19.046024362753
		
//		 15.0 18.2 0.21266443039771887 0.1 60 -477.5169573455006
//		 Python Tb_rur=	17.4651588495703
		
		
//		dz=15.0 ;
//		ref_ta =18.2    ;
//		UTb =   0.21266443039771887    ;
//		mod_U_TaRef = new double[1];
//		mod_U_TaRef[0] = 0.1    ;
//		Ri_rur =  -477.5169573455006;	
//		15.0 18.2 0.21266443039771887 0.1 60 -477.5169573455006
//		Python Tb_rur=	17.4651588495703
		
		
		
//		double value = solver.convergeTest(dz, ref_ta, UTb, mod_U_TaRef, i, Ri_rur);
//		System.out.println(value);
		
//		TbRurSolver_python solverPython = new TbRurSolver_python();
//		String workingDirectory = System.getProperty("user.dir");
//		solverPython.setWorkingDirectory(workingDirectory);
//		double value2 = solverPython.converge(i+"", dz+"", ref_ta+"", UTb+"", mod_U_TaRef+"", Ri_rur+"");
//		System.out.println(value2);
		
		
//  ('Tb_rur=', 21.8093381484548, 0, '-1.0_21.9_[ 2.63038178]_[ 3.05879268]_[ 0.24555776]', 21.7995777296108)
//('Tb_rur=', 21.8093381484548, 0, '-1.0_21.9_[ 2.63038178]_[ 3.05879268]_[ 0.24555776]', 21.7995777296108)
//		2009-02-05 00:30:00 1
//		('Tb_rur=', 22.4708725309899, 1, '-1.0_22.6_[ 4.73468721]_[ 5.50582682]_[ 0.10165256]', 22.4611121121459)
//		2009-02-05 01:00:00 2
//		('Tb_rur=', 23.2257456165725, 2, '-1.0_23.4_[ 5.78683992]_[ 6.7293439]_[ 0.08715121]', 23.2159851977285)
//		2009-02-05 01:30:00 3
//		('Error calculating Tb_rur', +inf*(-19.612*Thi_tb + 419.6968)/(Thi_tb + 21.4) - 109.818267569608, ' using 21.795 instead')
//		-1.0_21.4_0.1_[ 0.1]_[ 109.81826757]
//		('Tb_rur=', 23.2257456165725, 3, '-1.0_21.4_0.1_[ 0.1]_[ 109.81826757]', 23.2159851977285)
//		2009-02-05 02:00:00 4
//		('Error calculating Tb_rur', +inf*(-19.612*Thi_tb + 404.0072)/(Thi_tb + 20.6) + 36.7517207549013, ' using 21.795 instead')
//		-1.0_20.6_0.1_[ 0.1]_[-36.75172075]
//		('Tb_rur=', 23.2257456165725, 4, '-1.0_20.6_0.1_[ 0.1]_[-36.75172075]', 23.2159851977285)
//
//
		//2009-02-05 00:00:00 0
		//('Ok calculating Tb_rur', 5.4485254465654*(-19.612*Thi_tb + 429.5028)/(Thi_tb + 21.9) - 0.245557760307039)
		//('Tb_rur=', 21.8093381484548, 0, '-1.0_21.9_[ 2.63038178]_[ 3.05879268]_[ 0.24555776]', 21.7995777296108)
//     9.806 * dz * (Thi_tb - ref_ta) * 2.0 / (Thi_tb + ref_ta) / Math.pow((UTb - mod_U_TaRef[i]), 2.0) - Ri_rur;
//		                                         0.183535899
		
		
//		('Ok calculating Tb_rur', 2.12833025256461*(-19.612*Thi_tb + 382.434)/(Thi_tb + 19.5) + 0.100823358330785)
//		('Tb_rur=', 19.6041915418324, 6, '-1.0_19.5_[ 4.20861085]_[ 4.89406829]_[-0.10082336]', 19.5944311229884)

	}

//	public double solve(double dz, double ref_ta, double UTb, double[] mod_U_TaRef, int i, double Ri_rur)
//	{
//		double Tb_rur_itr = 9.806 * dz * (Thi_tb - ref_ta) * 2.0 / (Thi_tb + ref_ta)
//				/ Math.pow((UTb - mod_U_TaRef[i]), 2.0) - Ri_rur;
//	}
	
	

}
