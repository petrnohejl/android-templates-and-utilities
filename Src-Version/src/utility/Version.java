package com.example.utility;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;


public class Version
{
	@SuppressWarnings("rawtypes")
	public static String getApplicationVersion(Context context, Class cls)
	{
		try
		{
			ComponentName component = new ComponentName(context, cls);
			PackageInfo info = context.getPackageManager().getPackageInfo(component.getPackageName(), 0);
			return info.versionName;
		}
		catch(android.content.pm.PackageManager.NameNotFoundException e)
		{
			return "";
		}
	}
}
