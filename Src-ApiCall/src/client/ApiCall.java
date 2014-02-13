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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

import org.codehaus.jackson.JsonParseException;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.client.request.Request;
import com.example.client.response.Response;
import com.example.utility.Logcat;


public class ApiCall extends AsyncTask<Void, Void, Response>
{
	private Request mRequest;
	private WeakReference<OnApiCallListener> mOnApiCallListener;
	private ResponseStatus mStatus = new ResponseStatus();
	private Exception mException = null;
	
	private HttpURLConnection mConnection = null;
	//private HttpsURLConnection mConnection = null; // for SSL
	private OutputStream mRequestStream = null;
	private InputStream mResponseStream = null;

	
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
		try
		{
			// disables Keep-Alive for all connections
			if(isCancelled()) return null;
			System.setProperty("http.keepAlive", "false");
			
			// new connection
			byte[] requestData = mRequest.getContent();
			URL url = new URL(mRequest.getAddress());	
			mConnection = (HttpURLConnection) url.openConnection();
			//mConnection = (HttpsURLConnection) url.openConnection(); // for SSL
			
			// ssl connection properties
			//SelfSignedSslUtility.setupSslConnection(mConnection, url); // for SSL using self signed certificate
			//CertificateAuthoritySslUtility.setupSslConnection(mConnection, url); // for SSL using certificate authority
			
			// connection properties
			if(mRequest.getRequestMethod()!=null)
			{
				mConnection.setRequestMethod(mRequest.getRequestMethod()); // GET, POST, OPTIONS, HEAD, PUT, DELETE, TRACE
			}
			if(mRequest.getBasicAuthUsername()!=null && mRequest.getBasicAuthPassword()!=null)
			{
				mConnection.setRequestProperty("Authorization", getBasicAuthToken(mRequest.getBasicAuthUsername(), mRequest.getBasicAuthPassword()));
			}
			mConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//mConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mRequest.BOUNDARY); // for multipart
			mConnection.setRequestProperty("Accept", "application/json");
			mConnection.setRequestProperty("Accept-Encoding", "gzip");
			mConnection.setRequestProperty("Accept-Charset", "UTF-8");
			//mConnection.setRequestProperty("Content-Length", requestData == null ? "0" : String.valueOf(requestData.length));
			//if(requestData!=null) mConnection.setChunkedStreamingMode(0);
			if(requestData!=null) mConnection.setFixedLengthStreamingMode(requestData.length);
			mConnection.setConnectTimeout(30000);
			mConnection.setReadTimeout(30000);
			if(requestData!=null)
			{
				// this call automatically sets request method to POST on Android 4
				// if you don't want your app to POST, you must not call setDoOutput
				// http://webdiary.com/2011/12/14/ics-get-post/
				mConnection.setDoOutput(true);
			}
			mConnection.setDoInput(true);
			mConnection.setUseCaches(false);
			mConnection.connect();

			// send request
			if(isCancelled()) return null;
			if(requestData!=null)
			{
				mRequestStream = new BufferedOutputStream(mConnection.getOutputStream());
				mRequestStream.write(requestData);
				mRequestStream.flush();
			}
			
			// receive response
			if(isCancelled()) return null;
			String encoding = mConnection.getHeaderField("Content-Encoding");
			boolean gzipped = encoding!=null && encoding.toLowerCase().contains("gzip");
			try
			{
				InputStream inputStream = mConnection.getInputStream();
				if(gzipped) mResponseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
				else mResponseStream = new BufferedInputStream(inputStream);
			}
			catch(FileNotFoundException e)
			{
				// error stream
				InputStream errorStream = mConnection.getErrorStream();
				if(gzipped) mResponseStream = new BufferedInputStream(new GZIPInputStream(errorStream));
				else mResponseStream = new BufferedInputStream(errorStream);
			}
			
			// response info
			//Logcat.d("ApiCall.doInBackground().connection.getURL(): " + mConnection.getURL());
			//Logcat.d("ApiCall.doInBackground().connection.getContentType(): " + mConnection.getContentType());
			//Logcat.d("ApiCall.doInBackground().connection.getContentEncoding(): " + mConnection.getContentEncoding());
			//Logcat.d("ApiCall.doInBackground().connection.getResponseCode(): " + mConnection.getResponseCode());
			//Logcat.d("ApiCall.doInBackground().connection.getResponseMessage(): " + mConnection.getResponseMessage());
			
			// parse response
			if(isCancelled()) return null;
			Response response = mRequest.parseResponse(mResponseStream);
			if(response==null) throw new RuntimeException("Parser returned null response");

			if(isCancelled()) return null;
			return response;
		}
		catch(UnknownHostException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(FileNotFoundException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(SocketException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(SocketTimeoutException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(JsonParseException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			mException = e;
			e.printStackTrace();
			return null;
		}
		finally
		{
			disconnect();
		}
	}
	
	
	@Override
	protected void onPostExecute(Response response)
	{
		if(isCancelled()) return;
		
		OnApiCallListener listener = mOnApiCallListener.get();
		if(listener!=null)
		{
			if(response!=null)
			{
				listener.onApiCallRespond(this, mStatus, response);
			}
			else
			{
				listener.onApiCallFail(this, mStatus, mException);
			}
		}
	}
	
	
	@Override
	protected void onCancelled()
	{
		Logcat.d("ApiCall.onCancelled()");
	}

	
	private String getBasicAuthToken(String username, String password)
	{
		// Base64.NO_WRAP because of Android <4 problem
		String base64 = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
		return "Basic " + base64;
	}
	
	
	private void disconnect()
	{
		try
		{
			if(mRequestStream!=null) mRequestStream.close();
		}
		catch(IOException e) {}

		try
		{
			if(mResponseStream!=null) mResponseStream.close();
		}
		catch(IOException e) {}

		try
		{
			// set status
			if(mConnection!=null)
			{
				mStatus.setStatusCode(mConnection.getResponseCode());
				mStatus.setStatusMessage(mConnection.getResponseMessage());
				mConnection.disconnect();
			}
		}
		catch(Throwable e) {}

		mRequestStream = null;
		mResponseStream = null;
		mConnection = null;
	}
	
	
	public void kill()
	{
		disconnect();
	}
}
