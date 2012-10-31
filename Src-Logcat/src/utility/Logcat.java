package com.example.utility;


public class Logcat
{
	public static final boolean LOG_ENABLED = true;
	
	
	public static void d(String tag, String msg)
	{
		if(LOG_ENABLED) android.util.Log.d(tag, msg);
	}
	
	public static void e(String tag, String msg)
	{
		if(LOG_ENABLED) android.util.Log.e(tag, msg);
	}
	
	public static void e(String tag, String msg, Throwable tr)
	{
		if(LOG_ENABLED) android.util.Log.e(tag, msg, tr);
	}
	
	public static void i(String tag, String msg)
	{
		if(LOG_ENABLED) android.util.Log.i(tag, msg);
	}
	
	public static void v(String tag, String msg)
	{
		if(LOG_ENABLED) android.util.Log.v(tag, msg);
	}
	
	public static void w(String tag, String msg)
	{
		if(LOG_ENABLED) android.util.Log.w(tag, msg);
	}
}
