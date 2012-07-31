package com.example.tasks;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listeners.OnLoadLazyListener;

public class ListingLoadLazyTask extends AsyncTask<Void, Void, String>
{
	WeakReference<OnLoadLazyListener> mOnLoadLazyListener;
	
	
	public ListingLoadLazyTask(OnLoadLazyListener onLoadLazyListener)
	{
		setListener(onLoadLazyListener);
	}
	
	
	public void setListener(OnLoadLazyListener onLoadLazyListener)
	{
		mOnLoadLazyListener = new WeakReference<OnLoadLazyListener>(onLoadLazyListener);
	}
	
	
	@Override
	protected void onPreExecute()
	{
		OnLoadLazyListener listener = mOnLoadLazyListener.get();
		if(listener != null)
		{
			listener.onLoadLazyPreExecute();
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
		
		OnLoadLazyListener listener = mOnLoadLazyListener.get();
		if(listener != null)
		{
			listener.onLoadLazyPostExecute();
		}
	}
}
