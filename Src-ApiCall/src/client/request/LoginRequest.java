package com.example.client.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;

import com.example.client.parser.LoginParser;
import com.example.client.response.Response;
import com.example.utility.Logcat;


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
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		// params
		params.add(new BasicNameValuePair("accessToken", mFacebookAccessToken));
		String paramsString = URLEncodedUtils.format(params, CHARSET);

		// url
		builder.append(BASE_URL);
		builder.append(REQUEST_URL);
		if(paramsString !=null && !paramsString.equals(""))
		{
			builder.append("?");
			builder.append(paramsString);
		}

		Logcat.d(builder.toString());
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
