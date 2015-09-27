package com.example.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


// requires android.permission.ACCESS_NETWORK_STATE
public final class NetworkUtility
{
	private NetworkUtility() {}


	public static boolean isOnline(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected());
	}
	
	
	public static int getType(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(networkInfo!=null)
		{
			// returns ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE etc.
			return networkInfo.getType();
		}
		else
		{
			return -1;
		}
	}
	
	
	public static String getTypeName(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if(networkInfo!=null)
		{
			return networkInfo.getTypeName();
		}
		else
		{
			return null;
		}
	}
}
