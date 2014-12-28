package com.example.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.R;
import com.example.adapter.ActionBarListNavigationAdapter;


public class ExampleActivity extends ActionBarActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		
		// action bar navigation
		CharSequence[] items = { "One", "Two", "Three" };
		String subtitle = "Subtitle";
		setupActionBarNavigation(items, subtitle, 0);
	}
	
	
	private void setupActionBarNavigation(CharSequence[] items, String subtitle, int selectedItem)
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar bar = getSupportActionBar();
		Context context = bar.getThemedContext();
		
		// adapter
		ActionBarListNavigationAdapter<CharSequence> adapter = new ActionBarListNavigationAdapter<CharSequence>(context, android.R.layout.simple_spinner_item, items, subtitle);
		adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		
		// action bar navigation
		bar.setDisplayShowTitleEnabled(false);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(adapter, new OnNavigationListener()
		{
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId)
			{
				// TODO
				return true;
			}
		});
		if(selectedItem < items.length) bar.setSelectedNavigationItem(selectedItem);
	}
}
