package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.example.utility.Logcat;


public class ExampleService extends Service
{
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;


	@Override
	public void onCreate()
	{
		Logcat.d("");

		HandlerThread thread = new HandlerThread("ServiceStartArguments", Thread.NORM_PRIORITY);
		thread.start();

		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Logcat.d("");

		// intent may be null if the service is being restarted
		if(intent != null)
		{
			Message msg = mServiceHandler.obtainMessage();
			msg.arg1 = startId;
			msg.arg2 = intent.getIntExtra("arg", -1);
			mServiceHandler.sendMessage(msg);
		}

		return START_STICKY; // TODO: START_STICKY, START_NOT_STICKY, START_REDELIVER_INTENT
	}


	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}


	@Override
	public void onDestroy()
	{
		Logcat.d("");
	}


	private final class ServiceHandler extends Handler
	{
		public ServiceHandler(Looper looper)
		{
			super(looper);
		}


		@Override
		public void handleMessage(Message msg)
		{
			Logcat.d("%d", msg.arg2);

			long endTime = System.currentTimeMillis() + 5L * 1000L;

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

			stopSelf(msg.arg1);
		}
	}
}
