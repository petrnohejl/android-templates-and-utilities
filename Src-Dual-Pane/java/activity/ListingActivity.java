package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.R;
import com.example.fragment.SimpleFragment;
import com.example.listener.OnDualPaneShowListener;


public class ListingActivity extends ActionBarActivity implements OnDualPaneShowListener
{
	private static final String SAVED_DUAL_PANE_FRAGMENT = "dual_pane_fragment";
	private static final String SAVED_DUAL_PANE_INDEX = "dual_pane_index";
	
	private boolean mDualPane;
	private Class<?> mDualPaneFragment = null;
	private int mDualPaneIndex = -1;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_listing);
		
		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}
		
		// handle dual pane layout
		handleDualPane();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		
		if(mDualPaneFragment!=null) outState.putString(SAVED_DUAL_PANE_FRAGMENT, mDualPaneFragment.getName());
		if(mDualPaneIndex!=-1) outState.putInt(SAVED_DUAL_PANE_INDEX, mDualPaneIndex);
	}
	
	
	@Override
	public void onDualPaneShow(Class<?> targetFragment, int index)
	{
		mDualPaneFragment = targetFragment;
		mDualPaneIndex = index;
		
		if(mDualPane)
		{
			Fragment fragment = null;
			String tag = null;
			
			// type of fragment to be replaced
			if(targetFragment.equals(SimpleFragment.class))
			{
				fragment = SimpleFragment.newInstance(index);
				tag = SimpleFragment.class.getSimpleName();
			}

			// replace fragment
			if(fragment!=null)
			{
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container_dual_pane, fragment, tag);
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				transaction.commit();
			}
		}
		else
		{
			Class<?> activity = null;
			Bundle extras = null;
			
			// type of activity to be started
			if(targetFragment.equals(SimpleFragment.class))
			{
				activity = SimpleActivity.class;
				extras = new Bundle();
				extras.putInt(SimpleActivity.EXTRA_PRODUCT_ID, index);
			}
			
			// start activity
			if(activity!=null)
			{
				Intent intent = new Intent(this, activity);
				if(extras!=null) intent.putExtras(extras);
				startActivity(intent);
			}
		}
	}
	
	
	private void handleSavedInstanceState(Bundle savedInstanceState)
	{
		String dualPaneFragmentString = savedInstanceState.getString(SAVED_DUAL_PANE_FRAGMENT);
		try
		{
			if(dualPaneFragmentString!=null) mDualPaneFragment = Class.forName(dualPaneFragmentString);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		mDualPaneIndex = savedInstanceState.getInt(SAVED_DUAL_PANE_INDEX, -1);
	}

	
	private void handleDualPane()
	{
		// activity has dual pane layout
		View dualPaneContainer = findViewById(R.id.container_dual_pane);
		mDualPane = dualPaneContainer != null && dualPaneContainer.getVisibility() == View.VISIBLE;

		if(mDualPane && mDualPaneFragment!=null && mDualPaneIndex!=-1)
		{
			onDualPaneShow(mDualPaneFragment, mDualPaneIndex);
		}
	}
}
