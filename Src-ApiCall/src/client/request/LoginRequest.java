package com.example.client.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonParseException;

import com.example.client.parser.LoginParser;
import com.example.client.response.Response;


public class LoginRequest extends Request
{
	private final String REQUEST_METHOD = "POST";
	private final String REQUEST_URL = "Login";
	private final String CHARSET = "UTF-8";
	
	private String mFacebookAccessToken;
	

	public LoginRequest(String facebookAccessToken)
	{
		mFacebookAccessToken = facebookAccessToken;
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

		try
		{
			builder.append(BASE_URL);
			builder.append(REQUEST_URL);
			builder.append("?accessToken=");
			builder.append(URLEncoder.encode(mFacebookAccessToken, CHARSET));
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}

		return builder.toString();
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
		return null;
	}


	@Override
	public String getBasicAuthPassword()
	{
		return null;
	}


	@Override
	public Response parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return LoginParser.parse(stream);
	}
}
