package com.example.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.R;
import com.example.fragment.DetailFragment;
import com.example.fragment.ListingFragment;


public class ExampleActivity extends AppCompatActivity
{
	private SlidingPaneLayout mSlidingPaneLayout;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		setupActionBar();
		setupSlidingPane(savedInstanceState);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behavior
		switch(item.getItemId())
		{
			case android.R.id.home:
				if(!mSlidingPaneLayout.isOpen())
				{
					mSlidingPaneLayout.openPane();
					return true;
				}

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	private void setupActionBar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(false);
	}


	private void setupSlidingPane(Bundle savedInstanceState)
	{
		// reference
		mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.activity_example_sliding_pane_layout);

		// set slide listener
		mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener()
		{
			@Override
			public void onPanelSlide(View view, float v)
			{
			}


			@Override
			public void onPanelOpened(View view)
			{
				onSlidingPaneOpened();
			}


			@Override
			public void onPanelClosed(View view)
			{
				onSlidingPaneClosed();
			}
		});
		mSlidingPaneLayout.openPane();

		// set global layout listener
		mSlidingPaneLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{
				if(mSlidingPaneLayout.isSlideable() && !mSlidingPaneLayout.isOpen())
				{
					onSlidingPaneClosed();
				}
				else
				{
					onSlidingPaneOpened();
				}

				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				{
					mSlidingPaneLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				else
				{
					mSlidingPaneLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			}
		});

		// add fragments
		if(savedInstanceState == null)
		{
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.activity_example_sliding_pane_left, ListingFragment.newInstance()).commitAllowingStateLoss();
			fragmentManager.beginTransaction().replace(R.id.activity_example_sliding_pane_right, DetailFragment.newInstance()).commitAllowingStateLoss();
		}
	}


	private void onSlidingPaneOpened()
	{
		Fragment fragmentLeft = getSupportFragmentManager().findFragmentById(R.id.activity_example_sliding_pane_left);
		Fragment fragmentRight = getSupportFragmentManager().findFragmentById(R.id.activity_example_sliding_pane_right);

		if(mSlidingPaneLayout.isSlideable())
		{
			if(fragmentLeft != null) fragmentLeft.setHasOptionsMenu(true);
			if(fragmentRight != null) fragmentRight.setHasOptionsMenu(false);
		}
		else
		{
			if(fragmentLeft != null) fragmentLeft.setHasOptionsMenu(false);
			if(fragmentRight != null) fragmentRight.setHasOptionsMenu(true);
		}

		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(false);
	}


	private void onSlidingPaneClosed()
	{
		Fragment fragmentLeft = getSupportFragmentManager().findFragmentById(R.id.activity_example_sliding_pane_left);
		Fragment fragmentRight = getSupportFragmentManager().findFragmentById(R.id.activity_example_sliding_pane_right);

		if(fragmentLeft != null) fragmentLeft.setHasOptionsMenu(false);
		if(fragmentRight != null) fragmentRight.setHasOptionsMenu(true);

		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
	}
}
