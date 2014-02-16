package com.example.client;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.client.request.Request;
import com.example.client.response.Response;
import com.example.utility.Logcat;


public class ApiCallTask extends AsyncTask<Void, Void, Response>
{
	private ApiCall mApiCall;
	private WeakReference<ApiCallListener> mListener;

	
	public ApiCallTask(Request request, ApiCallListener listener)
	{
		mApiCall = new ApiCall(request, this);
		setListener(listener);
	}
	
	
	public void setListener(ApiCallListener listener)
	{
		mListener = new WeakReference<ApiCallListener>(listener);
	}

	
	public Request getRequest()
	{
		return mApiCall.getRequest();
	}
	
	
	public void kill()
	{
		mApiCall.kill();
	}

	
	@Override
	protected Response doInBackground(Void... params)
	{
		return mApiCall.execute();
	}
	
	
	@Override
	protected void onPostExecute(Response response)
	{
		if(isCancelled()) return;
		
		ApiCallListener listener = mListener.get();
		if(listener!=null)
		{
			if(response!=null)
			{
				listener.onApiCallRespond(this, mApiCall.getResponseStatus(), response);
			}
			else
			{
				listener.onApiCallFail(this, mApiCall.getResponseStatus(), mApiCall.getException());
			}
		}
	}
	
	
	@Override
	protected void onCancelled()
	{
		Logcat.d("ApiCallTask.onCancelled()");
	}
}
