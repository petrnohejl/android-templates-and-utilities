package com.example.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.R;
import com.example.adapter.ListingAdapter;
import com.example.client.entity.Message;
import com.example.listener.OnLoadLazyListener;
import com.example.listener.OnLoadListener;
import com.example.task.ListingLoadLazyTask;
import com.example.task.ListingLoadTask;
import com.example.task.TaskSherlockListFragment;
import com.example.utility.ViewState;


public class ListingFragment extends TaskSherlockListFragment implements OnLoadListener, OnLoadLazyListener
{
	private final int LAZY_LOADING_TAKE = 3;
	private final int LAZY_LOADING_OFFSET = 1;
	
	private View mRootView;
	private View mFooterView;
	private ViewState.Visibility mViewState = null;
	private ArrayList<Message> mMessages;
	private ListingAdapter mAdapter;
	private ListingLoadTask mLoadTask;
	private ListingLoadLazyTask mLoadLazyTask;
	private boolean mLazyLoading = false;
	
	
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
		
		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}
		
		// handle intent extras
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.layout_listing, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.Visibility.OFFLINE)
		{
			if(true) // TODO: isOnline?
			{
				loadData();
			}
			else
			{
				showOffline();
			}
		}
		else if(mViewState==ViewState.Visibility.CONTENT)
		{
			renderView();
			showList();
		}
		else if(mViewState==ViewState.Visibility.PROGRESS)
		{
			showProgress();
		}
		
		// lazy loading
		if(mLazyLoading) startLazyLoadData();
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
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		if(mLoadTask!=null) mLoadTask.cancel(true);
		if(mLoadLazyTask!=null) mLoadLazyTask.cancel(true);
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
		
		// TODO
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
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
	{
		// context menu
		super.onCreateContextMenu(menu, view, menuInfo);
		
		// TODO
	}
	 
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		// context menu behaviour
		return super.onContextItemSelected(item);
		
		// TODO
	}
	
	
	@Override
	public void onListItemClick(ListView listView, View clickedView, int position, long id)
	{
		// listview item onclick
		if(mAdapter!=null) mAdapter.setSelectedPosition(position);
		
		// TODO
	}
	
	
	@Override
	public void onLoadPreExecute()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				showProgress();
			}
		});
	}
	
	
	@Override
	public void onLoadPostExecute()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				// TODO: show data
				Message m1 = new Message();
				m1.setName("One");
				
				Message m2 = new Message();
				m2.setName("Two");
				
				Message m3 = new Message();
				m3.setName("Three");
				
				mMessages = new ArrayList<Message>();
				mMessages.add(m1);
				mMessages.add(m2);
				mMessages.add(m3);
				
				showList();
				renderView();
			}
		});
	}
	
	
	@Override
	public void onLoadLazyPreExecute()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				// start lazy loading
				startLazyLoadData();
			}
		});
	}
	
	
	@Override
	public void onLoadLazyPostExecute()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				// TODO: refresh data
				Message m1 = new Message();
				m1.setName("Next one");
				
				Message m2 = new Message();
				m2.setName("Next two");
				
				Message m3 = new Message();
				m3.setName("Next three");
				
				mMessages.add(m1);
				mMessages.add(m2);
				mMessages.add(m3);
				
				// notify adapter
				if(mAdapter!=null) mAdapter.notifyDataSetChanged();
				
				// stop lazy loading
				stopLazyLoadData(); 
			}
		});
	}
	
	
	private void handleSavedInstanceState(Bundle savedInstanceState)
	{
		// TODO
	}
	
	
	private void handleExtras(Bundle extras)
	{
		// TODO
	}
	
	
	private void loadData()
	{
		// run async task
		mLoadTask = new ListingLoadTask(this);
		mLoadTask.execute();
	}
	
	
	private void lazyLoadData()
	{
		// run async task
		mLoadLazyTask = new ListingLoadLazyTask(this);
		mLoadLazyTask.execute();
	}
	
	
	private void startLazyLoadData()
	{
		mLazyLoading = true;
		
		// show footer
		ListView listView = getListView();
		listView.addFooterView(mFooterView);
	}
	
	
	private void stopLazyLoadData()
	{
		// hide footer
		ListView listView = getListView();
		listView.removeFooterView(mFooterView);
		
		mLazyLoading = false;
	}
	
	
	private void showList()
	{
		// show list container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		FrameLayout containerList = (FrameLayout) mRootView.findViewById(R.id.container_list);
		LinearLayout containerProgress = (LinearLayout) mRootView.findViewById(R.id.container_progress);
		LinearLayout containerOffline = (LinearLayout) mRootView.findViewById(R.id.container_offline);
		containerList.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		mViewState = ViewState.Visibility.OFFLINE;
	}
	
	
	private void renderView()
	{
		// reference
		ListView listView = getListView();
		
		// listview content
		if(getListAdapter()==null)
		{
			// adapter
			mAdapter = new ListingAdapter(getActivity(), mMessages);
		}
		else
		{
			// refill adapter
			mAdapter.refill(getActivity(), mMessages);
		}
		
		// init footer, because addFooterView() must be called at least once before setListAdapter()
		mFooterView = getActivity().getLayoutInflater().inflate(R.layout.layout_listing_footer, null);
		listView.addFooterView(mFooterView);
		
		// set adapter
		setListAdapter(mAdapter);
		
		// hide footer
		listView.removeFooterView(mFooterView);
		
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
				if(totalItemCount-(firstVisibleItem+visibleItemCount) <= LAZY_LOADING_OFFSET && mMessages.size() % LAZY_LOADING_TAKE==0 && !mMessages.isEmpty())
				{
					if(!mLazyLoading) lazyLoadData();
				}
			}
		});
		
		// listview item long onclick
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				return true;
			}
		});
	}
}
