package com.example.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.R;
import com.example.adapter.ExpandableListingAdapter;
import com.example.entity.ProductEntity;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.task.TaskFragment;
import com.example.utility.NetworkManager;
import com.example.view.ViewState;


public class ExpandableListingFragment extends TaskFragment implements OnLoadDataListener
{
	private boolean mActionBarProgress = false;
	private ViewState mViewState = null;
	private View mRootView;
	private ExpandableListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;

	private ArrayList<String> mGroupList = new ArrayList<String>();
	private ArrayList<ArrayList<ProductEntity>> mProductList = new ArrayList<ArrayList<ProductEntity>>();
	
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_expandable_listing, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.OFFLINE)
		{
			loadData();
		}
		else if(mViewState==ViewState.CONTENT)
		{
			if(mGroupList!=null && mProductList!=null) renderView();
			showList();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}
		
		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		// stop adapter
		if(mAdapter!=null) mAdapter.stop();
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	
	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		mRootView = null;
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		if(mLoadDataTask!=null) mLoadDataTask.cancel(true);
	}
	
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);
		
		// TODO
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// action bar menu behaviour
		return super.onOptionsItemSelected(item);
		
		// TODO
	}
	
	
	@Override
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				// get data
				for(int i=0; i<10; i++)
				{
					ArrayList<ProductEntity> group = new ArrayList<ProductEntity>();
					for(int j=0; j<10; j++)
					{
						ProductEntity p = new ProductEntity();
						p.setName("Product " + i + "/" + j);
						group.add(p);
					}
					mProductList.add(group);
					mGroupList.add("Group " + i);
				}
				
				// render view
				if(mViewState==ViewState.CONTENT && mAdapter!=null)
				{
					mAdapter.notifyDataSetChanged();
				}
				else
				{
					if(mGroupList!=null && mProductList!=null) renderView();
				}
				
				// hide progress
				showList();
			}
		});
	}
	
	
	private void loadData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			// show progress
			showProgress();
			
			// run async task
			mLoadDataTask = new LoadDataTask(this);
			executeTask(mLoadDataTask);
		}
		else
		{
			showOffline();
		}
	}
	
	
	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
		((ActionBarActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}
	
	
	private void showList()
	{
		// show list container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_list);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_list);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		ViewGroup containerList = (ViewGroup) mRootView.findViewById(R.id.container_list);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.OFFLINE;
	}
	
	
	private void renderView()
	{
		// reference
		ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
		ViewGroup emptyView = (ViewGroup) mRootView.findViewById(android.R.id.empty);
		
		// listview content
		if(mAdapter==null)
		{
			// adapter
			mAdapter = new ExpandableListingAdapter(getActivity(), mGroupList, mProductList);
			
			// set adapter
			listView.setAdapter(mAdapter);
			
			// expand first group
			listView.expandGroup(0);
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mGroupList, mProductList);
			
			// set adapter
			listView.setAdapter(mAdapter);
		}
		
		// listview item onclick
		listView.setOnChildClickListener(new OnChildClickListener()
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id)
			{
				// listview item onclick
				if(mAdapter!=null) mAdapter.setSelectedPosition(groupPosition, childPosition);
				
				// TODO
				
				return true;
			}
		});
		
		// listview empty view
		listView.setEmptyView(emptyView);
	}
	
	
	public void expand()
	{
		if(mAdapter!=null && mRootView!=null)
		{
			ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
			int count =  mAdapter.getGroupCount();
			for(int i = 0; i <count ; i++) listView.expandGroup(i);
			mRootView.invalidate();
		}
	}
	
	
	public void collapse()
	{
		if(mAdapter!=null && mRootView!=null)
		{
			ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
			int count =  mAdapter.getGroupCount();
			for(int i = 0; i <count ; i++) listView.collapseGroup(i);
			mRootView.invalidate();
		}
	}
}
