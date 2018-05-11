package Target;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TbRurSolver_python
{
	Common common = new Common();
	private String workingDirectory;
	public static final double ERROR_RETURN = -9999.;

	public static void main(String[] args)
	{
		String i="1";
		String dz="-1.0";
		String ref_ta="21.9";
		String UTb="2.63038178";
		String mod_U_TaRef="3.05879268";
		String Ri_rur="0.24555776";
		
		TbRurSolver_python solver = new TbRurSolver_python();
		solver.setWorkingDirectory("/home/kerryn/git/Target_Java/bin");
		double returnValue = solver.converge(i, dz, ref_ta, UTb, mod_U_TaRef, Ri_rur);
		System.out.println(returnValue);
	}
	
//	public TbRurSolver_python(String workingDirectory)
//	{
//		this.workingDirectory = workingDirectory;				
//	}
		
	public double converge(String i, String dz, String ref_ta, String UTb, String mod_U_TaRef, String Ri_rur)
	{
		double ret = ERROR_RETURN;
		try
		{
			String solverLocation = this.workingDirectory + "/../" + "TbRurSolver.py";
			System.out.println("solverLocation="+solverLocation);
			
			solverLocation = findTbRurPython(this.workingDirectory);
			System.out.println("Final solverLocation="+solverLocation);
			
			ProcessBuilder pb = new ProcessBuilder("/usr/bin/python", solverLocation,i,dz,ref_ta,UTb,mod_U_TaRef,Ri_rur);
//			ProcessBuilder pb = new ProcessBuilder("python","/home/kerryn/git/Target_Java/TbRurSolver.py",
//					"1","-1.0","21.9","2.63038178","3.05879268","0.24555776");
			Process p = pb.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String returnValue = in.readLine();
//			System.out.println(returnValue);
			ret = new Double(returnValue).doubleValue();
			//System.out.println("value is : " + ret);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return ret;
	}
	
	public String findTbRurPython(String workingDirectory)
	{
		String returnValue = "Can't find solver file";
		
		String solverLocation = this.workingDirectory + "/../" + "TbRurSolver.py";
		boolean exists = common.verifyFileExists(solverLocation);
		if (exists)
		{
			return solverLocation;
		}
		solverLocation = this.workingDirectory + "/" + "TbRurSolver.py";
		exists = common.verifyFileExists(solverLocation);
		if (exists)
		{
			return solverLocation;
		}
		
		
		
		return returnValue;
	}
	
//	public double converge2(String i, String dz, String ref_ta, String UTb, String mod_U_TaRef, String Ri_rur)
//	{
//		double ret = ERROR_RETURN;
//		
//		try
//		{
//			String parameters = i+" "+dz+" "+ref_ta+" "+UTb+" "+mod_U_TaRef+" "+Ri_rur;
//			String solverLocation = this.workingDirectory + "/" + "TbRurSolver.py" ;
//			String totalCommand = "python  " + solverLocation + " " + parameters;
//			System.out.println(totalCommand);
//			Process p = Runtime.getRuntime().exec(totalCommand);
//		    BufferedReader in = new BufferedReader(new InputStreamReader(
//		            p.getInputStream()));
//
//		    String line;  
//		        while ((line = in.readLine()) != null) 
//		        {  
//		            //System.out.println(line);  
//		            ret = new Double(line).doubleValue();
//		        }  
//		        in.close();
//		        p.waitFor();
//		}
//		catch (Exception e)
//		{
//			System.out.println(e);
//		}
//		
//		
//		
////		try
////		{
////			String solverLocation = this.workingDirectory + "/" + "TbRurSolver.py";
////			System.out.println("solverLocation="+solverLocation);
////			ProcessBuilder pb = new ProcessBuilder("/usr/bin/python", solverLocation,i,dz,ref_ta,UTb,mod_U_TaRef,Ri_rur);
//////			ProcessBuilder pb = new ProcessBuilder("python","/home/kerryn/git/Target_Java/TbRurSolver.py",
//////					"1","-1.0","21.9","2.63038178","3.05879268","0.24555776");
////			Process p = pb.start();
////
////			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
////			String returnValue = in.readLine();
////			System.out.println(returnValue);
////			ret = new Double(returnValue).doubleValue();
////			//System.out.println("value is : " + ret);
////		}
////		catch (Exception e)
////		{
////			System.out.println(e);
////		}
//		return ret;
//	}

	public String getWorkingDirectory()
	{
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}

}
