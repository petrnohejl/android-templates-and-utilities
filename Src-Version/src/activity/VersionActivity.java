package com.example.activity;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.R;
import com.example.utility.Preferences;
import com.example.utility.Version;


public class VersionActivity extends SherlockFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_version);
		
		checkVersion();
	}


	private void checkVersion()
	{
		Preferences preferences = new Preferences(this);
		
		String currentVersion = Version.getApplicationVersion(this, ListingActivity.class);
		String lastVersion = preferences.getVersion();
		
		// new version is available
		if(!currentVersion.equals(lastVersion))
		{
			// TODO: do something
			
			// set new version in preferences
			preferences.setVersion(currentVersion);
		}
	}
}
