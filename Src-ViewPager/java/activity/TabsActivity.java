package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.adapter.TabsFragmentPagerAdapter;
import com.example.fragment.ExampleFragment;


public class TabsActivity extends AppCompatActivity
{
	private TabsFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);
		renderView();
	}
	
	
	private void renderView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_tabs_pager);
		
		// navigation mode
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new TabsFragmentPagerAdapter(this, actionBar, viewPager);
			mAdapter.addTab(actionBar.newTab().setText("Title 0"), ExampleFragment.class, null);
			mAdapter.addTab(actionBar.newTab().setText("Title 1"), ExampleFragment.class, null);
			mAdapter.addTab(actionBar.newTab().setText("Title 2"), ExampleFragment.class, null);
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
	}
}
