package com.example.task;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;


public class TaskSherlockListFragment extends SherlockListFragment implements TaskManager
{
	private final Object mLock = new Object();
	private Boolean mReady = false;
	private List<Runnable> mPendingCallbacks = new LinkedList<Runnable>();


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		synchronized(mLock)
		{
			mReady = true;
			int pendingCallbacks = mPendingCallbacks.size();
			while(pendingCallbacks-- > 0)
			{
				Runnable runnable = mPendingCallbacks.remove(0);
				//Logcat.d("EXAMPLE", "callback run pending " + runnable.getClass().getSimpleName());
				runNow(runnable);
			}
		}
	}


	@Override
	public void onDetach()
	{
		super.onDetach();
		synchronized(mLock)
		{
			mReady = false;
		}
	}
	
	
	@Override
	public void runTaskCallback(Runnable runnable)
	{
		if(mReady) runNow(runnable);
		else addPending(runnable);
	}
	
	
	private void runNow(Runnable runnable)
	{
		//Logcat.d("EXAMPLE", "callback run now " + runnable.getClass().getSimpleName());
		getActivity().runOnUiThread(runnable);
	}


	private void addPending(Runnable runnable)
	{
		synchronized(mLock)
		{
			//Logcat.d("EXAMPLE", "callback add pending " + runnable.getClass().getSimpleName());
			mPendingCallbacks.add(runnable);
		}
	}
}
