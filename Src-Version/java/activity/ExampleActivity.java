package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.utility.Preferences;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.VersionUtility;


public class ExampleActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		checkNewVersion();
		checkRateCounter();
	}


	private void checkNewVersion()
	{
		Preferences preferences = new Preferences();
		String currentVersion = VersionUtility.getVersionName(this);
		String lastVersion = preferences.getVersion();

		// new version is available
		if(!currentVersion.equals(lastVersion))
		{
			// TODO: do something

			// set new version in preferences
			preferences.setVersion(currentVersion);
		}
	}


	private void checkRateCounter()
	{
		// get current rate counter
		Preferences preferences = new Preferences();
		final int rateCounter = preferences.getRateCounter();
		Logcat.d("" + rateCounter);

		// check rate counter
		boolean showMessage = false;
		if(rateCounter != -1)
		{
			if(rateCounter >= 10 && rateCounter % 10 == 0) showMessage = true;
		}
		else
		{
			return;
		}

		// show rate message
		if(showMessage)
		{
			// TODO: show message
			// TODO: set rate counter to -1
		}

		// increment rate counter
		preferences.setRateCounter(rateCounter + 1);
	}
}
