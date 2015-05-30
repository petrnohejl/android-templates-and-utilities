package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.utility.Preferences;
import com.example.utility.Version;


public class ExampleActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		checkNewVersion();
		checkLaunch();
	}


	private void checkNewVersion()
	{
		Preferences preferences = new Preferences(this);
		
		String currentVersion = Version.getVersionName(this);
		String lastVersion = preferences.getVersion();
		
		// new version is available
		if(!currentVersion.equals(lastVersion))
		{
			// TODO: do something
			
			// set new version in preferences
			preferences.setVersion(currentVersion);
		}
	}


	private void checkLaunch()
	{
		// get current launch
		Preferences preferences = new Preferences(this);
		final int launch = preferences.getLaunch();

		// check launch number
		boolean showDialog = false;
		if(!preferences.isRated())
		{
			if(launch==3) showDialog = true;
			else if(launch>=10 && launch%10==0) showDialog = true;
		}

		// show rating dialog
		if(showDialog)
		{
			// TODO: show rating dialog
		}

		// increment launch
		preferences.setLaunch(launch + 1);
	}
}
