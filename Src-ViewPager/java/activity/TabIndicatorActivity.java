package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.example.R;
import com.example.adapter.TabIndicatorFragmentPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;


public class TabIndicatorActivity extends ActionBarActivity
{
	private TabIndicatorFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_tabindicator);
		
		renderView();
	}
	
	
	private void renderView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_tabindicator_pager);
		TabPageIndicator tabPageIndicator = (TabPageIndicator) findViewById(R.id.activity_tabindicator_indicator);
		
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new TabIndicatorFragmentPagerAdapter(getSupportFragmentManager());
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
		
		// set adapter
		viewPager.setAdapter(mAdapter);
		
		// set indicator
		tabPageIndicator.setViewPager(viewPager);
	}
}
