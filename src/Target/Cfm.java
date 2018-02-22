package Target;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

public class Cfm
{
	
	private Common common = new Common();
	private TreeMap<String,String> cfmData = new TreeMap<String,String>();

	public static void main(String[] args)
	{
		String filename = "/home/kerryn/Documents/Work/Toolkit2-Runs/Sunbury3ExtremeB/controlfiles/Sunbury3ExtremeB/Sunbury3ExtremeB.txt";
		Cfm cfm = new Cfm(filename);
		//System.out.println( cfm.getValue("date1"));
	}

	public Cfm(String configFileName)
	{
		super();
		readCfm(configFileName);
		
	}
	
	public void readCfm(String configFileName)
	{
		ArrayList<String> cfmFile = common.readTextFileToArray(configFileName);
		for (String line : cfmFile)
		{
			line = line.trim();
			//System.out.println(line);
			if (line.length() < 1)
			{
				continue;
			}
			String[] splitLine = line.split("#");
			//System.out.println(splitLine.toString());
			if (splitLine.length < 1 || splitLine[0].length() < 1 )
			{
				continue;
			}
			
			String[] subSplitLine = splitLine[0].split("=");
			//System.out.println(subSplitLine[0] + " " + subSplitLine[1]);
			String key = subSplitLine[0].trim();
			String value = subSplitLine[1].trim().replaceAll("'", "").replaceAll("\"", "");
			System.out.println(key + " " + value);
			cfmData.put(key, value);
			
		}
		
	}
	public String getValue(String key)
	{
		return cfmData.get(key);
	}
	
	public Date getDateValue(String key)
	{
		String dateStr = cfmData.get(key);
		//System.out.println(key + " " + dateStr) ;
		//String formatStr = cfmData.get("date_fmt");
		String[] dateStrSplit = dateStr.split(",");
		//  year,month,day,hour
		Integer year = new Integer( dateStrSplit[0]).intValue();
		Integer month = new Integer( dateStrSplit[1]).intValue();
		Integer day = new Integer( dateStrSplit[2]).intValue();
		Integer hour = new Integer( dateStrSplit[3]).intValue();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.DAY_OF_MONTH,day);		 
		cal.set(Calendar.HOUR_OF_DAY,hour);
		
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		Date d = cal.getTime();	
		return d;
	}

	
	
	
	
	
}
