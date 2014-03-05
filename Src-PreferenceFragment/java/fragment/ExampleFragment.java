package com.example.fragment;

import com.example.R;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;


public class ExampleFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		
		addPreferencesFromResource(R.xml.prefs);
	}
}
