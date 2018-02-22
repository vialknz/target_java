package Target;

import java.util.ArrayList;
import java.util.TreeMap;

public class LCData
{
	private double maxH = 0.0;
	private double maxW = 0.0;
	
	private Common common = new Common();
	private ArrayList<ArrayList<Double>> lcDataArrays = new ArrayList<ArrayList<Double>>();
	
	public final static int FID=0;
	public final static int roof=1;
	public final static int road=2;
	public final static int watr=3;
	public final static int conc=4;
	public final static int Veg=5;
	public final static int dry=6;
	public final static int irr=7;
	public final static int H=8;
	public final static int W=9;
	public final static int wall=10;
	
	public static final String VEG_SURF = "Veg";
	public static final String ROAD_SURF = "road";
	public static final String WATR_SURF = "watr";
	public static final String CONC_SURF = "conc";
	public static final String DRY_SURF = "dry";
	public static final String IRR_SURF = "irr";
	public static final String ROOF_SURF = "roof";
	
//	private double vegTotal;
//	private double roadTotal;
//	private double watrTotal;
//	private double concTotal;
//	private double dryTotal;
//	private double irrTotal;
//	private double roofTotal;
	

	


	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String file = "/home/kerryn/Documents/Work/Toolkit2-Runs/Sunbury3ExtremeB/input/Sunbury3ExtremeB/LC/lc.csv";
		LCData lcdata = new LCData(file);
	}
	
	public LCData(String file)
	{
		super();
		readFile(file);
	}
	
	public ArrayList<ArrayList<Double>> getlcData()
	{
		return lcDataArrays;
	}

	public void readFile(String file)
	{
//		FID,roof,road,watr,conc,Veg,dry,irr,H,W
//		0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,2.0,1
		
		int count = 0;
		ArrayList<String> cfmFile = common.readTextFileToArray(file);
		for (String line : cfmFile)
		{
			if (count == 0)
			{
				count ++;
				continue;
			}
			line = line.trim();
			String[] splitLine = line.split(",");
			ArrayList<Double> dataLine = new ArrayList<Double>();
			//System.out.println(line);
			
			double roofValue = new Double(splitLine[roof]).doubleValue();
			//roofTotal += roofValue;
			
			double roadValue = new Double(splitLine[road]).doubleValue();
			//roadTotal += roadValue;
			
			double watrValue = new Double(splitLine[watr]).doubleValue();
			//watrTotal += watrValue;
			
			double concValue = new Double(splitLine[conc]).doubleValue();
			//concTotal += concValue;
			
			double VegValue = new Double(splitLine[Veg]).doubleValue();
			//vegTotal += VegValue;
			
			double dryValue = new Double(splitLine[dry]).doubleValue();
			//dryTotal += dryValue;
			
			double irrValue = new Double(splitLine[irr]).doubleValue();
			//irrTotal += irrValue;
			
			double hValue = new Double(splitLine[H]).doubleValue();
			if (hValue > maxH)
			{
				maxH = hValue;
			}
			double wValue = new Double(splitLine[W]).doubleValue();
			if (wValue > maxW)
			{
				maxW = wValue;
			}
			
			double wall = 0.0;
			
			dataLine.add(new Double(splitLine[FID]).doubleValue());
			dataLine.add(roofValue);
			dataLine.add(roadValue);
			dataLine.add(watrValue);
			dataLine.add(concValue);
			dataLine.add(VegValue);
			dataLine.add(dryValue);
			dataLine.add(irrValue);
			dataLine.add(hValue);
			dataLine.add(wValue);
			dataLine.add(wall);
			//System.out.println(dataLine.toString());
			
			lcDataArrays.add(dataLine);
			
			count ++;
						
			
		}
	}

	public double getMaxH()
	{
		return this.maxH;
	}

	public double getMaxW()
	{
		return this.maxW;
	}

//	public double getVegTotal()
//	{
//		return vegTotal;
//	}
//
//	public void setVegTotal(double vegTotal)
//	{
//		this.vegTotal = vegTotal;
//	}
//
//	public double getRoadTotal()
//	{
//		return roadTotal;
//	}
//
//	public void setRoadTotal(double roadTotal)
//	{
//		this.roadTotal = roadTotal;
//	}
//
//	public double getWatrTotal()
//	{
//		return watrTotal;
//	}
//
//	public void setWatrTotal(double watrTotal)
//	{
//		this.watrTotal = watrTotal;
//	}
//
//	public double getConcTotal()
//	{
//		return concTotal;
//	}
//
//	public void setConcTotal(double concTotal)
//	{
//		this.concTotal = concTotal;
//	}
//
//	public double getDryTotal()
//	{
//		return dryTotal;
//	}
//
//	public void setDryTotal(double dryTotal)
//	{
//		this.dryTotal = dryTotal;
//	}
//
//	public double getIrrTotal()
//	{
//		return irrTotal;
//	}
//
//	public void setRoofTotal(double roofTotal)
//	{
//		this.roofTotal = roofTotal;
//	}
//	public double getRoofTotal()
//	{
//		return roofTotal;
//	}
//
//	public void setIrrTotal(double irrTotal)
//	{
//		this.irrTotal = irrTotal;
//	}
	

}
