package com.example.client;

import com.example.client.response.Response;


public interface OnApiCallListener
{
	public void onApiCallRespond(ApiCall call, ResponseStatus status, Response response);
	public void onApiCallFail(ApiCall call, ResponseStatus status, boolean parseFail);
}
