package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utility.Logcat;


public class ExampleAlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Logcat.d("ExampleAlarmReceiver.onReceive()");
		
		// TODO: do something
	}
}
