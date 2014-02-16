package com.example.client;

import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Build;

import com.example.client.request.Request;
import com.example.utility.Logcat;


public class ApiCallManager
{
	private LinkedList<ApiCallTask> mTaskList = new LinkedList<ApiCallTask>();
	
	
	public ApiCallManager()
	{

	}


	public void executeTask(Request request, ApiCallListener listener)
	{
		ApiCallTask task = new ApiCallTask(request, listener);
		mTaskList.add(task);
		
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
	
	
	public boolean finishTask(ApiCallTask task)
	{
		return mTaskList.remove(task);
	}


	public int getTasksCount()
	{
		return mTaskList.size();
	}
	
	
	public boolean hasRunningTask(Class<?> requestClass)
	{
		String className = requestClass.getSimpleName();
		
		for(ApiCallTask task : mTaskList)
		{
			String taskName = task.getRequest().getClass().getSimpleName();
			if(className.equals(taskName)) return true;
		}
		
		return false;
	}
	
	
	public void cancelAllTasks()
	{
		for(int i=mTaskList.size()-1;i>=0;i--)
		{
			ApiCallTask task = mTaskList.get(i);
			if(task!=null)
			{
				task.cancel(true);
				mTaskList.remove(task);
			}
		}
	}
	
	
	public void killAllTasks()
	{
		for(int i=mTaskList.size()-1;i>=0;i--)
		{
			ApiCallTask task = mTaskList.get(i);
			if(task!=null)
			{
				task.kill();
				task.cancel(true);
				mTaskList.remove(task);
			}
		}
	}
	
	
	public void printRunningTasks()
	{
		for(ApiCallTask task : mTaskList)
		{
			Logcat.d("ApiCallManager.printRunningTasks(): " + (task==null ? "null" : (task.getRequest().getClass().getSimpleName() + " / " + task.getStatus().toString())));
		}
		
		if(mTaskList.isEmpty()) Logcat.d("ApiCallManager.printRunningTasks(): empty");
	}
}
