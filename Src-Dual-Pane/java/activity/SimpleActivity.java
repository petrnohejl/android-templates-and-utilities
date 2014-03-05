package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.R;


public class SimpleActivity extends ActionBarActivity
{
	public static final String EXTRA_PRODUCT_ID = "product_id";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// if activity has dual pane layout, we don't need this activity
		if(getResources().getBoolean(R.bool.dual_pane))
		{
			finish();
			return;
		}
		
		setContentView(R.layout.activity_simple);
	}
}
