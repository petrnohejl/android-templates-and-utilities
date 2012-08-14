package com.example.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.example.R;
import com.example.client.ApiCall;
import com.example.client.OnApiCallListener;
import com.example.client.RequestManager;
import com.example.client.ResponseStatus;
import com.example.client.request.LoginRequest;
import com.example.client.response.LoginResponse;
import com.example.client.response.Response;


public class ExampleFragment extends SherlockListFragment  implements OnApiCallListener
{
	private View mRootView;
	private RequestManager mRequestManager = new RequestManager();

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.layout_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		// load and show data
		loadData();
	}
	
	
	@Override
	public void onPause()
	{
		// cancel async tasks
		mRequestManager.cancelAllRequests();
		
		super.onPause();
	}


	@Override
	public void onApiCallRespond(ApiCall call, ResponseStatus status, Response response)
	{
		if(response.getClass().getSimpleName().equalsIgnoreCase("LoginResponse"))
		{
			LoginResponse loginResponse = (LoginResponse) response;
			
			// error
			if(loginResponse.isError())
			{
				Log.d("EXAMPLE", "onApiCallRespond: login response error");
				Log.d("EXAMPLE", "onApiCallRespond status code: " + status.getStatusCode());
				Log.d("EXAMPLE", "onApiCallRespond status message: " + status.getStatusMessage());
				Log.d("EXAMPLE", "onApiCallRespond error: " + loginResponse.getErrorType() + ": " + loginResponse.getErrorMessage());
			}
			
			// response
			else
			{
				Log.d("EXAMPLE", "onApiCallRespond: login response ok");
				Log.d("EXAMPLE", "onApiCallRespond status code: " + status.getStatusCode());
				Log.d("EXAMPLE", "onApiCallRespond status message: " + status.getStatusMessage());
				
				// TODO: render view
			}
		}
		
		boolean finished = mRequestManager.finishRequest(call);
		Log.d("EXAMPLE", "finishRequest: " + finished);
	}


	@Override
	public void onApiCallFail(ApiCall call, ResponseStatus status, boolean parseFail)
	{
		if(call.getRequest().getClass().getSimpleName().equalsIgnoreCase("LoginRequest"))
		{
			Log.d("EXAMPLE", "onApiCallFail: login request fail");
			Log.d("EXAMPLE", "onApiCallFail status code: " + status.getStatusCode());
			Log.d("EXAMPLE", "onApiCallFail status message: " + status.getStatusMessage());
			Log.d("EXAMPLE", "onApiCallFail parse fail: " + parseFail);
		}
		
		boolean finished = mRequestManager.finishRequest(call);
		Log.d("EXAMPLE", "finishRequest: " + finished);
	}
	
	
	private void loadData()
	{
		// execute request
		LoginRequest request = new LoginRequest("example");
		mRequestManager.executeRequest(request, this);
	}
}
