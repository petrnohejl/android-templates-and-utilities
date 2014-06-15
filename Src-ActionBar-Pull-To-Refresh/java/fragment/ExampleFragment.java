package com.example.fragment;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.R;
import com.example.activity.ExampleActivity;
import com.example.client.APICallManager;
import com.example.client.request.ExampleRequest;
import com.example.task.TaskListFragment;
import com.example.utility.NetworkManager;


public class ExampleFragment extends TaskListFragment implements OnRefreshListener
{
	private boolean mActionBarProgress = false;
	private View mRootView;
	private APICallManager mAPICallManager = new APICallManager();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// pull to refresh
		PullToRefreshLayout pullToRefreshLayout = (PullToRefreshLayout) mRootView.findViewById(R.id.container_pull_to_refresh);
		ActionBarPullToRefresh.from(getActivity())
				.options(Options.create()
						.scrollDistance(0.5f)
						.refreshOnUp(false)
						.minimize(1 * 1000)
						.build())
				.allChildrenArePullable()
				.listener(this)
				.setup(pullToRefreshLayout);
		
		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}
	
	
	@Override
	public void onRefreshStarted(View view)
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
		if(NetworkManager.isOnline(getActivity()))
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
		PullToRefreshLayout pullToRefreshLayout = (PullToRefreshLayout) mRootView.findViewById(R.id.container_pull_to_refresh);
		pullToRefreshLayout.setRefreshing(visible);
		mActionBarProgress = visible;
	}
}
