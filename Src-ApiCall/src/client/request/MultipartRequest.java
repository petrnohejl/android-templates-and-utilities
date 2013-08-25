package com.example.client.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.codehaus.jackson.JsonParseException;

import com.example.client.parser.MultipartParser;
import com.example.client.response.Response;


public class MultipartRequest extends Request
{
	private final String REQUEST_METHOD = "POST";
	private final String REQUEST_URL = "Multipart";

	private String mTextData1;
	private String mTextData2;
	private byte[] mBinaryData1;
	private byte[] mBinaryData2;


	public MultipartRequest(String textData1, String textData2, byte[] binaryData1, byte[] binaryData2)
	{
		mTextData1 = textData1;
		mTextData2 = textData2;
		mBinaryData1 = binaryData1;
		mBinaryData2 = binaryData2;
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

		// url
		builder.append(API_ENDPOINT);
		builder.append(REQUEST_URL);

		return builder.toString();
	}


	@Override
	public byte[] getContent()
	{
		StringBuilder plainBuilder = new StringBuilder();
		
		plainBuilder.append("--" + BOUNDARY + "\r\n");
		plainBuilder.append("Content-Disposition: form-data; name=\"text1\"\r\n");
		plainBuilder.append("Content-Type: text/plain; charset=utf-8\r\n");
		plainBuilder.append("\r\n");
		plainBuilder.append(mTextData1);
		plainBuilder.append("\r\n");
		
		plainBuilder.append("--" + BOUNDARY + "\r\n");
		plainBuilder.append("Content-Disposition: form-data; name=\"text2\"\r\n");
		plainBuilder.append("Content-Type: text/plain; charset=utf-8\r\n");
		plainBuilder.append("\r\n");
		plainBuilder.append(mTextData2);
		plainBuilder.append("\r\n");
		
		StringBuilder binary1Builder = new StringBuilder();
		binary1Builder.append("--" + BOUNDARY + "\r\n");
		binary1Builder.append("Content-Disposition: form-data; name=\"binary1\"; filename=\"file.data\"\r\n");
		binary1Builder.append("Content-Type: application/octet-stream\r\n");
		binary1Builder.append("\r\n");
		
		StringBuilder binary2Builder = new StringBuilder();
		binary2Builder.append("--" + BOUNDARY + "\r\n");
		binary2Builder.append("Content-Disposition: form-data; name=\"binary2\"; filename=\"file.data\"\r\n");
		binary2Builder.append("Content-Type: application/octet-stream\r\n");
		binary2Builder.append("\r\n");
		
		try
		{
			byte[] plainBytes = plainBuilder.toString().getBytes("UTF-8");
			byte[] binary1Bytes = binary1Builder.toString().getBytes("UTF-8");
			byte[] binary2Bytes = binary2Builder.toString().getBytes("UTF-8");
			byte[] breakBytes = "\r\n".getBytes("UTF-8");
			byte[] endBytes = ("--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
			
			// byte stream
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			stream.write(plainBytes);
			if(mBinaryData1.length > 0)
			{
				stream.write(binary1Bytes);
				stream.write(mBinaryData1);
				stream.write(breakBytes);
			}
			if(mBinaryData2.length > 0)
			{
				stream.write(binary2Bytes);
				stream.write(mBinaryData2);
				stream.write(breakBytes);
			}
			stream.write(endBytes);
			
			return stream.toByteArray();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	// TODO: implement isMultipart() method in all requests and set proper content type in ApiCall
	@Override
	public boolean isMultipart()
	{
		return true;
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
		return MultipartParser.parse(stream);
	}
}
