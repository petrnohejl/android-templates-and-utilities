package com.example.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.client.request.Request;
import com.example.client.response.Response;


public class ApiCall extends AsyncTask<Void, Void, Response>
{
	private WeakReference<OnApiCallListener> mOnApiCallListener;
	private Request mRequest;
	private ResponseStatus mStatus = new ResponseStatus();
	private boolean mParseFail = false;

	
	public ApiCall(Request request, OnApiCallListener onApiCallListener)
	{
		mRequest = request;
		setListener(onApiCallListener);
	}
	
	
	public void setListener(OnApiCallListener onApiCallListener)
	{
		mOnApiCallListener = new WeakReference<OnApiCallListener>(onApiCallListener);
	}

	
	public Request getRequest()
	{
		return mRequest;
	}

	
	@Override
	protected Response doInBackground(Void... params)
	{
		HttpURLConnection connection = null;
		OutputStream requestStream = null;
		InputStream responseStream = null;
		
		try
		{
			// disables Keep-Alive for all connections
			if(isCancelled()) return null;
			System.setProperty("http.keepAlive", "false");

			// new connection
			byte[] requestData = mRequest.getContent();
			URL url = new URL(mRequest.getAddress());	
			connection = (HttpURLConnection) url.openConnection();

			// connection properties
			if(mRequest.getRequestMethod()!=null)
			{
				connection.setRequestMethod(mRequest.getRequestMethod()); // GET, POST, OPTIONS, HEAD, PUT, DELETE, TRACE
			}
			if(mRequest.getBasicAuthUsername()!=null && mRequest.getBasicAuthPassword()!=null)
			{
				connection.setRequestProperty("Authorization", getBasicAuthToken(mRequest.getBasicAuthUsername(), mRequest.getBasicAuthPassword()));
			}
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//connection.setRequestProperty("Content-Length", String.valueOf(requestData.length));
			connection.setRequestProperty("Accept-Encoding", "gzip");
			connection.setConnectTimeout(30000);
			//connection.setReadTimeout(30000);
			connection.setDoOutput(requestData!=null); 
			connection.setDoInput(true); 
			connection.setUseCaches(false);
			connection.connect();

			// send request
			if(isCancelled()) return null;
			if(requestData!=null)
			{
				//connection.setChunkedStreamingMode(0); 
				connection.setFixedLengthStreamingMode(requestData.length);
				requestStream = new BufferedOutputStream(connection.getOutputStream());
				requestStream.write(requestData);
			}
			
			// receive response
			if(isCancelled()) return null;
			responseStream = new BufferedInputStream(connection.getInputStream());
			//errorStream = new BufferedInputStream(connection.getErrorStream());
			
			// response info
			//Log.d("EXAMPLE", "apicall.getContentType: " + connection.getContentType());
			//Log.d("EXAMPLE", "apicall.getResponseCode: " + connection.getResponseCode());
			//Log.d("EXAMPLE", "apicall.getResponseMessage: " + connection.getResponseMessage());
			
			// parse response
			if(isCancelled()) return null;
			Response response = null;
			try
			{
				response = mRequest.parseResponse(responseStream);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(response==null) mParseFail = true;

			if(isCancelled()) return null;
			return response;
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(SocketException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				if(requestStream != null) requestStream.close();
			}
			catch(IOException e) {}

			try
			{
				if(responseStream != null) responseStream.close();
			}
			catch(IOException e) {}

			try
			{
				// set status
				if(connection!=null)
				{
					mStatus.setStatusCode(connection.getResponseCode());
					mStatus.setStatusMessage(connection.getResponseMessage());
					connection.disconnect();
				}
			}
			catch(Throwable e) {}

			requestStream = null;
			responseStream = null;
			connection = null;
		}
	}
	
	
	@Override
	protected void onPostExecute(Response response)
	{
		if(isCancelled()) return;
		
		OnApiCallListener listener = mOnApiCallListener.get();
		if(listener != null)
		{
			if(response != null)
			{
				listener.onApiCallRespond(this, mStatus, response);
			}
			else
			{
				listener.onApiCallFail(this, mStatus, mParseFail);
			}
		}
	}
	
	
	@Override
	protected void onCancelled()
	{
		//Log.d("EXAMPLE", "apicall.onCancelled");
	}
	
	
	private String getBasicAuthToken(String username, String password)
	{
		String base64 = Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
		return "Basic " + base64;
	}
}
