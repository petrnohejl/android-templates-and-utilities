package com.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.R;
import com.example.entity.Product;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.task.TaskSherlockFragment;
import com.example.utility.NetworkManager;
import com.example.utility.ViewState;


public class SimpleFragment extends TaskSherlockFragment implements OnLoadDataListener
{
	private boolean mActionBarProgress = false;
	private ViewState.Visibility mViewState = null;
	private View mRootView;
	private LoadDataTask mLoadDataTask;

	private Product mProduct;
	
	
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
		mRootView = inflater.inflate(R.layout.fragment_simple, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		if(mViewState==null || mViewState==ViewState.Visibility.OFFLINE)
		{
			loadData();
		}
		else if(mViewState==ViewState.Visibility.CONTENT)
		{
			if(mProduct!=null) renderView();
			showContent();
		}
		else if(mViewState==ViewState.Visibility.PROGRESS)
		{
			showProgress();
		}
		else if(mViewState==ViewState.Visibility.EMPTY)
		{
			showEmpty();
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
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				// get data
				mProduct = new Product();
				mProduct.setName("Test Product");
				
				// hide progress and render view
				if(mProduct!=null)
				{
					renderView();
					showContent();
				}
				else showEmpty();
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
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}
	
	
	private void showContent()
	{
		// show content container
		FrameLayout containerContent = (FrameLayout) mRootView.findViewById(R.id.container_content);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		FrameLayout containerEmpty = (FrameLayout) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.VISIBLE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.CONTENT;
	}
	
	
	private void showProgress()
	{
		// show progress container
		FrameLayout containerContent = (FrameLayout) mRootView.findViewById(R.id.container_content);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		FrameLayout containerEmpty = (FrameLayout) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.VISIBLE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.PROGRESS;
	}
	
	
	private void showOffline()
	{
		// show offline container
		FrameLayout containerContent = (FrameLayout) mRootView.findViewById(R.id.container_content);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		FrameLayout containerEmpty = (FrameLayout) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.VISIBLE);
		containerEmpty.setVisibility(View.GONE);
		mViewState = ViewState.Visibility.OFFLINE;
	}
	
	
	private void showEmpty()
	{
		// show empty container
		FrameLayout containerContent = (FrameLayout) mRootView.findViewById(R.id.container_content);
		FrameLayout containerProgress = (FrameLayout) mRootView.findViewById(R.id.container_progress);
		FrameLayout containerOffline = (FrameLayout) mRootView.findViewById(R.id.container_offline);
		FrameLayout containerEmpty = (FrameLayout) mRootView.findViewById(R.id.container_empty);
		containerContent.setVisibility(View.GONE);
		containerProgress.setVisibility(View.GONE);
		containerOffline.setVisibility(View.GONE);
		containerEmpty.setVisibility(View.VISIBLE);
		mViewState = ViewState.Visibility.EMPTY;
	}
	
	
	private void renderView()
	{
		// reference
		TextView nameTextView = (TextView) mRootView.findViewById(R.id.fragment_simple_name);
		
		// content
		nameTextView.setText(mProduct.getName());
	}
}
