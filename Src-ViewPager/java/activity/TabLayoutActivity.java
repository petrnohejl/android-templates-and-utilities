package com.example.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.adapter.TabLayoutFragmentPagerAdapter;


public class TabLayoutActivity extends AppCompatActivity
{
	private TabLayoutFragmentPagerAdapter mAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablayout);
		setupView();
	}


	private void setupView()
	{
		// reference
		ViewPager viewPager = (ViewPager) findViewById(R.id.activity_tablayout_pager);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_tablayout_tabs);

		// pager content
		if(mAdapter == null)
		{
			// create adapter
			mAdapter = new TabLayoutFragmentPagerAdapter(getSupportFragmentManager());
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}

		// set adapter
		viewPager.setAdapter(mAdapter);

		// tab layout
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
}
