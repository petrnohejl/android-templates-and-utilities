package com.example.database;

import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Build;

import com.example.database.query.Query;
import com.example.utility.Logcat;


public class QueryManager
{
	private LinkedList<DatabaseCall> mQueue = new LinkedList<DatabaseCall>();
	
	
	public QueryManager()
	{
	
	}
	
	
	public void executeQuery(Query query, OnDatabaseCallListener onDatabaseCallListener)
	{
		DatabaseCall databaseCall = new DatabaseCall(query, onDatabaseCallListener);
		mQueue.add(databaseCall);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// use AsyncTask.THREAD_POOL_EXECUTOR or AsyncTask.SERIAL_EXECUTOR
			databaseCall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			databaseCall.execute();
		}
	}
	
	
	public boolean finishQuery(DatabaseCall databaseCall)
	{
		return mQueue.remove(databaseCall);
	}
	
	
	public int getQueriesCount()
	{
		return mQueue.size();
	}
	
	
	public boolean hasRunningQuery(Class<?> cls)
	{
		String className = cls.getSimpleName();
		
		for(DatabaseCall call : mQueue)
		{
			String callName = call.getQuery().getClass().getSimpleName();
			if(className.equals(callName)) return true;
		}
		
		return false;
	}
	
	
	public void cancelAllQueries()
	{
		for(int i=mQueue.size()-1;i>=0;i--)
		{
			DatabaseCall call = mQueue.get(i);
			if(call!=null)
			{
				call.cancel(true);
				mQueue.remove(call);
			}
		}
	}
	
	
	public void printQueue()
	{
		for(DatabaseCall call : mQueue)
		{
			Logcat.d("QueryManager.printQueue(): " + (call==null ? "null" : (call.getQuery().getClass().getSimpleName() + " / " + call.getStatus().toString())));
		}
		
		if(mQueue.isEmpty()) Logcat.d("QueryManager.printQueue(): empty");
	}
}
