package com.example.client.request;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;

import com.example.client.parser.LoginParser;
import com.example.client.response.Response;


public class LoginRequest extends Request
{
	private final String REQUEST_METHOD = "POST";
	private final String REQUEST_URL = "Login";
	
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
		builder.append(BASE_URL);
		builder.append(REQUEST_URL);
		builder.append("?accessToken=");
		builder.append(mFacebookAccessToken);
		return builder.toString();
	}


	@Override
	public byte[] getContent()
	{
//		StringBuilder builder = new StringBuilder();
//		builder.append("content");
//		
//		try
//		{
//			return builder.toString().getBytes("utf-8");
//		}
//		catch (UnsupportedEncodingException e)
//		{
//			e.printStackTrace();
//			return null;
//		}
		
		return null;
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
