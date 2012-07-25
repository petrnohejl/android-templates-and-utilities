package com.example.tasks;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listeners.OnListingLoadLazyListener;

public class ListingLoadLazyTask extends AsyncTask<Void, Void, String>
{
	WeakReference<OnListingLoadLazyListener> mOnListingLoadLazyListener;
	
	
	public ListingLoadLazyTask(OnListingLoadLazyListener onListingLoadLazyListener)
	{
		setListener(onListingLoadLazyListener);
	}
	
	
	public void setListener(OnListingLoadLazyListener onListingLoadLazyListener)
	{
		mOnListingLoadLazyListener = new WeakReference<OnListingLoadLazyListener>(onListingLoadLazyListener);
	}
	
	
	@Override
	protected void onPreExecute()
	{
		OnListingLoadLazyListener listener = mOnListingLoadLazyListener.get();
		if(listener != null)
		{
			listener.onListingLoadLazyPreExecute();
		}
	}
	
	
	@Override
	protected String doInBackground(Void... params)
	{
		try
		{
			// TODO: do something
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(String result)
	{
		if(isCancelled()) return;
		
		OnListingLoadLazyListener listener = mOnListingLoadLazyListener.get();
		if(listener != null)
		{
			listener.onListingLoadLazyPostExecute();
		}
	}
}
