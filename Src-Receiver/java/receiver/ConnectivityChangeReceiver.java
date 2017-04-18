package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;


public class ConnectivityChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Logcat.d(NetworkUtility.getTypeName(context));

		// TODO: do something
	}
}
