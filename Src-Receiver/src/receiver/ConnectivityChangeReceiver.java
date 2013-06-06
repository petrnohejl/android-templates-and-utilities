package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utility.Logcat;
import com.example.utility.NetworkManager;


public class ConnectivityChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Logcat.d("ConnectivityChangeReceiver.onReceive(): " + NetworkManager.getTypeName(context));
		
		// TODO: do something
	}
}
