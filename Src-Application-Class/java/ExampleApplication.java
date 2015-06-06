package com.example;

import android.app.Application;
import android.content.Context;


public class ExampleApplication extends Application
{
	private static ExampleApplication sInstance;


	public static Context getContext()
	{
		return sInstance;
	}


	public ExampleApplication()
	{
		sInstance = this;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		
		// force AsyncTask to be initialized in the main thread due to the bug:
		// http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
		try
		{
			Class.forName("android.os.AsyncTask");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
