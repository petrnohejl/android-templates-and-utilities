package com.example.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.R;
import com.example.client.APICallManager;
import com.example.client.request.ExampleRequest;
import com.example.utility.NetworkUtility;


public class ListingFragment extends TaskFragment implements SwipeRefreshLayout.OnRefreshListener
{
	private boolean mActionBarProgress = false;
	private View mRootView;
	private APICallManager mAPICallManager = new APICallManager();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_listing, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// pull to refresh
		setupSwipeRefreshLayout();

		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}
	
	
	@Override
	public void onRefresh()
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				refreshData();
			}
		});

//		// testing task
//		showActionBarProgress(true);
//		new AsyncTask<Void, Void, Void>()
//		{
//			@Override
//			protected Void doInBackground(Void... params)
//			{
//				try
//				{
//					// TODO: do something
//					Thread.sleep(2000);
//				}
//				catch(InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				return null;
//			}
//			
//			@Override
//			protected void onPostExecute(Void result)
//			{
//				super.onPostExecute(result);
//				showActionBarProgress(false);
//			}
//		}.execute();
	}
	
	
	public void refreshData()
	{
		if(NetworkUtility.isOnline(getActivity()))
		{
			if(!mAPICallManager.hasRunningTask(ExampleRequest.class))
			{
				// show progress in action bar
				showActionBarProgress(true);
				
				// TODO
			}
		}
		else
		{
			showActionBarProgress(false);
			Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
		}
	}


	private void showActionBarProgress(boolean visible)
	{
		// show pull to refresh progress bar
		SwipeRefreshLayout listSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.container_swipe_refresh_list);
		SwipeRefreshLayout emptySwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(android.R.id.empty);

		listSwipeRefreshLayout.setRefreshing(visible);
		listSwipeRefreshLayout.setEnabled(!visible);

		emptySwipeRefreshLayout.setRefreshing(visible);
		emptySwipeRefreshLayout.setEnabled(!visible);

		mActionBarProgress = visible;
	}


	private void setupSwipeRefreshLayout()
	{
		SwipeRefreshLayout listSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.container_swipe_refresh_list);
		SwipeRefreshLayout emptySwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(android.R.id.empty);

		listSwipeRefreshLayout.setColorSchemeResources(R.color.global_color_primary, R.color.global_color_accent);
		listSwipeRefreshLayout.setOnRefreshListener(this);

		emptySwipeRefreshLayout.setColorSchemeResources(R.color.global_color_primary, R.color.global_color_accent);
		emptySwipeRefreshLayout.setOnRefreshListener(this);
	}
}
