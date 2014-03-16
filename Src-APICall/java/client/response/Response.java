package com.example.client.response;


public class Response<T>
{
	private T mResponseObject;
	private boolean mError = false;
	private String mErrorType = null;
	private String mErrorMessage = null;


	public Response()
	{
	}


	public T getResponseObject()
	{
		return mResponseObject;
	}


	public void setResponseObject(T responseObject)
	{
		mResponseObject = responseObject;
	}
	
	
	public boolean isError()
	{
		return mError;
	}


	public void setError(boolean error)
	{
		mError = error;
	}


	public String getErrorType()
	{
		return mErrorType;
	}


	public void setErrorType(String errorType)
	{
		mErrorType = errorType;
	}


	public String getErrorMessage()
	{
		return mErrorMessage;
	}


	public void setErrorMessage(String errorMessage)
	{
		mErrorMessage = errorMessage;
	}
}
