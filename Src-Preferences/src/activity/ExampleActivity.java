package com.example.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.example.R;


public class ExampleActivity extends SherlockFragmentActivity implements OnSharedPreferenceChangeListener
{
	private boolean mPreferencesChanged = false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_example);
		
		// register listener
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		// preferences have changed so refresh data
		if(mPreferencesChanged)
		{
			refreshData();
			mPreferencesChanged = false;
		}
	}
	
	
	@Override
	public void onDestroy()
	{
		// unregister listener
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
		
		super.onDestroy();
	}
	
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		if(key.equals(getString(R.string.prefs_key_example)))
		{
			mPreferencesChanged = true;
		}
	}
}
