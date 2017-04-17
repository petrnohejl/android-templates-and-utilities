package com.example.client;


public class ResponseStatus
{
	// HTTP status codes are defined in HttpURLConnection
	private int mStatusCode = -1;
	private String mStatusMessage = "Unknown";


	public ResponseStatus()
	{
	}


	public int getStatusCode()
	{
		return mStatusCode;
	}


	public void setStatusCode(int statusCode)
	{
		mStatusCode = statusCode;
	}


	public String getStatusMessage()
	{
		return mStatusMessage;
	}


	public void setStatusMessage(String statusMessage)
	{
		mStatusMessage = statusMessage;
	}
}
