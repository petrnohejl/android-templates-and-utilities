package com.example.client.response;


public class Response
{
	protected boolean mError = false;
	protected String mErrorType = null;
	protected String mErrorMessage = null;
	
	
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
