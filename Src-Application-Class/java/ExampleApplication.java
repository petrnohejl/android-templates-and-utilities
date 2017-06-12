package com.example;

import android.app.Application;
import android.content.Context;


public class ExampleApplication extends Application
{
	private static ExampleApplication sInstance;


	public ExampleApplication()
	{
		sInstance = this;
	}


	public static Context getContext()
	{
		return sInstance;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		// TODO
	}
}
