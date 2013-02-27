package com.example.task;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.listener.OnLoadDataListener;


public class LoadDataTask extends AsyncTask<Void, Void, String>
{
	private WeakReference<OnLoadDataListener> mOnLoadDataListener;
	
	
	public LoadDataTask(OnLoadDataListener onLoadDataListener)
	{
		setListener(onLoadDataListener);
	}
	
	
	public void setListener(OnLoadDataListener onLoadDataListener)
	{
		mOnLoadDataListener = new WeakReference<OnLoadDataListener>(onLoadDataListener);
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
		
		OnLoadDataListener listener = mOnLoadDataListener.get();
		if(listener != null)
		{
			listener.onLoadData();
		}
	}
}
