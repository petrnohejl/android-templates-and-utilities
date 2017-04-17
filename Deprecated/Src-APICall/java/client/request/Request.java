package com.example.client.request;

import android.os.Bundle;

import com.example.ExampleConfig;
import com.example.client.response.Response;

import org.codehaus.jackson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;


public abstract class Request
{
	public static final String REST_BASE_URL = ExampleConfig.REST_BASE_URL;
	public static final String CHARSET = "UTF-8";
	public static final String BOUNDARY = "0xKhTmLbOuNdArY";

	private Bundle mMetaData = null;


	public abstract String getRequestMethod();
	public abstract String getAddress();
	public abstract Response<?> parseResponse(InputStream stream) throws IOException, JsonParseException;


	public byte[] getContent()
	{
		return null;
	}


	public String getBasicAuthUsername()
	{
		return null;
	}


	public String getBasicAuthPassword()
	{
		return null;
	}


	public boolean isMultipart()
	{
		return false;
	}


	public Bundle getMetaData()
	{
		return mMetaData;
	}


	public void setMetaData(Bundle metaData)
	{
		mMetaData = metaData;
	}
}
