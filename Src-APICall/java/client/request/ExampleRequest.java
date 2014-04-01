package com.example.client.request;

import com.example.client.parser.ExampleParser;
import com.example.client.response.Response;
import com.example.entity.ProductEntity;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
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
		StringBuilder builder = new StringBuilder();
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		// params
		params.add(new BasicNameValuePair("skip", Integer.toString(mSkip)));
		params.add(new BasicNameValuePair("take", Integer.toString(mTake)));
		String paramsString = URLEncodedUtils.format(params, CHARSET);

		// url
		builder.append(API_ENDPOINT);
		builder.append(REQUEST_PATH);
		if(paramsString!=null && !paramsString.equals(""))
		{
			builder.append("?");
			builder.append(paramsString);
		}

		return builder.toString();
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
