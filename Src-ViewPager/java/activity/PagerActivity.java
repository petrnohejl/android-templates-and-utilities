package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.R;
import com.example.adapter.PagerFragmentPagerAdapter;


public class PagerActivity extends ActionBarActivity
{
	private PagerFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);
		renderView();
	}
	
	
	private void renderView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_pager_pager);
		
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new PagerFragmentPagerAdapter(getSupportFragmentManager());
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
		
		// set adapter
		viewPager.setAdapter(mAdapter);
	}
}
