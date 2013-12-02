package com.example.fragment;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.activity.ExampleActivity;
import com.example.task.TaskListFragment;


public class ExampleFragment extends TaskListFragment implements PullToRefreshAttacher.OnRefreshListener
{
	private View mRootView;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	private void renderView()
	{
		// reference
		PullToRefreshLayout pullToRefreshLayout = (PullToRefreshLayout) mRootView.findViewById(R.id.container_pull_to_refresh);
		
		// pull to refresh
		mPullToRefreshAttacher = ((ExampleActivity) getActivity()).getPullToRefreshAttacher();
		pullToRefreshLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);
	}
	
	
	@Override
	public void onRefreshStarted(View view)
	{
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					// TODO: do something
					Thread.sleep(2000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
				mPullToRefreshAttacher.setRefreshComplete();
			}
		}.execute();
	}
}
