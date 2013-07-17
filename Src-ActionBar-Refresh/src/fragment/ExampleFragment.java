package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.R;


public class ExampleFragment extends SherlockFragment
{
	private boolean mActionBarProgress = false;
	private View mRootView;
	private MenuItem mRefreshMenuItem;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);
		
		// TODO

		// reference to refresh menu item
		mRefreshMenuItem = menu.findItem(R.id.ab_button_refresh);
		
		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behaviour
		switch (item.getItemId()) 
		{
			// TODO

			case R.id.ab_button_refresh:
				refreshData();
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public void refreshData()
	{
		// TODO
	}


	private void showActionBarProgress(boolean visible)
	{
		// show action view progress
		if(mRefreshMenuItem!=null)
		{
			if(visible)
			{
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.ab_action_refresh, null);
				mRefreshMenuItem.setActionView(view);
			}
			else
			{
				mRefreshMenuItem.setActionView(null);
			}
		}
		
		// show action bar progress
		//getSherlockActivity().setSupportProgressBarIndeterminateVisibility(visible);
		
		mActionBarProgress = visible;
	}
}
