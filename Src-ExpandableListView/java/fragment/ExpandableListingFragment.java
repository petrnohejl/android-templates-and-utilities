package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
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
import com.example.utility.NetworkUtility;
import com.example.view.ViewState;


public class ExpandableListingFragment extends TaskFragment implements OnLoadDataListener
{
	private ViewState mViewState = null;
	private View mRootView;
	private ExpandableListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;
	private List<String> mGroupList = new ArrayList<>();
	private List<List<ProductEntity>> mProductList = new ArrayList<>();
	
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
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
			if(mGroupList!=null && mProductList!=null) bindData();
			showContent();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}
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
					List<ProductEntity> group = new ArrayList<>();
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
					if(mGroupList!=null && mProductList!=null) bindData();
				}
				
				// hide progress
				showContent();
			}
		});
	}
	
	
	private void loadData()
	{
		if(NetworkUtility.isOnline(getActivity()))
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
	
	
	private void showContent()
	{
		// show list container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		ViewGroup containerContent = (ViewGroup) mRootView.findViewById(R.id.container_content);
		ViewGroup containerProgress = (ViewGroup) mRootView.findViewById(R.id.container_progress);
		ViewGroup containerOffline = (ViewGroup) mRootView.findViewById(R.id.container_offline);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.OFFLINE;
	}
	
	
	private void bindData()
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
	
	
	private void expand()
	{
		if(mAdapter!=null && mRootView!=null)
		{
			ExpandableListView listView = (ExpandableListView) mRootView.findViewById(android.R.id.list);
			int count =  mAdapter.getGroupCount();
			for(int i = 0; i <count ; i++) listView.expandGroup(i);
			mRootView.invalidate();
		}
	}
	
	
	private void collapse()
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
