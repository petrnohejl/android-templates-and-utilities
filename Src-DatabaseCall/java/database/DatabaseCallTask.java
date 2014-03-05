package com.example.database;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.database.data.Data;
import com.example.database.query.Query;
import com.example.utility.Logcat;


public class DatabaseCallTask extends AsyncTask<Void, Void, Data>
{
	private Query mQuery;
	private WeakReference<DatabaseCallListener> mListener;
	private Exception mException = null;
	
	
	public DatabaseCallTask(Query query, DatabaseCallListener listener)
	{
		mQuery = query;
		setListener(listener);
	}
	
	
	public void setListener(DatabaseCallListener listener)
	{
		mListener = new WeakReference<DatabaseCallListener>(listener);
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
		
		DatabaseCallListener listener = mListener.get();
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
		Logcat.d("DatabaseCallTask.onCancelled()");
	}
}
