package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;


public class ExampleActivity extends ActionBarActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setupActionBar();
		setContentView(R.layout.activity_example);
	}
	
	
	private void setupActionBar()
	{
		ActionBar bar = getSupportActionBar();
		
		// custom view
		LayoutInflater inflater = (LayoutInflater) bar.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View customView = inflater.inflate(R.layout.ab_custom_donebar, null);
		customView.findViewById(R.id.ab_custom_donebar_done).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO
			}
		});
		customView.findViewById(R.id.ab_custom_donebar_cancel).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO
			}
		});
		
		// setup action bar
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		bar.setCustomView(customView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}
}
