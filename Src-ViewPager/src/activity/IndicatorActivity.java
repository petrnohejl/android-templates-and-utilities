package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.example.R;
import com.example.adapter.IndicatorFragmentPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;


public class IndicatorActivity extends SherlockFragmentActivity
{
	private IndicatorFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_indicator);
		
		renderView();
	}
	
	
	private void renderView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_indicator_pager);
		CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.activity_indicator_indicator);
		
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new IndicatorFragmentPagerAdapter(getSupportFragmentManager());
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
		
		// set adapter
		viewPager.setAdapter(mAdapter);
		
		// set indicator
		circlePageIndicator.setViewPager(viewPager);
	}
}
