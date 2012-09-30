package com.example.client.request;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;

import android.os.Bundle;

import com.example.client.response.Response;


public abstract class Request
{	
	protected final String BASE_URL = "http://example.com/api/";
	protected final String CHARSET = "UTF-8";

	private Bundle mMetaData = null;
	
	public abstract String getRequestMethod();
	public abstract String getAddress();
	public abstract byte[] getContent();
	public abstract String getBasicAuthUsername();
	public abstract String getBasicAuthPassword();
	public abstract Response parseResponse(InputStream stream) throws IOException, JsonParseException;


	public Bundle getMetaData()
	{
		return mMetaData;
	}
	public void setMetaData(Bundle metaData)
	{
		mMetaData = metaData;
	}
}
