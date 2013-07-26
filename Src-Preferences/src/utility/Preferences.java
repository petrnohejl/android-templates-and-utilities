package com.example.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.R;
import com.example.ExampleApplication;


public class Preferences
{
	private SharedPreferences mSharedPreferences;
	private Context mContext;
	
	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1l;
	public static final double NULL_DOUBLE = -1.0;
	public static final String NULL_STRING = null;
	
	
	public Preferences(Context context)
	{
		if(context==null) context = ExampleApplication.getContext();
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mContext = context;
	}
	
	
	public void clearPreferences()
	{
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
	
	
	// GETTERS ////////////////////////////////////////////////////////////////////////////////////
	
	
	public long getUserId()
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		long value = mSharedPreferences.getLong(key, NULL_LONG);
		return value;
	}
	
	
	public String getPassword()
	{
		String key = mContext.getString(R.string.prefs_key_password);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public String getVersion()
	{
		String key = mContext.getString(R.string.prefs_key_version);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public int getLaunch()
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		int value = mSharedPreferences.getInt(key, 0);
		return value;
	}


	public boolean isRated()
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		boolean value = mSharedPreferences.getBoolean(key, false);
		return value;
	}


	public String getFacebookAccessToken()
	{
		String key = mContext.getString(R.string.prefs_key_fb_access_token);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}
	
	
	public long getFacebookAccessExpiration()
	{
		String key = mContext.getString(R.string.prefs_key_fb_expiration);
		long value = mSharedPreferences.getLong(key, NULL_LONG);
		return value;
	}


	// SETTERS ////////////////////////////////////////////////////////////////////////////////////
	
	
	public void setUserId(long userId)
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putLong(key, userId);
		editor.commit();
	}
	
	
	public void setPassword(String password)
	{
		String key = mContext.getString(R.string.prefs_key_password);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, password);
		editor.commit();
	}


	public void setVersion(String version)
	{
		String key = mContext.getString(R.string.prefs_key_version);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, version);
		editor.commit();
	}


	public void setLaunch(int launch)
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(key, launch);
		editor.commit();
	}


	public void setRated(boolean rated)
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, rated);
		editor.commit();
	}


	public void setFacebookAccessToken(String facebookAccessToken)
	{
		String key = mContext.getString(R.string.prefs_key_fb_access_token);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, facebookAccessToken);
		editor.commit();
	}
	
	
	public void setFacebookAccessExpiration(long expiration)
	{
		String key = mContext.getString(R.string.prefs_key_fb_expiration);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putLong(key, expiration);
		editor.commit();
	}
}
