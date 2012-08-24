package com.example.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


// date format: http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
public class DateConvertor
{
	public static String dateToString(Date date, String format)
	{
		String str = null;
		if(date!=null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			str = dateFormat.format(date);
		}
		return str;
	}
	
	
	public static Date stringToDate(String str, String format)
	{
		Date date = null;
		if(str!=null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			try { date = dateFormat.parse(str); }
			catch(ParseException e) { e.printStackTrace(); }
		}
		return date;
	}
}
