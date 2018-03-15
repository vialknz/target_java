package Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.DoubleStream;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayInt;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

public class NetCdfOutput
{
	NetcdfFileWriter writer = null;
	String location = "/tmp/testWrite.nc";

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String loc = "/tmp/testWrite.nc";
		int y = 64; //lon
		int x = 128; //lat
		NetCdfOutput netCdfOutput = new NetCdfOutput(loc);
		//netCdfOutput.process();
		//netCdfOutput.createNetcdf(x, y);
		
		try
		{
			netCdfOutput.testWriteRecordOneAtaTime3(loc);
		}
		catch (IOException | InvalidRangeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public NetCdfOutput(String location)
	{
		super();
		this.location = location;
	}
	
	public void createNetcdf(int x, int y)
	{
		try
		{
			writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension latDim = writer.addDimension(null, "lat", y);
		Dimension lonDim = writer.addDimension(null, "lon", x);

		// add Variable double temperature(lat,lon)
		List<Dimension> dims = new ArrayList<Dimension>();
		dims.add(latDim);
		dims.add(lonDim);
		Variable t = writer.addVariable(null, "temperature", DataType.DOUBLE, dims);
		t.addAttribute(new Attribute("units", "C")); // add a 1D attribute of length 3
		Array data = Array.factory(int.class, new int[]{ 3 }, new int[]{ 1, 2, 3 });
		t.addAttribute(new Attribute("scale", data));

		// add a string-valued variable: char svar(80)
		Dimension svar_len = writer.addDimension(null, "svar_len", 80);
		writer.addVariable(null, "svar", DataType.CHAR, "svar_len");

		// add a 2D string-valued variable: char names(names, 80)
		Dimension names = writer.addDimension(null, "names", 3);
		writer.addVariable(null, "names", DataType.CHAR, "names svar_len");

		// add a scalar variable
		writer.addVariable(null, "scalar", DataType.DOUBLE, new ArrayList<Dimension>());

		// add global attributes
		writer.addGroupAttribute(null, new Attribute("yo", "face"));
		writer.addGroupAttribute(null, new Attribute("versionD", 1.2));
		writer.addGroupAttribute(null, new Attribute("versionF", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("versionI", 1));
		writer.addGroupAttribute(null, new Attribute("versionS", (short) 2));
		writer.addGroupAttribute(null, new Attribute("versionB", (byte) 3));

		// create the file
		try
		{
			writer.create();
		}
		catch (IOException e)
		{
			System.err.printf("ERROR creating file %s%n%s", location, e.getMessage());
		}
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outputNetcdf(String filename, int latX, int lonY, 
			ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_all_timesteps,
			ArrayList<ArrayList<TreeMap<Integer,Double>>> mod_rslts_tmrt_utci_all_timesteps) //throws IOException, InvalidRangeException
	{
		// String filename = TestLocal.temporaryDataDir + "testWriteRecord2.nc";
		NetcdfFileWriter writer = null;
		try
		{
			writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filename);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// define dimensions, including unlimited
		Dimension latDim = writer.addDimension(null, "lat", latX);
		Dimension lonDim = writer.addDimension(null, "lon", lonY);
		Dimension timeDim = writer.addUnlimitedDimension("time");

		// define Variables
		Variable lat = writer.addVariable(null, "lat", DataType.FLOAT, "lat");
		lat.addAttribute(new Attribute("units", "degrees_north"));
		Variable lon = writer.addVariable(null, "lon", DataType.FLOAT, "lon");
		lon.addAttribute(new Attribute("units", "degrees_east"));

		Variable t = writer.addVariable(null, "T", DataType.DOUBLE, "time lat lon");
		t.addAttribute(new Attribute("long_name", "air temperature"));
		t.addAttribute(new Attribute("units", "degC"));
		Variable time = writer.addVariable(null, "time", DataType.INT, "time");
		time.addAttribute(new Attribute("units", "hours since 1990-01-01"));
		
		Variable tm = writer.addVariable(null, "Tmrt", DataType.DOUBLE, "time lat lon");
		tm.addAttribute(new Attribute("long_name", "mean radiant temperature"));
		tm.addAttribute(new Attribute("units", "degC"));
		
		Variable u = writer.addVariable(null, "UTCI", DataType.DOUBLE, "time lat lon");
		u.addAttribute(new Attribute("long_name", "UTCI temperature"));
		u.addAttribute(new Attribute("units", "degC"));

		// create the file
		try
		{
			writer.create();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		-34.81425315    138.6066486     0.12185321017843
//		-34.81429613    138.6088335     0.410331603879865
//		-34.81433907    138.6110184     0.32056195573247
//		.00004294		.0021849
		
		float[] latArray = new float[latX];
		for (int i=0;i<latX;i++)
		{
			float value = (float) (-34.81425315 + (.00004294 * i));
			latArray[i]=value;
		}
		float[] lonArray = new float[lonY];
		for (int i=0;i<lonY;i++)
		{
			float value = (float) (138.6066486 + (.0021849 * i));
			lonArray[i]=value;
		}
	

		// write out the non-record variables
		try
		{
			writer.write(lat, Array.factory(latArray));
			writer.write(lon, Array.factory(lonArray));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidRangeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//// heres where we write the record variables
		// different ways to create the data arrays.
		// Note the outer dimension has shape 1, since we will write one record
		//// at a time
//		ArrayInt rhData = new ArrayInt.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 tempData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 utciData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 tmrtData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		Array timeData = Array.factory(DataType.INT, new int[]
		{ 1 });
//		Index ima = rhData.getIndex();

		int[] origin = new int[]
		{ 0, 0, 0 };
		int[] time_origin = new int[]
		{ 0 };
		
		// loop over each record
		for (int timeIdx = 0; timeIdx < mod_rslts_all_timesteps.size(); timeIdx++)
		{
			ArrayList<TreeMap<Integer,Double>> mod_rslts = mod_rslts_all_timesteps.get(timeIdx);
			ArrayList<TreeMap<Integer,Double>> mod_rslts_tmrt_utci = mod_rslts_tmrt_utci_all_timesteps.get(timeIdx);
			
			// make up some data for this record, using different ways to fill
			// the data arrays.
			timeData.setInt(timeData.getIndex(), timeIdx * 12);

			int count = 0;
			for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++)
			{
				for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++)
				{
					TreeMap<Integer,Double> mod_rslts_grid = mod_rslts.get(count);
					TreeMap<Integer,Double> mod_rslts_tmrt_utci_grid = mod_rslts_tmrt_utci.get(count);
					
					double ucanValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Ucan_INDEX);
					double tsurfHorzValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Tsurf_horz_INDEX);
					double tsurfCanValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Tsurf_can_INDEX);
					double tsurfWallValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Tsurf_wall_INDEX);
					double tacValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Tac_INDEX);
					double dteValue = mod_rslts_grid.get(TargetModule.FOR_TAB_dte_INDEX);
					double httcUrbNewValue = mod_rslts_grid.get(TargetModule.FOR_TAB_httc_urb_new_INDEX);
					double httcCanValue = mod_rslts_grid.get(TargetModule.FOR_TAB_httc_can_INDEX);
					double tbRurValue = mod_rslts_grid.get(TargetModule.FOR_TAB_Tb_rur_INDEX);
					double modUTaRefValue = mod_rslts_grid.get(TargetModule.FOR_TAB_mod_U_TaRef_INDEX);
					double utbValue = mod_rslts_grid.get(TargetModule.FOR_TAB_UTb_INDEX);
					
					double tmrtValue = mod_rslts_tmrt_utci_grid.get(TargetModule.FOR_TAB_UTCI_tmrt_INDEX);
					double utciValue = mod_rslts_tmrt_utci_grid.get(TargetModule.FOR_TAB_UTCI_utci_INDEX);
					if (tacValue== -999.0)
					{
						tacValue = Double.NaN;
					}
					if (tmrtValue== -999.0)
					{
						tmrtValue = Double.NaN;
					}
					if (utciValue== -999.0)
					{
						utciValue = Double.NaN;
					}
										
//					double calcuationData = timeIdx * latIdx * lonIdx / 3.14159 + 0.1*lonIdx*latIdx;
					//int calcuationData2 = timeIdx * latIdx * lonIdx;
					//System.out.println(calcuationData + " " + calcuationData2);
//					rhData.setInt(ima.set(0, latIdx, lonIdx), calcuationData2);
					
					
					tempData.set(0, latIdx, lonIdx, tacValue);
					tmrtData.set(0, latIdx, lonIdx, tmrtValue);
					utciData.set(0, latIdx, lonIdx, utciValue);
					count ++;
				}
			}
			// write the data out for one record
			// set the origin here
			time_origin[0] = timeIdx;
			origin[0] = timeIdx;
			try
			{
//				writer.write(rh, origin, rhData);
				writer.write(t, origin, tempData);
				writer.write(tm, origin, tmrtData);
				writer.write(u, origin, utciData);
				writer.write(time, time_origin, timeData);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InvalidRangeException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // loop over record

		// all done
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testWriteRecordOneAtaTime3(String filename) throws IOException, InvalidRangeException
	{
		// String filename = TestLocal.temporaryDataDir + "testWriteRecord2.nc";
		NetcdfFileWriter writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filename);

		// define dimensions, including unlimited
		Dimension latDim = writer.addDimension(null, "lat", 3);
		Dimension lonDim = writer.addDimension(null, "lon", 4);
		Dimension timeDim = writer.addUnlimitedDimension("time");

		// define Variables
		Variable lat = writer.addVariable(null, "lat", DataType.FLOAT, "lat");
		lat.addAttribute(new Attribute("units", "degrees_north"));
		Variable lon = writer.addVariable(null, "lon", DataType.FLOAT, "lon");
		lon.addAttribute(new Attribute("units", "degrees_east"));
		Variable rh = writer.addVariable(null, "rh", DataType.INT, "time lat lon");
		rh.addAttribute(new Attribute("long_name", "relative humidity"));
		rh.addAttribute(new Attribute("units", "percent"));
		Variable t = writer.addVariable(null, "T", DataType.DOUBLE, "time lat lon");
		t.addAttribute(new Attribute("long_name", "surface temperature"));
		t.addAttribute(new Attribute("units", "degC"));
		Variable time = writer.addVariable(null, "time", DataType.INT, "time");
		time.addAttribute(new Attribute("units", "hours since 1990-01-01"));

		// create the file
		writer.create();

		// write out the non-record variables
		writer.write(lat, Array.factory(new float[]
		{ -37, -38, -39 }));
		writer.write(lon, Array.factory(new float[]
		{ 144, 145, 146, 147 }));
		
		//// heres where we write the record variables
		// different ways to create the data arrays.
		// Note the outer dimension has shape 1, since we will write one record
		//// at a time
		ArrayInt rhData = new ArrayInt.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 tempData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		Array timeData = Array.factory(DataType.INT, new int[]
		{ 1 });
		Index ima = rhData.getIndex();

		int[] origin = new int[]
		{ 0, 0, 0 };
		int[] time_origin = new int[]
		{ 0 };
		
		// loop over each record
		for (int timeIdx = 0; timeIdx < 20; timeIdx++)
		{
			// make up some data for this record, using different ways to fill
			// the data arrays.
			timeData.setInt(timeData.getIndex(), timeIdx * 12);

			for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++)
			{
				for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++)
				{
					double calcuationData = timeIdx * latIdx * lonIdx / 3.14159 + 0.1*lonIdx*latIdx;
					int calcuationData2 = timeIdx * latIdx * lonIdx;
					System.out.println(calcuationData + " " + calcuationData2);
					rhData.setInt(ima.set(0, latIdx, lonIdx), calcuationData2);
					tempData.set(0, latIdx, lonIdx, calcuationData);
				}
			}
			// write the data out for one record
			// set the origin here
			time_origin[0] = timeIdx;
			origin[0] = timeIdx;
			writer.write(rh, origin, rhData);
			writer.write(t, origin, tempData);
			writer.write(time, time_origin, timeData);
		} // loop over record

		// all done
		writer.close();
		
	}
	
	public void testWriteRecordOneAtaTime2(String filename) throws IOException, InvalidRangeException
	{
		// String filename = TestLocal.temporaryDataDir + "testWriteRecord2.nc";
		NetcdfFileWriter writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filename);

		// define dimensions, including unlimited
		Dimension latDim = writer.addDimension(null, "lat", 3);
		Dimension lonDim = writer.addDimension(null, "lon", 4);
		Dimension timeDim = writer.addUnlimitedDimension("time");

		// define Variables
		Variable lat = writer.addVariable(null, "lat", DataType.FLOAT, "lat");
		lat.addAttribute(new Attribute("units", "degrees_north"));
		Variable lon = writer.addVariable(null, "lon", DataType.FLOAT, "lon");
		lon.addAttribute(new Attribute("units", "degrees_east"));
		Variable rh = writer.addVariable(null, "rh", DataType.INT, "time lat lon");
		rh.addAttribute(new Attribute("long_name", "relative humidity"));
		rh.addAttribute(new Attribute("units", "percent"));
		Variable t = writer.addVariable(null, "T", DataType.DOUBLE, "time lat lon");
		t.addAttribute(new Attribute("long_name", "surface temperature"));
		t.addAttribute(new Attribute("units", "degC"));
		Variable time = writer.addVariable(null, "time", DataType.INT, "time");
		time.addAttribute(new Attribute("units", "hours since 1990-01-01"));

		// create the file
		writer.create();

		// write out the non-record variables
		writer.write(lat, Array.factory(new float[]
		{ -37, -38, -39 }));
		writer.write(lon, Array.factory(new float[]
		{ 144, 145, 146, 147 }));
		
		//// heres where we write the record variables
		// different ways to create the data arrays.
		// Note the outer dimension has shape 1, since we will write one record
		//// at a time
		ArrayInt rhData = new ArrayInt.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 tempData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		Array timeData = Array.factory(DataType.INT, new int[]
		{ 1 });
		Index ima = rhData.getIndex();

		int[] origin = new int[]
		{ 0, 0, 0 };
		int[] time_origin = new int[]
		{ 0 };
		
//		try
//		{
//			writer.close();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		writer = NetcdfFileWriter.openExisting(location);
		

		// loop over each record
		for (int timeIdx = 0; timeIdx < 20; timeIdx++)
		{
			// make up some data for this record, using different ways to fill
			// the data arrays.
			timeData.setInt(timeData.getIndex(), timeIdx * 12);

			for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++)
			{
				for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++)
				{
					double calcuationData = timeIdx * latIdx * lonIdx / 3.14159 + 0.1*lonIdx*latIdx;
					int calcuationData2 = timeIdx * latIdx * lonIdx;
					System.out.println(calcuationData + " " + calcuationData2);
					rhData.setInt(ima.set(0, latIdx, lonIdx), calcuationData2);
					tempData.set(0, latIdx, lonIdx, calcuationData);
				}
			}
			// write the data out for one record
			// set the origin here
			time_origin[0] = timeIdx;
			origin[0] = timeIdx;
			writer.write(rh, origin, rhData);
			writer.write(t, origin, tempData);
			writer.write(time, time_origin, timeData);
		} // loop over record

		// all done
		writer.close();
		
//		writer = NetcdfFileWriter.openExisting(location);
//		// loop over each record
//		for (int timeIdx = 0; timeIdx < 10; timeIdx++)
//		{
//			// make up some data for this record, using different ways to fill
//			// the data arrays.
//			timeData.setInt(timeData.getIndex(), timeIdx * 12);
//
//			for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++)
//			{
//				for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++)
//				{
//					double calcuationData = timeIdx * latIdx * lonIdx / 3.14159 + 0.1*lonIdx*latIdx;
//					int calcuationData2 = timeIdx * latIdx * lonIdx;
//					System.out.println(calcuationData + " " + calcuationData2);
//					rhData.setInt(ima.set(0, latIdx, lonIdx), calcuationData2);
//					tempData.set(0, latIdx, lonIdx, calcuationData);
//				}
//			}
//			// write the data out for one record
//			// set the origin here
//			time_origin[0] = timeIdx;
//			origin[0] = timeIdx;
//			writer.write(rh, origin, rhData);
//			writer.write(t, origin, tempData);
//			writer.write(time, time_origin, timeData);
//		} // loop over record
	}
	
	public void testWriteRecordOneAtaTime(String filename) throws IOException, InvalidRangeException
	{
		// String filename = TestLocal.temporaryDataDir + "testWriteRecord2.nc";
		NetcdfFileWriter writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filename);

		// define dimensions, including unlimited
		Dimension latDim = writer.addDimension(null, "lat", 3);
		Dimension lonDim = writer.addDimension(null, "lon", 4);
		Dimension timeDim = writer.addUnlimitedDimension("time");

		// define Variables
		Variable lat = writer.addVariable(null, "lat", DataType.FLOAT, "lat");
		lat.addAttribute(new Attribute("units", "degrees_north"));
		Variable lon = writer.addVariable(null, "lon", DataType.FLOAT, "lon");
		lon.addAttribute(new Attribute("units", "degrees_east"));
		Variable rh = writer.addVariable(null, "rh", DataType.INT, "time lat lon");
		rh.addAttribute(new Attribute("long_name", "relative humidity"));
		rh.addAttribute(new Attribute("units", "percent"));
		Variable t = writer.addVariable(null, "T", DataType.DOUBLE, "time lat lon");
		t.addAttribute(new Attribute("long_name", "surface temperature"));
		t.addAttribute(new Attribute("units", "degC"));
		Variable time = writer.addVariable(null, "time", DataType.INT, "time");
		time.addAttribute(new Attribute("units", "hours since 1990-01-01"));

		// create the file
		writer.create();

		// write out the non-record variables
		writer.write(lat, Array.factory(new float[]
		{ 41, 40, 39 }));
		writer.write(lon, Array.factory(new float[]
		{ -109, -107, -105, -103 }));

		//// heres where we write the record variables
		// different ways to create the data arrays.
		// Note the outer dimension has shape 1, since we will write one record
		//// at a time
		ArrayInt rhData = new ArrayInt.D3(1, latDim.getLength(), lonDim.getLength());
		ArrayDouble.D3 tempData = new ArrayDouble.D3(1, latDim.getLength(), lonDim.getLength());
		Array timeData = Array.factory(DataType.INT, new int[]
		{ 1 });
		Index ima = rhData.getIndex();

		int[] origin = new int[]
		{ 0, 0, 0 };
		int[] time_origin = new int[]
		{ 0 };

		// loop over each record
		for (int timeIdx = 0; timeIdx < 10; timeIdx++)
		{
			// make up some data for this record, using different ways to fill
			// the data arrays.
			timeData.setInt(timeData.getIndex(), timeIdx * 12);

			for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++)
			{
				for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++)
				{
					double calcuationData = timeIdx * latIdx * lonIdx / 3.14159 + 0.1*lonIdx*latIdx;
					int calcuationData2 = timeIdx * latIdx * lonIdx;
					System.out.println(calcuationData + " " + calcuationData2);
					rhData.setInt(ima.set(0, latIdx, lonIdx), calcuationData2);
					tempData.set(0, latIdx, lonIdx, calcuationData);
				}
			}
			// write the data out for one record
			// set the origin here
			time_origin[0] = timeIdx;
			origin[0] = timeIdx;
			writer.write(rh, origin, rhData);
			writer.write(t, origin, tempData);
			writer.write(time, time_origin, timeData);
		} // loop over record

		// all done
		writer.close();
	}
	
//	public void addToNetcdf()
//	{
//		NetcdfFileWriter writer = NetcdfFileWriter.openExisting(location);
//	}


	public void process()
	{
		
		
		try
		{
			writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension latDim = writer.addDimension(null, "lat", 64);
		Dimension lonDim = writer.addDimension(null, "lon", 128);

		// add Variable double temperature(lat,lon)
		List<Dimension> dims = new ArrayList<Dimension>();
		dims.add(latDim);
		dims.add(lonDim);
		Variable t = writer.addVariable(null, "temperature", DataType.DOUBLE, dims);
		t.addAttribute(new Attribute("units", "K")); // add a 1D attribute of
														// length 3
		Array data = Array.factory(int.class, new int[]
		{ 3 }, new int[]
		{ 1, 2, 3 });
		t.addAttribute(new Attribute("scale", data));

		// add a string-valued variable: char svar(80)
		Dimension svar_len = writer.addDimension(null, "svar_len", 80);
		writer.addVariable(null, "svar", DataType.CHAR, "svar_len");

		// add a 2D string-valued variable: char names(names, 80)
		Dimension names = writer.addDimension(null, "names", 3);
		writer.addVariable(null, "names", DataType.CHAR, "names svar_len");

		// add a scalar variable
		writer.addVariable(null, "scalar", DataType.DOUBLE, new ArrayList<Dimension>());

		// add global attributes
		writer.addGroupAttribute(null, new Attribute("yo", "face"));
		writer.addGroupAttribute(null, new Attribute("versionD", 1.2));
		writer.addGroupAttribute(null, new Attribute("versionF", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("versionI", 1));
		writer.addGroupAttribute(null, new Attribute("versionS", (short) 2));
		writer.addGroupAttribute(null, new Attribute("versionB", (byte) 3));

		// create the file
		try
		{
			writer.create();
		}
		catch (IOException e)
		{
			System.err.printf("ERROR creating file %s%n%s", location, e.getMessage());
		}
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
