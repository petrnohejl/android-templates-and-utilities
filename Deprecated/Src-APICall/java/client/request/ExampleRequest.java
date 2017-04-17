package com.example.client.request;

import android.net.Uri;

import com.example.client.parser.ExampleParser;
import com.example.client.response.Response;
import com.example.entity.ProductEntity;

import org.codehaus.jackson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class ExampleRequest extends Request
{
	private static final String REQUEST_METHOD = "POST";
	private static final String REQUEST_PATH = "example";

	private int mSkip;
	private int mTake;


	public ExampleRequest(int skip, int take)
	{
		mSkip = skip;
		mTake = take;
	}


	@Override
	public String getRequestMethod()
	{
		return REQUEST_METHOD;
	}


	@Override
	public String getAddress()
	{
		Uri.Builder builder = new Uri.Builder();
		builder.encodedPath(REST_BASE_URL);
		builder.appendEncodedPath(REQUEST_PATH);
		builder.appendQueryParameter("skip", Integer.toString(mSkip));
		builder.appendQueryParameter("take", Integer.toString(mTake));
		return builder.build().toString();
	}


	@Override
	public Response<List<ProductEntity>> parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return ExampleParser.parse(stream);
	}


	@Override
	public byte[] getContent()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("content");

		try
		{
			return builder.toString().getBytes(CHARSET);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String getBasicAuthUsername()
	{
		return "myusername";
	}


	@Override
	public String getBasicAuthPassword()
	{
		return "mypassword";
	}
}
