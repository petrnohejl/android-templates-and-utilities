package com.example.fragment;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.R;
import com.example.adapter.StickyListingAdapter;
import com.example.entity.GroupEntity;
import com.example.entity.ProductEntity;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.task.TaskFragment;
import com.example.utility.NetworkManager;
import com.example.view.ViewState;


public class StickyListingFragment extends TaskFragment implements
		OnLoadDataListener,
		AdapterView.OnItemClickListener,
		AdapterView.OnItemLongClickListener,
		StickyListHeadersListView.OnHeaderClickListener
{
	private ViewState mViewState = null;
	private View mRootView;
	private StickyListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;
	private int mListviewPosition = 0;
	
	private ArrayList<ProductEntity> mProductList = new ArrayList<ProductEntity>();
	
	
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
		mRootView = inflater.inflate(R.layout.fragment_sticky_listing, container, false);
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
			if(mProductList!=null) renderView();
			showList();
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
		
		// listview position
		if(mRootView!=null && mAdapter!=null)
		{
			StickyListHeadersListView stickyListView = (StickyListHeadersListView) mRootView.findViewById(android.R.id.list);
			mListviewPosition = stickyListView.getFirstVisiblePosition();
		}
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
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long id)
	{
		// listview item onclick

		// TODO
	}
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View clickedView, int position, long id)
	{
		// TODO
		
		return true;
	}


	@Override
	public void onHeaderClick(StickyListHeadersListView listView, View headerView, int itemPosition, long headerId, boolean currentlySticky)
	{
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
				for(int i=0; i<8; i++)
				{
					GroupEntity g = new GroupEntity();
					g.setId(Integer.toString(i));
					g.setName("Group " + i);
					
					for(int j=0; j<4; j++)
					{
						ProductEntity p = new ProductEntity();
						p.setName("Product " + j);
						p.setGroup(g);
						mProductList.add(p);
					}
				}
				
				// render view
				if(mViewState==ViewState.CONTENT && mAdapter!=null)
				{
					mAdapter.notifyDataSetChanged();
				}
				else
				{
					if(mProductList!=null) renderView();
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
		StickyListHeadersListView stickyListView = (StickyListHeadersListView) mRootView.findViewById(android.R.id.list);
		ViewGroup emptyView = (ViewGroup) mRootView.findViewById(android.R.id.empty);
		
		// listview content
		if(mAdapter==null)
		{
			// create adapter
			mAdapter = new StickyListingAdapter(getActivity(), mProductList);
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mProductList);
		}
		
		// listview parameters
		stickyListView.setOnItemClickListener(this);
		//stickyListView.setOnItemLongClickListener(this);
		stickyListView.setOnHeaderClickListener(this);
		stickyListView.setEmptyView(emptyView);
		stickyListView.setDrawingListUnderStickyHeader(true);
		stickyListView.setAreHeadersSticky(true);
		
		// set adapter
		stickyListView.setAdapter(mAdapter);
		
		// listview position
		stickyListView.setSelectionFromTop(mListviewPosition, 0);
	}
}
