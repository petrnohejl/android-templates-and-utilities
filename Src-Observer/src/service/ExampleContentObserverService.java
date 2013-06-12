package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.utility.ExampleContentObserver;
import com.example.utility.Logcat;


public class ExampleContentObserverService extends Service
{
	private ExampleContentObserver mExampleContentObserver;


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// start content observer
		if(mExampleContentObserver==null)
		{
			Logcat.d("Service.onStartCommand(): start content observer");
			
			mExampleContentObserver = new ExampleContentObserver(new Handler());
			getContentResolver().registerContentObserver(
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					true,
					mExampleContentObserver);
		}
		else
		{
			Logcat.d("Service.onStartCommand(): content observer already running");
		}
		
		return START_STICKY;
	}


	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}


	@Override
	public void onDestroy()
	{
		// stop content observer
		if(mExampleContentObserver!=null)
		{
			getContentResolver().unregisterContentObserver(mExampleContentObserver);
			mExampleContentObserver = null;
		}
	}
}
