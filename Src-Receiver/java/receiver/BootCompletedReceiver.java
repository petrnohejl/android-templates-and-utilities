package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utility.Logcat;


public class BootCompletedReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Logcat.d("BootCompletedReceiver.onReceive()");
		
		// TODO: do something
	}
}
