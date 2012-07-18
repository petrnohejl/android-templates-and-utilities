package com.example.activities;

import android.app.Application;
import android.content.Context;

public class ExampleApplication extends Application
{
	private static ExampleApplication instance;

	public ExampleApplication()
	{
		instance = this;
	}

	public static Context getContext()
	{
		return instance;
	}
}
