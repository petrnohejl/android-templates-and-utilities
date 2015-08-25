package com.example.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ExampleConfig;
import com.example.R;
import com.example.gcm.GcmUtility;
import com.example.utility.Logcat;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class ExampleActivity extends AppCompatActivity
{
	private GoogleCloudMessaging mGcm;
	private String mGcmRegistrationId;
	private AsyncTask<Void, Void, Void> mGcmRegisterAsyncTask;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		
		// handle GCM registration
		handleGcmRegistration();
	}


	@Override
	public void onResume()
	{
		super.onResume();

		// check Google Play Services
		GcmUtility.checkPlayServices(this);
	}


	@Override
	public void onDestroy()
	{
		// cancel async tasks
		if(mGcmRegisterAsyncTask!=null) mGcmRegisterAsyncTask.cancel(true);
		
		super.onDestroy();
	}


	private void handleGcmRegistration()
	{
		final Context context = getApplicationContext();

		// check device for Play Services APK
		if(GcmUtility.checkPlayServices(this))
		{
			// registration id
			mGcm = GoogleCloudMessaging.getInstance(this);
			mGcmRegistrationId = GcmUtility.getRegistrationId(context);

			// register device
			if(mGcmRegistrationId.isEmpty())
			{
				mGcmRegisterAsyncTask = new AsyncTask<Void, Void, Void>()
				{
					@Override
					protected Void doInBackground(Void... params)
					{
						try
						{
							// register on GCM server
							if(mGcm==null) mGcm = GoogleCloudMessaging.getInstance(context);
							mGcmRegistrationId = mGcm.register(ExampleConfig.GCM_SENDER_ID);

							// GcmUtility.register() must be called after successfull GoogleCloudMessaging.register(),
							// because it sets registration id in shared preferences
							GcmUtility.register(context, mGcmRegistrationId);
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						return null;
					}


					@Override
					protected void onPostExecute(Void result)
					{
						mGcmRegisterAsyncTask = null;
					}
				};
				mGcmRegisterAsyncTask.execute(null, null, null);
			}
			else
			{
				Logcat.d("device is already registered on server with id = %s", mGcmRegistrationId);
			}
		}
		else
		{
			Logcat.d("no valid Google Play Services APK found");
		}
	}
}
