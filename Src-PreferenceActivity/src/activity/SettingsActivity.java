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
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// setHeaderRes() must be called before super.onCreate()
		setHeaderRes(R.xml.prefs_headers);
		
		// set desired preference file and mode (optional)
		setSharedPreferencesName("unified_preferences");
		setSharedPreferencesMode(Context.MODE_PRIVATE);
		
		super.onCreate(savedInstanceState);
		
		setActionBar();
		
		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}
		
		// handle intent extras
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
		}
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
	public void onSaveInstanceState(Bundle outState) 
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		
		// TODO
	}
	
	
	@Override
	public void onRestoreInstanceState (Bundle savedInstanceState)
	{
		// restore saved state
		super.onRestoreInstanceState(savedInstanceState);
		
		if(savedInstanceState != null)
		{
			// TODO
		}
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
		switch (item.getItemId()) 
		{
			case android.R.id.home:
				// TODO
				Intent intent = new Intent(this, SettingsActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void handleSavedInstanceState(Bundle savedInstanceState)
	{
		// TODO
	}
	
	
	private void handleExtras(Bundle extras)
	{
		// TODO
	}
	
	
	private void setActionBar()
	{
		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
		
		setSupportProgressBarIndeterminateVisibility(false);
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
