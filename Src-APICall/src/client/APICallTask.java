package com.example.client;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.example.client.request.Request;
import com.example.client.response.Response;
import com.example.utility.Logcat;


public class APICallTask extends AsyncTask<Void, Void, Response>
{
	private APICall mAPICall;
	private WeakReference<APICallListener> mListener;

	
	public APICallTask(Request request, APICallListener listener)
	{
		mAPICall = new APICall(request, this);
		setListener(listener);
	}
	
	
	public void setListener(APICallListener listener)
	{
		mListener = new WeakReference<APICallListener>(listener);
	}

	
	public Request getRequest()
	{
		return mAPICall.getRequest();
	}
	
	
	public void kill()
	{
		mAPICall.kill();
	}

	
	@Override
	protected Response doInBackground(Void... params)
	{
		return mAPICall.execute();
	}
	
	
	@Override
	protected void onPostExecute(Response response)
	{
		if(isCancelled()) return;
		
		APICallListener listener = mListener.get();
		if(listener!=null)
		{
			if(response!=null)
			{
				listener.onAPICallRespond(this, mAPICall.getResponseStatus(), response);
			}
			else
			{
				listener.onAPICallFail(this, mAPICall.getResponseStatus(), mAPICall.getException());
			}
		}
	}
	
	
	@Override
	protected void onCancelled()
	{
		Logcat.d("APICallTask.onCancelled()");
	}
}
