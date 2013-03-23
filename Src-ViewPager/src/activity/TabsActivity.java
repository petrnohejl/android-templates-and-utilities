package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.example.R;
import com.example.adapter.TabsFragmentPagerAdapter;
import com.example.fragment.SimpleFragment;


public class TabsActivity extends SherlockFragmentActivity
{
	private TabsFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
			mAdapter.addTab(actionBar.newTab().setText("Title 0"), SimpleFragment.class, null);
			mAdapter.addTab(actionBar.newTab().setText("Title 1"), SimpleFragment.class, null);
			mAdapter.addTab(actionBar.newTab().setText("Title 2"), SimpleFragment.class, null);
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
	}
}
