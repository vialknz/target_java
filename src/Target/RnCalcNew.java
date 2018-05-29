package Target;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class RnCalcNew
{
	public static final String RN_KEY = "Rn";
	public static final String RNPREV_KEY = "Rnprev";
	public static final String RNSTAR_KEY = "Rnstar";
	
//	"""
//
//	calculates net radiation for current, previous, and next time step
//
//	see section 3.1 tech description for more details
//
//	inputs:
//		cs		= constants dictionary
//		cfM		= main control file
//		met		= met forcing data frame
//		surf	= current surface type
//		Dats	= dates dictionary
//		mod_ts	= surface temperature data frame
//		i		= current index
//
//	Outputs:
//		Rn	   = net radiation
//		Rnprev = net radiation (t-1)
//		Rnstar = 0.5*(Rn(t-1) + Rn(t+1))
//
//
//
//	"""

	public TreeMap<String,Double> rn_calc_new(Cfm cfm, ArrayList<ArrayList<Object>> met, String surf, TreeMap<String,Date> Dats, 
			ArrayList<Double> mod_ts, int i, double svf)
	{
		TreeMap<String,Double> returnValues = new TreeMap<String,Double> ();
		
		Double albedo = Constants.cs_alb.get(surf);		//# surface albedo
		Double emiss  = Constants.cs_emis.get(surf);	//# surface emissivity
		
		double cs_sb = Constants.cs_sb;
		
		double Rn	   = 0.;
		double Rnprev = 0.;
		double Rnnext = 0.;
		double Rnstar = 0.;
		
//		String tmstp = cfm.getValue("timestep");                            // # time step (minutes)
//		int tmstpInt = new Integer( tmstp.replaceAll("S", "").replaceAll("'", "") ).intValue();
		Date dte = Dats.get("dte");
		Date date1A = Dats.get("date1A");
				
		if (dte.getTime() == date1A.getTime())
		{
			//# intial values set to 0.
			Rn	   = 0.;
			Rnprev = 0.;
			Rnnext = 0.;
			Rnstar = 0.;
		}
		else
		{   //  mod_ts= new double[met_data_all.size()][numberOfVf][numberOfSurfaces];
//			TreeMap<String,Double> modTsMinus3 = mod_ts.get(i-3);
//			TreeMap<String,Double> modTsMinus2 = mod_ts.get(i-2);
//			TreeMap<String,Double> modTsMinus1 = mod_ts.get(i-1);			
			
//			double Ta_srfp = (double)modTsMinus3.get(surf);			//# "previous" modelled T_surf (3 timesteps back)
//			double Ta_srf	= (double)modTsMinus2.get(surf);			//# "current" modelled T_Surf (2 time steps back)
//			double Ta_srfn = (double)modTsMinus1.get(surf);			//# "next" modelled T_Surf (1 time steps back)
			
			double Ta_srfp = mod_ts.get(2);			//# "previous" modelled T_surf (3 timesteps back)
			double Ta_srf	= mod_ts.get(1);			//# "current" modelled T_Surf (2 time steps back)
			double Ta_srfn = mod_ts.get(0);			//# "next" modelled T_Surf (1 time steps back)
			
			ArrayList<Object> met0 = met.get(i);			
			double metKd0 = (double)met0.get(MetData.Kd);
			double metLd0 = (double)met0.get(MetData.Ld);
			
			ArrayList<Object> metPlus1 = met.get(i+1);			
			double metKdPlus1 = (double)metPlus1.get(MetData.Kd);
			double metLdPlus1 = (double)metPlus1.get(MetData.Ld);
			
			ArrayList<Object> metMinus1 = met.get(i-1);			
			double metKdMinus1 = (double)metMinus1.get(MetData.Kd);
			double metLdMinus1 = (double)metMinus1.get(MetData.Ld);
			
			if (!surf.equals("roof"))
			{
			
				Rn	   = ((metKd0*(1.0-albedo))*svf) + ((emiss*(metLd0 - (cs_sb*Math.pow((Ta_srf+273.15),4))))*svf);	 //# modified version of eq 11 Loridan et al. (2011)
				Rnprev = ((metKdMinus1*(1.0-albedo))*svf) + ((emiss*(metLdMinus1 - (cs_sb*Math.pow((Ta_srfp+273.15),4))))*svf);
				Rnnext = ((metKdPlus1*(1.0-albedo))*svf) + ((emiss*(metLdPlus1 - (cs_sb*Math.pow((Ta_srfn+273.15),4))))*svf);
				Rnstar = 0.5*(Rnnext - Rnprev);
				
			}
				
			if (surf.equals("roof"))
			{
				
				Rn	   = metKd0*(1.0-albedo) + (emiss*(metLd0 - (cs_sb*Math.pow((Ta_srf+273.15),4))))	; //# modified version of eq 11 Loridan et al. (2011)
				Rnprev = metKdMinus1*(1.0-albedo) + (emiss*(metLdMinus1 - (cs_sb*Math.pow((Ta_srfp+273.15),4))));
				Rnnext = metKdPlus1*(1.0-albedo) + (emiss*(metLdPlus1 - (cs_sb*Math.pow((Ta_srfn+273.15),4))));
				Rnstar = 0.5*(Rnnext - Rnprev);
			}
	            
//			# if (surf == 'wall'):
//
//				# Ta_srfp = mod_ts[surf][i-3]			# "previous" modelled T_surf (3 timesteps back)
//				# Ta_srf	= mod_ts[surf][i-2]			# "current" modelled T_Surf (2 time steps back)
//				# Ta_srfn = mod_ts[surf][i-1]			# "next" modelled T_Surf (1 time steps back)
//
//				# Rn	   = (met['Kd'][i]  * (1.0 - svf) * (1.0-albedo))	 + ((emiss*(met['Ld'][i]	 - (cs.cs['sb']*(Ta_srf+273.15)**4)))*(1.0-svf))	# / 2. # modified version of eq 11 Loridan et al. (2011)
//				# Rnprev = (met['Kd'][i-1]* (1.0 - svf) * (1.0-albedo))	 + ((emiss*(met['Ld'][i-1]   - (cs.cs['sb']*(Ta_srfp+273.15)**4)))*(1.0-svf)) #/ 2.
//				# Rnnext = (met['Kd'][i+1]* (1.0 - svf) * (1.0-albedo))	 + ((emiss*(met['Ld'][i+1]   - (cs.cs['sb']*(Ta_srfn+273.15)**4)))*(1.0-svf)) #/ 2.
//				# Rnstar = 0.5*(Rnnext - Rnprev)	
			
	}

			
		returnValues.put(RN_KEY, Rn);
		returnValues.put(RNPREV_KEY, Rnprev);
		returnValues.put(RNSTAR_KEY, Rnstar);
		
		//return {'Rn':Rn,'Rnprev':Rnprev, 'Rnstar':Rnstar}
		return returnValues;
}
}
