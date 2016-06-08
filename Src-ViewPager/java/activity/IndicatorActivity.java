package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.adapter.IndicatorFragmentPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;


public class IndicatorActivity extends AppCompatActivity
{
	private IndicatorFragmentPagerAdapter mAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator);
		bindData();
	}


	private void bindData()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_indicator_pager);
		CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.activity_indicator_indicator);

		// pager content
		if(mAdapter == null)
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
