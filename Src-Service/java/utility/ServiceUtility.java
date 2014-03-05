package com.example.utility;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;


public class ServiceUtility
{
	public static boolean isServiceRunning(Context context, Class<?> cls)
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE))
		{
			if(cls.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}
}
