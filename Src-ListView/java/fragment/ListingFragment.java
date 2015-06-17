package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.R;
import com.example.adapter.ListingAdapter;
import com.example.entity.ProductEntity;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.utility.NetworkUtility;
import com.example.view.ViewState;


public class ListingFragment extends TaskFragment implements OnLoadDataListener
{
	private static final int LAZY_LOADING_TAKE = 16;
	private static final int LAZY_LOADING_OFFSET = 4;
	
	private boolean mLazyLoading = false;
	private ViewState mViewState = null;
	private View mRootView;
	private View mFooterView;
	private ListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;

	private List<ProductEntity> mProductList = new ArrayList<>();
	
	
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
		mRootView = inflater.inflate(R.layout.fragment_listing, container, false);
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
			showContent();
		}
		else if(mViewState==ViewState.PROGRESS)
		{
			showProgress();
		}
		
		// lazy loading progress
		if(mLazyLoading) showLazyLoadingProgress(true);
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
				final int size = mProductList.size();
				for(int i=0; i<LAZY_LOADING_TAKE; i++)
				{
					ProductEntity p = new ProductEntity();
					p.setName("Product " + (size + i));
					mProductList.add(p);
				}
				
				// render view
				if(mLazyLoading && mViewState==ViewState.CONTENT && mAdapter!=null)
				{
					mAdapter.notifyDataSetChanged();
				}
				else
				{
					if(mProductList!=null) renderView();
				}

				// hide progress
				showLazyLoadingProgress(false);
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
	
	
	private void lazyLoadData()
	{
		if(NetworkUtility.isOnline(getActivity()))
		{
			// show lazy loading progress
			showLazyLoadingProgress(true);
			
			// run async task
			mLoadDataTask = new LoadDataTask(this);
			executeTask(mLoadDataTask);
		}
	}
	
	
	private void showLazyLoadingProgress(boolean visible)
	{
		if(visible)
		{
			mLazyLoading = true;
		
			// show footer
			ListView listView = getListView();
			listView.addFooterView(mFooterView);
		}
		else
		{
			// hide footer
			ListView listView = getListView();
			listView.removeFooterView(mFooterView);
			
			mLazyLoading = false;
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
	
	
	private void renderView()
	{
		// reference
		ListView listView = getListView();
		ViewGroup emptyView = (ViewGroup) mRootView.findViewById(android.R.id.empty);
		
		// listview content
		if(listView.getAdapter()==null)
		{
			// create adapter
			mAdapter = new ListingAdapter(getActivity(), mProductList);
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mProductList);
		}

		// add header
		//setListAdapter(null);
		//if(listView.getHeaderViewsCount()==0)
		//{
		//	mHeaderView = getActivity().getLayoutInflater().inflate(R.layout.fragment_listing_header, listView, false);
		//	listView.addHeaderView(mHeaderView);
		//}
		
		// init footer, because addFooterView() must be called at least once before setListAdapter()
		mFooterView = getActivity().getLayoutInflater().inflate(R.layout.fragment_listing_footer, listView, false);
		listView.addFooterView(mFooterView);
		
		// set adapter
		listView.setAdapter(mAdapter);
		
		// hide footer
		listView.removeFooterView(mFooterView);

		// listview empty view
		listView.setEmptyView(emptyView);

		// lazy loading
		listView.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				if(totalItemCount-(firstVisibleItem+visibleItemCount) <= LAZY_LOADING_OFFSET && mProductList.size() % LAZY_LOADING_TAKE==0 && !mProductList.isEmpty())
				{
					if(!mLazyLoading) lazyLoadData();
				}
			}
		});

		// listview item onclick
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// list position
				int listPosition = getListPosition(position);

				// TODO
			}
		});

		// listview item long onclick
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				// list position
				int listPosition = getListPosition(position);

				// TODO

				return true;
			}
		});
	}


	private ListView getListView()
	{
		return mRootView!=null ? (ListView) mRootView.findViewById(android.R.id.list) : null;
	}


	private int getListPosition(int globalPosition)
	{
		// list position without headers, should be used for getting data entities from collections
		int listPosition = globalPosition;
		if(getListView()!=null) listPosition = globalPosition - getListView().getHeaderViewsCount();
		return listPosition;
	}
}
