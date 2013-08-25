package com.example.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

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
		//HttpsURLConnection connection = null; // for SSL
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
			//connection = (HttpsURLConnection) url.openConnection(); // for SSL
			
			// ssl connection properties
			//SelfSignedSslUtility.setSslConnection(connection, url); // for SSL using self signed certificate
			//CertificateAuthoritySslUtility.setSslConnection(connection, url); // for SSL using certificate authority
			
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
			//connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mRequest.BOUNDARY); // for multipart
			connection.setRequestProperty("Accept-Encoding", "gzip");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			//connection.setRequestProperty("Content-Length", requestData == null ? "0" : String.valueOf(requestData.length));
			//if(requestData!=null) connection.setChunkedStreamingMode(0);
			if(requestData!=null) connection.setFixedLengthStreamingMode(requestData.length);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			if(requestData!=null)
			{
				// this call automatically sets request method to POST on Android 4
				// if you don't want your app to POST, you must not call setDoOutput
				// http://webdiary.com/2011/12/14/ics-get-post/
				connection.setDoOutput(true);
			}
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.connect();

			// send request
			if(isCancelled()) return null;
			if(requestData!=null)
			{
				requestStream = new BufferedOutputStream(connection.getOutputStream());
				requestStream.write(requestData);
				requestStream.flush();
			}
			
			// receive response
			if(isCancelled()) return null;
			String encoding = connection.getHeaderField("Content-Encoding");
			boolean gzipped = encoding!=null && encoding.toLowerCase().contains("gzip");
			try
			{
				InputStream inputStream = connection.getInputStream();
				if(gzipped) responseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
				else responseStream = new BufferedInputStream(inputStream);
			}
			catch(FileNotFoundException e)
			{
				// error stream
				InputStream errorStream = connection.getErrorStream();
				if(gzipped) responseStream = new BufferedInputStream(new GZIPInputStream(errorStream));
				else responseStream = new BufferedInputStream(errorStream);
			}
			
			// response info
			//Logcat.d("ApiCall.doInBackground().connection.getURL(): " + connection.getURL());
			//Logcat.d("ApiCall.doInBackground().connection.getContentType(): " + connection.getContentType());
			//Logcat.d("ApiCall.doInBackground().connection.getContentEncoding(): " + connection.getContentEncoding());
			//Logcat.d("ApiCall.doInBackground().connection.getResponseCode(): " + connection.getResponseCode());
			//Logcat.d("ApiCall.doInBackground().connection.getResponseMessage(): " + connection.getResponseMessage());
			
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
		//Logcat.d("ApiCall.onCancelled()");
	}
	
	
	private String getBasicAuthToken(String username, String password)
	{
		// Base64.NO_WRAP because of Android <4 problem
		String base64 = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
		return "Basic " + base64;
	}
}
