package com.example.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.R;
import com.example.activities.ExampleApplication;

public class Preferences
{
	private SharedPreferences mSharedPreferences;
	private Context mContext;
	
	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1;
	public static final double NULL_DOUBLE = -1;
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
		String key = mContext.getString(R.string.preferences_key_user_id);
		long value = mSharedPreferences.getLong(key, NULL_LONG);
		return value;
	}
	
	
	public String getPassword()
	{
		String key = mContext.getString(R.string.preferences_key_password);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}
	

	// SETTERS ////////////////////////////////////////////////////////////////////////////////////
	
	
	public void setUserId(long userId)
	{
		String key = mContext.getString(R.string.preferences_key_user_id);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putLong(key, userId);
		editor.commit();
	}
	
	
	public void setPassword(String password)
	{
		String key = mContext.getString(R.string.preferences_key_password);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, password);
		editor.commit();
	}
}
