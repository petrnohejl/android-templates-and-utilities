package com.example.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.broadcast.ExampleBroadcast;
import com.example.utility.Logcat;


public class ExampleFragment extends Fragment
{
	private View mRootView;
	private IntentFilter mExampleIntentFilter;
	private BroadcastReceiver mExampleBroadcastReceiver;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// init receiver
		initReceiver();
	}


	@Override
	public void onResume()
	{
		super.onResume();
		
		// register receiver
		getActivity().registerReceiver(mExampleBroadcastReceiver, mExampleIntentFilter);
	}


	@Override
	public void onPause()
	{
		super.onPause();
		
		// unregister receiver
		getActivity().unregisterReceiver(mExampleBroadcastReceiver);
	}


	private void initReceiver()
	{
		// create intent filter
		mExampleIntentFilter = new IntentFilter();
		mExampleIntentFilter.addAction(ExampleBroadcast.ACTION_EXAMPLE);
		
		// create broadcast receiver
		mExampleBroadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				if(intent.getAction().equals(ExampleBroadcast.ACTION_EXAMPLE))
				{
					String arg = intent.getExtras().getString(ExampleBroadcast.EXTRA_ARG);
					
					Logcat.d(arg);
				}
			}
		};
	}
}
