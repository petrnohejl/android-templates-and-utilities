package com.example.broadcast;

import android.content.Context;
import android.content.Intent;


public class ExampleBroadcast
{
	public static final String ACTION_EXAMPLE = "com.example.EXAMPLE";
	public static final String EXTRA_ARG = "arg";


	public static void sendBroadcast(Context context, String arg)
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_EXAMPLE);
		broadcastIntent.putExtra(EXTRA_ARG, arg);
		context.sendBroadcast(broadcastIntent);
	}
}
