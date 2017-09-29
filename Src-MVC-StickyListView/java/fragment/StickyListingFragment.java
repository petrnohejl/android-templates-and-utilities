package com.example.fragment;

import android.content.Context;
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
import com.example.task.LoadDataTask;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class StickyListingFragment extends TaskFragment implements
		LoadDataTask.OnLoadDataListener,
		AdapterView.OnItemClickListener,
		AdapterView.OnItemLongClickListener,
		StickyListHeadersListView.OnHeaderClickListener
{
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private StickyListingAdapter mAdapter;
	private LoadDataTask mLoadDataTask;
	private int mListviewPosition = 0;
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
		mRootView = inflater.inflate(R.layout.fragment_sticky_listing, container, false);
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

		// listview position
		if(mRootView != null && mAdapter != null)
		{
			StickyListHeadersListView stickyListView = mRootView.findViewById(android.R.id.list);
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
		// action bar menu behavior
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
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				// get data
				for(int i = 0; i < 8; i++)
				{
					GroupEntity g = new GroupEntity();
					g.setId(Integer.toString(i));
					g.setName("Group " + i);

					for(int j = 0; j < 4; j++)
					{
						ProductEntity p = new ProductEntity();
						p.setName("Product " + j);
						p.setGroup(g);
						mProductList.add(p);
					}
				}

				// show content
				mStatefulLayout.showContent();
			}
		});
	}


	@Override
	public void onHeaderClick(StickyListHeadersListView listView, View headerView, int itemPosition, long headerId, boolean currentlySticky)
	{
		// TODO
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


	private void setupView()
	{
		// reference
		StickyListHeadersListView stickyListView = mRootView.findViewById(android.R.id.list);
		ViewGroup emptyView = mRootView.findViewById(android.R.id.empty);

		// listview content
		if(mAdapter == null)
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
					StickyListHeadersListView stickyListView = mRootView.findViewById(android.R.id.list);
					if(stickyListView.getAdapter() != null)
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
}
