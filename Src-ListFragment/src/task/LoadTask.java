package com.example.task;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listener.OnLoadListener;


public class LoadTask extends AsyncTask<Void, Void, String>
{
	private WeakReference<OnLoadListener> mOnLoadListener;
	
	
	public LoadTask(OnLoadListener onLoadListener)
	{
		setListener(onLoadListener);
	}
	
	
	public void setListener(OnLoadListener onLoadListener)
	{
		mOnLoadListener = new WeakReference<OnLoadListener>(onLoadListener);
	}
	
	
	@Override
	protected String doInBackground(Void... params)
	{
		try
		{
			// TODO: do something
			Thread.sleep(2000);
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
			listener.onLoad();
		}
	}
}
