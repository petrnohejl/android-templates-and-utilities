package com.example.activity;

import net.saik0.android.unifiedpreference.UnifiedPreferenceFragment;
import net.saik0.android.unifiedpreference.UnifiedSherlockPreferenceActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.R;


public class SettingsActivity extends UnifiedSherlockPreferenceActivity
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, SettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// setHeaderRes() must be called before super.onCreate()
		setHeaderRes(R.xml.prefs_headers);
		
		// set desired preference file and mode (optional)
		setSharedPreferencesName("unified_preferences");
		setSharedPreferencesMode(Context.MODE_PRIVATE);
		
		super.onCreate(savedInstanceState);
		
		setupActionBar();
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behaviour
		switch(item.getItemId()) 
		{
			case android.R.id.home:
				// TODO
				Intent intent = SettingsActivity.newIntent(this);
				startActivity(intent);
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void setupActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
	}


	public static class GeneralPreferenceFragment extends UnifiedPreferenceFragment
	{
		// TODO
	}


	public static class NotificationPreferenceFragment extends UnifiedPreferenceFragment
	{
		// TODO
	}


	public static class DataSyncPreferenceFragment extends UnifiedPreferenceFragment
	{
		// TODO
	}
}
