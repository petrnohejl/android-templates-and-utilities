package com.example.activity;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.R;


public class ExampleActivity extends ActionBarActivity
{
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_example);
		
		// pull to refresh
		setupPullToRefresh();
	}
	
	
	private void setupPullToRefresh()
	{
		PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
		options.refreshScrollDistance = 0.5f;
		options.refreshOnUp = false;
		options.refreshMinimize = true;
		options.refreshMinimizeDelay = 1 * 1000;
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this, options);
	}
	
	
	public PullToRefreshAttacher getPullToRefreshAttacher()
	{
		return mPullToRefreshAttacher;
	}
}
