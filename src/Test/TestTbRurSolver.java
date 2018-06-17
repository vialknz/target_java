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
