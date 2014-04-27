package com.example.service;

import com.example.utility.Logcat;

import android.app.IntentService;
import android.content.Intent;


public class ExampleIntentService extends IntentService
{
	public ExampleIntentService()
	{
		super("ExampleIntentService");
		setIntentRedelivery(false); // TODO: START_NOT_STICKY if false, START_REDELIVER_INTENT if true
		
		Logcat.d("IntentService.IntentService()");
	}
	
	
	@Override
	public void onCreate()
	{
		Logcat.d("IntentService.onCreate()");
		
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Logcat.d("IntentService.onStartCommand()");

		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	protected void onHandleIntent(Intent intent)
	{
		// intent may be null if the service is being restarted
		if(intent==null) return;
		
		Logcat.d("IntentService.onHandleIntent(): " + intent.getIntExtra("arg", -1));
		
		long endTime = System.currentTimeMillis() + 5l * 1000l;
		while(System.currentTimeMillis() < endTime)
		{
			synchronized(this)
			{
				try
				{
					// TODO: do something
					wait(endTime - System.currentTimeMillis());
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Override
	public void onDestroy()
	{
		Logcat.d("IntentService.onDestroy()");
		
		super.onDestroy();
	}
}
