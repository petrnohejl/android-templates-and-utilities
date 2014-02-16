package com.example.client;

import com.example.client.response.Response;


public interface ApiCallListener
{
	public void onApiCallRespond(ApiCallTask task, ResponseStatus status, Response response);
	public void onApiCallFail(ApiCallTask task, ResponseStatus status, Exception exception);
}
