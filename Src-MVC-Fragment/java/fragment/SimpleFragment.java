package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.entity.ProductEntity;
import com.example.task.LoadDataTask;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;


public class SimpleFragment extends TaskFragment implements LoadDataTask.OnLoadDataListener
{
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private LoadDataTask mLoadDataTask;
	private ProductEntity mProduct;


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
		mRootView = inflater.inflate(R.layout.fragment_simple, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// setup stateful layout
		setupStatefulLayout(savedInstanceState);

		// load data
		if(mProduct == null) loadData();
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
				mProduct = new ProductEntity();
				mProduct.setName("Test Product");

				// show content
				if(mProduct != null) mStatefulLayout.showContent();
				else mStatefulLayout.showEmpty();
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


	private void setupView()
	{
		// reference
		TextView nameTextView = mRootView.findViewById(R.id.fragment_simple_name);

		// content
		nameTextView.setText(mProduct.getName());
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
					if(mProduct != null) setupView();
				}
			}
		});

		// restore state
		mStatefulLayout.restoreInstanceState(savedInstanceState);
	}
}
