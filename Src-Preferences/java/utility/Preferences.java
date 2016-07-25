package com.example.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.ExampleApplication;
import com.example.R;


public class Preferences
{
	private Context mContext;
	private SharedPreferences mSharedPreferences;


	public Preferences()
	{
		mContext = ExampleApplication.getContext();
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}


	public void clearPreferences()
	{
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
	}


	public long getUserId()
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		return mSharedPreferences.getLong(key, -1L);
	}


	public void setUserId(long userId)
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putLong(key, userId);
		editor.commit();
	}


	public String getPassword()
	{
		String key = mContext.getString(R.string.prefs_key_password);
		return mSharedPreferences.getString(key, null);
	}


	public void setPassword(String password)
	{
		String key = mContext.getString(R.string.prefs_key_password);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, password);
		editor.commit();
	}


	public String getVersion()
	{
		String key = mContext.getString(R.string.prefs_key_version);
		return mSharedPreferences.getString(key, null);
	}


	public void setVersion(String version)
	{
		String key = mContext.getString(R.string.prefs_key_version);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, version);
		editor.commit();
	}


	public int getLaunch()
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		return mSharedPreferences.getInt(key, 0);
	}


	public void setLaunch(int launch)
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(key, launch);
		editor.commit();
	}


	public boolean isRated()
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		return mSharedPreferences.getBoolean(key, false);
	}


	public void setRated(boolean rated)
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, rated);
		editor.commit();
	}
}
