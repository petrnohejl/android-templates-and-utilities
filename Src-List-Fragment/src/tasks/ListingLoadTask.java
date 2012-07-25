package com.example.tasks;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listeners.OnListingLoadListener;

public class ListingLoadTask extends AsyncTask<Void, Void, String>
{
	WeakReference<OnListingLoadListener> mOnListingLoadListener;
	
	
	public ListingLoadTask(OnListingLoadListener onListingLoadListener)
	{
		setListener(onListingLoadListener);
	}
	
	
	public void setListener(OnListingLoadListener onListingLoadListener)
	{
		mOnListingLoadListener = new WeakReference<OnListingLoadListener>(onListingLoadListener);
	}
	
	
	@Override
	protected void onPreExecute()
	{
		OnListingLoadListener listener = mOnListingLoadListener.get();
		if(listener != null)
		{
			listener.onListingLoadPreExecute();
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
		
		OnListingLoadListener listener = mOnListingLoadListener.get();
		if(listener != null)
		{
			listener.onListingLoadPostExecute();
		}
	}
}
