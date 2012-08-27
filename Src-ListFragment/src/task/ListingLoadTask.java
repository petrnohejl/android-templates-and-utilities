package com.example.task;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listener.OnLoadListener;


public class ListingLoadTask extends AsyncTask<Void, Void, String>
{
	private WeakReference<OnLoadListener> mOnLoadListener;
	
	
	public ListingLoadTask(OnLoadListener onLoadListener)
	{
		setListener(onLoadListener);
	}
	
	
	public void setListener(OnLoadListener onLoadListener)
	{
		mOnLoadListener = new WeakReference<OnLoadListener>(onLoadListener);
	}
	
	
	@Override
	protected void onPreExecute()
	{
		OnLoadListener listener = mOnLoadListener.get();
		if(listener != null)
		{
			listener.onLoadPreExecute();
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
		
		OnLoadListener listener = mOnLoadListener.get();
		if(listener != null)
		{
			listener.onLoadPostExecute();
		}
	}
}
