package com.example.task;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;


public class TaskListFragment extends ListFragment implements TaskManager
{
	private final Object mLock = new Object();
	private Boolean mReady = false;
	private List<Runnable> mPendingCallbacks = new LinkedList<Runnable>();


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
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


	public void executeTask(AsyncTask<Void, ?, ?> task)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// use AsyncTask.THREAD_POOL_EXECUTOR or AsyncTask.SERIAL_EXECUTOR
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			task.execute();
		}
	}
	
	
	private void runNow(Runnable runnable)
	{
		//Logcat.d("TaskListFragment.runNow(): " + runnable.getClass().getEnclosingMethod());
		getActivity().runOnUiThread(runnable);
	}


	private void addPending(Runnable runnable)
	{
		synchronized(mLock)
		{
			//Logcat.d("TaskListFragment.addPending(): " + runnable.getClass().getEnclosingMethod());
			mPendingCallbacks.add(runnable);
		}
	}
}
