package com.example.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.example.R;
import com.example.adapter.TabHostFragmentPagerAdapter;
import com.example.fragment.SimpleFragment;


public class TabHostActivity extends SherlockFragmentActivity
{
	private TabHostFragmentPagerAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
			mAdapter.addTab(tabHost.newTabSpec("tag0").setIndicator("Title 0"), SimpleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag1").setIndicator("Title 1"), SimpleFragment.class, null);
			mAdapter.addTab(tabHost.newTabSpec("tag2").setIndicator("Title 2"), SimpleFragment.class, null);
		}
		else
		{
			// refill adapter
			mAdapter.refill();
		}
	}
}
