package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TabHost;

import com.example.R;
import com.example.adapter.TabHostFragmentPagerAdapter;
import com.example.fragment.ExampleFragment;


public class TabHostActivity extends ActionBarActivity
{
	private TabHostFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost);
		
		renderView();
	}
	
	
	private void renderView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_tabhost_pager);
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		// tab host
		tabHost.setup();
		
		// pager content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new TabHostFragmentPagerAdapter(this, tabHost, viewPager);
			mAdapter.addTab(tabHost.newTabSpec("tag0").setIndicator("Title 0"), ExampleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag1").setIndicator("Title 1"), ExampleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag2").setIndicator("Title 2"), ExampleFragment.class, null);
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
	}
}
