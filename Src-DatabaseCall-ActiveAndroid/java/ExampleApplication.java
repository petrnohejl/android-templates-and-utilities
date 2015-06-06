package com.example;

import android.content.Context;

import com.activeandroid.ActiveAndroid;


public class ExampleApplication extends com.activeandroid.app.Application
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
		
		// logs in ActiveAndroid
		ActiveAndroid.setLoggingEnabled(ExampleConfig.LOGS);
	}
}
