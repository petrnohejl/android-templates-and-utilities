package com.example.fragment;

import android.content.Context;
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
import com.example.task.LoadDataTask;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;


public class ListingFragment extends TaskFragment implements LoadDataTask.OnLoadDataListener
{
	private static final int LAZY_LOADING_TAKE = 16;
	private static final int LAZY_LOADING_OFFSET = 4;

	private boolean mLazyLoading = false;
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private View mFooterView;
	private ListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;
	private List<ProductEntity> mProductList = new ArrayList<>();


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
		mRootView = inflater.inflate(R.layout.fragment_listing, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// setup stateful layout
		setupStatefulLayout(savedInstanceState);

		// load data
		if(mProductList == null || mProductList.isEmpty()) loadData();

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
		if(mAdapter != null) mAdapter.stop();
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
		if(mLoadDataTask != null) mLoadDataTask.cancel(true);
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

		// stateful layout state
		if(mStatefulLayout != null) mStatefulLayout.saveInstanceState(outState);
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
		// action bar menu behavior
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
				if(mRootView == null) return; // view was destroyed

				// get data
				final int size = mProductList.size();
				for(int i = 0; i < LAZY_LOADING_TAKE; i++)
				{
					ProductEntity p = new ProductEntity();
					p.setName("Product " + (size + i));
					mProductList.add(p);
				}

				// show content
				mStatefulLayout.showContent();
				showLazyLoadingProgress(false);
			}
		});
	}


	private void loadData()
	{
		if(NetworkUtility.isOnline(getActivity()))
		{
			// show progress
			mStatefulLayout.showProgress();

			// run async task
			mLoadDataTask = new LoadDataTask(this);
			executeTask(mLoadDataTask);
		}
		else
		{
			mStatefulLayout.showOffline();
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


	private void setupView()
	{
		// reference
		ListView listView = getListView();
		ViewGroup emptyView = mRootView.findViewById(android.R.id.empty);

		// listview content
		if(listView.getAdapter() == null)
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
				if(totalItemCount - (firstVisibleItem + visibleItemCount) <= LAZY_LOADING_OFFSET && mProductList.size() % LAZY_LOADING_TAKE == 0 && !mProductList.isEmpty())
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


	private void setupStatefulLayout(Bundle savedInstanceState)
	{
		// reference
		mStatefulLayout = (StatefulLayout) mRootView;

		// state change listener
		mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener()
		{
			@Override
			public void onStateChange(View view, @StatefulLayout.State int state)
			{
				Logcat.d(String.valueOf(state));

				if(state == StatefulLayout.CONTENT)
				{
					ListView listView = getListView();
					if(mLazyLoading && listView.getAdapter() != null)
					{
						mAdapter.notifyDataSetChanged();
					}
					else
					{
						if(mProductList != null) setupView();
					}
				}
			}
		});

		// restore state
		mStatefulLayout.restoreInstanceState(savedInstanceState);
	}


	private ListView getListView()
	{
		return mRootView != null ? mRootView.findViewById(android.R.id.list) : null;
	}


	private int getListPosition(int globalPosition)
	{
		// list position without headers, should be used for getting data entities from collections
		int listPosition = globalPosition;
		if(getListView() != null) listPosition = globalPosition - getListView().getHeaderViewsCount();
		return listPosition;
	}
}
