package com.justin.evernote.dataSources;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 */
@Component
public class DateDataSource implements IDataSource
{
	public static final String KEY = "DATE";
	
	/**
	 */
	public String getDataSourceKey()
	{
		return KEY;
	}

	@Override
	public Map<String, Object> getDataMap() throws Exception
	{
		SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM");
		SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEEE");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setFirstDayOfWeek(Calendar.SUNDAY);
		int weekNum = gc.get(Calendar.WEEK_OF_YEAR);
 
		Map<String, Object> dateStrings = new HashMap<String, Object>();
		dateStrings.put("full_date", fullDateFormatter.format(gc.getTime()));
		dateStrings.put("month", monthFormatter.format(gc.getTime()));
		dateStrings.put("week_num", String.format("%02d", weekNum));
		dateStrings.put("day", dayFormatter.format(gc.getTime()));
		dateStrings.put("time", timeFormatter.format(gc.getTime()));
		
		return dateStrings;
	}
}
