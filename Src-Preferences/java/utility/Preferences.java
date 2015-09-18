package com.example.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.R;
import com.example.ExampleApplication;


public class Preferences
{
	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1l;
	public static final double NULL_DOUBLE = -1.0;
	public static final String NULL_STRING = null;

	private SharedPreferences mSharedPreferences;
	private Context mContext;

	
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


	// getters


	public long getUserId()
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		return mSharedPreferences.getLong(key, NULL_LONG);
	}
	
	
	public String getPassword()
	{
		String key = mContext.getString(R.string.prefs_key_password);
		return mSharedPreferences.getString(key, NULL_STRING);
	}


	public String getVersion()
	{
		String key = mContext.getString(R.string.prefs_key_version);
		return mSharedPreferences.getString(key, NULL_STRING);
	}


	public int getLaunch()
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		return mSharedPreferences.getInt(key, 0);
	}


	public boolean isRated()
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		return mSharedPreferences.getBoolean(key, false);
	}


	public String getFacebookAccessToken()
	{
		String key = mContext.getString(R.string.prefs_key_fb_access_token);
		return mSharedPreferences.getString(key, NULL_STRING);
	}
	
	
	public long getFacebookAccessExpiration()
	{
		String key = mContext.getString(R.string.prefs_key_fb_expiration);
		return mSharedPreferences.getLong(key, NULL_LONG);
	}


	// setters


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
