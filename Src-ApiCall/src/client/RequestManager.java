package com.example.client;

import java.util.LinkedList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.client.request.Request;


public class RequestManager
{
	private LinkedList<ApiCall> mQueue = new LinkedList<ApiCall>();
	
	
	public RequestManager()
	{

	}
	

	public void executeRequest(Request request, OnApiCallListener onApiCallListener)
	{
		ApiCall apiCall = new ApiCall(request, onApiCallListener);
		mQueue.add(apiCall);
		apiCall.execute();
	}
	
	
	public boolean finishRequest(ApiCall apiCall)
	{
		return mQueue.remove(apiCall);
	}
	
	
	public void cancelAllRequests()
	{
		for(int i=mQueue.size()-1;i>=0;i--)
		{
			ApiCall call = mQueue.get(i);
			if(call!=null)
			{
				call.cancel(true);
				mQueue.remove(call);
			}
		}
	}
	
	
	public void printQueue()
	{
		for(ApiCall call : mQueue)
		{
			Log.d("EXAMPLE", "request in queue: " + (call==null ? "null" : (call.getRequest().getClass().getSimpleName() + ": " + call.getStatus().toString())));
		}
		
		if(mQueue.isEmpty()) Log.d("EXAMPLE", "request in queue: empty");
	}
	
	
	public static boolean isOnline(Context context)
	{
		// needs android.permission.ACCESS_NETWORK_STATE
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return (netInfo != null && netInfo.isAvailable() && netInfo.isConnected());
	}
}
