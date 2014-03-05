package com.example.client.request;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;

import android.os.Bundle;

import com.example.ExampleConfig;
import com.example.client.response.Response;


public abstract class Request
{
	protected final String API_ENDPOINT = ExampleConfig.DEV_API ? ExampleConfig.API_ENDPOINT_DEVELOPMENT : ExampleConfig.API_ENDPOINT_PRODUCTION;
	protected final String CHARSET = "UTF-8";
	public final String BOUNDARY = "0xKhTmLbOuNdArY";

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
