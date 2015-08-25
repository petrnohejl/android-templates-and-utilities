package com.example.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.utility.ExampleFileObserver;
import com.example.utility.ExternalStorageUtility;
import com.example.utility.Logcat;


public class ExampleFileObserverService extends Service
{
	private List<ExampleFileObserver> mExampleFileObserverList;


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// start file observers
		if(mExampleFileObserverList==null)
		{
			Logcat.d("start file observers");
			startFileObservers();
		}
		else
		{
			Logcat.d("file observers already running");
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
		// stop file observers
		if(mExampleFileObserverList!=null) stopFileObservers();
	}
	
	
	private void startFileObservers()
	{
		if(ExternalStorageUtility.isAvailable())
		{
			// create list
			mExampleFileObserverList = new ArrayList<>();
						
			// watch directories
			List<File> directoryList = getObservedDirectories();
			for(int i=0; i<directoryList.size(); i++)
			{
				File directory = directoryList.get(i);
				
				if(directory.exists() && directory.isDirectory())
				{
					ExampleFileObserver observer = new ExampleFileObserver(directory.getAbsolutePath());
					observer.startWatching();
					mExampleFileObserverList.add(observer);
				}
			}
		}
	}
	
	
	private void stopFileObservers()
	{
		for(int i=0; i<mExampleFileObserverList.size(); i++)
		{
			ExampleFileObserver observer = mExampleFileObserverList.get(i);
			if(observer!=null)
			{
				observer.stopWatching();
				observer = null;
			}
		}
		mExampleFileObserverList = null;
	}
}
