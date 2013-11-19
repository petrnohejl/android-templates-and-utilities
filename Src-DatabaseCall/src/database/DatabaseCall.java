package com.example.database;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.database.data.Data;
import com.example.database.query.Query;


public class DatabaseCall extends AsyncTask<Void, Void, Data>
{
	private WeakReference<OnDatabaseCallListener> mOnDatabaseCallListener;
	private Query mQuery;
	private Exception mException = null;
	
	
	public DatabaseCall(Query query, OnDatabaseCallListener onDatabaseCallListener)
	{
		mQuery = query;
		setListener(onDatabaseCallListener);
	}
	
	
	public void setListener(OnDatabaseCallListener onDatabaseCallListener)
	{
		mOnDatabaseCallListener = new WeakReference<OnDatabaseCallListener>(onDatabaseCallListener);
	}
	
	
	public Query getQuery()
	{
		return mQuery;
	}
	
	
	@Override
	protected Data doInBackground(Void... params)
	{
		try
		{
			Data data = mQuery.queryData();
			
			if(isCancelled()) return null;
			else return data;
		}
		catch(Exception e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	protected void onPostExecute(Data data)
	{
		if(isCancelled()) return;
		
		OnDatabaseCallListener listener = mOnDatabaseCallListener.get();
		if(listener != null)
		{
			if(data != null)
			{
				listener.onDatabaseCallRespond(this, data);
			}
			else
			{
				listener.onDatabaseCallFail(this, mException);
			}
		}
	}
	
	
	@Override
	protected void onCancelled()
	{
		//Logcat.d("DatabaseCall.onCancelled()");
	}
}
