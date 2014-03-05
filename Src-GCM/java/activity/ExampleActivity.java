package com.example.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.ExampleConfig;
import com.example.R;
import com.example.gcm.GCMUtility;
import com.example.utility.Logcat;
import com.google.android.gcm.GCMRegistrar;


public class ExampleActivity extends ActionBarActivity
{
	AsyncTask<Void, Void, Void> mRegisterAsyncTask;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_example);
		
		// handle GCM registration
		handleGCMRegistration();
	}


	@Override
	public void onDestroy()
	{
		if(mRegisterAsyncTask!=null) mRegisterAsyncTask.cancel(true);
		GCMRegistrar.onDestroy(getApplicationContext());
		
		super.onDestroy();
	}


	private void handleGCMRegistration()
	{
		final Context context = getApplicationContext();
		
		// make sure the device has the proper dependencies
		GCMRegistrar.checkDevice(context);
		
		// make sure the manifest was properly set
		GCMRegistrar.checkManifest(context);
		
		// registration id
		final String registrationId = GCMRegistrar.getRegistrationId(context);
		
		if(registrationId.equals(""))
		{
			// automatically registers application on startup
			GCMRegistrar.register(context, ExampleConfig.GCM_SENDER_ID);
		}
		else
		{
			// device is already registered on GCM service, check server
			if(GCMRegistrar.isRegisteredOnServer(context))
			{
				// skips registration
				Logcat.d("Activity.onCreate(): device is already registered on server");
			}
			else
			{
				// Try to register on server again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(), hence the use of AsyncTask instead of a raw thread.
				Logcat.d("Activity.onCreate(): device is not registered on server");
				mRegisterAsyncTask = new AsyncTask<Void, Void, Void>()
				{
					@Override
					protected Void doInBackground(Void... params)
					{
						GCMUtility.register(context, registrationId);
						return null;
					}
					
					
					@Override
					protected void onPostExecute(Void result)
					{
						mRegisterAsyncTask = null;
					}
				};
				mRegisterAsyncTask.execute(null, null, null);
			}
		}
	}
}
