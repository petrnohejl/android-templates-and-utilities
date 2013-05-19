package com.example.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.R;
import com.example.gcm.GcmUtility;
import com.example.utility.Logcat;
import com.google.android.gcm.GCMRegistrar;


public class GcmActivity extends SherlockFragmentActivity
{
	AsyncTask<Void, Void, Void> mRegisterAsyncTask;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gcm);
		
		// handle GCM registration
		handleGcmRegistration();
	}


	@Override
	public void onDestroy()
	{
		if(mRegisterAsyncTask!=null) mRegisterAsyncTask.cancel(true);
		GCMRegistrar.onDestroy(this);
		
		super.onDestroy();
	}


	private void handleGcmRegistration()
	{
		// make sure the device has the proper dependencies
		GCMRegistrar.checkDevice(this);
		
		// make sure the manifest was properly set
		GCMRegistrar.checkManifest(this);
		
		// registration id
		final String registrationId = GCMRegistrar.getRegistrationId(this);
		
		if(registrationId.equals(""))
		{
			// automatically registers application on startup
			GCMRegistrar.register(this, GcmUtility.SENDER_ID);
		}
		else
		{
			// device is already registered on GCM service, check server
			if(GCMRegistrar.isRegisteredOnServer(this))
			{
				// skips registration
				Logcat.d("Activity.onCreate(): device is already registered on server");
			}
			else
			{
				// Try to register on server again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(), hence the use of AsyncTask instead of a raw thread.
				Logcat.d("Activity.onCreate(): device is not registered on server");
				final Context context = this;
				mRegisterAsyncTask = new AsyncTask<Void, Void, Void>()
				{
					@Override
					protected Void doInBackground(Void... params)
					{
						GcmUtility.register(context, registrationId);
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
