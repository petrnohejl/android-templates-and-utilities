package com.example.gcm;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.example.ExampleConfig;
import com.example.utility.Logcat;
import com.example.utility.Preferences;
import com.example.utility.Version;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


public class GcmUtility
{
	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final Random sRandom = new Random();


	public static boolean checkPlayServices(Activity activity)
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		if(resultCode!=ConnectionResult.SUCCESS)
		{
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
			{
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else
			{
				Logcat.d("GcmUtility.checkPlayServices(): this device is not supported");
				activity.finish();
			}
			return false;
		}
		return true;
	}


	public static String getRegistrationId(Context context)
	{
		Preferences preferences = new Preferences(context);

		// get saved registration id
		String registrationId = preferences.getGcmRegistrationId();
		if(registrationId.isEmpty())
		{
			Logcat.d("GcmUtility.getRegistrationId(): registration id not found");
			return "";
		}

		// check if app was updated
		int registeredVersion = preferences.getGcmVersionCode();
		int currentVersion = Version.getVersionCode(context);
		if(registeredVersion!=currentVersion)
		{
			Logcat.d("GcmUtility.getRegistrationId(): app version has changed");
			return "";
		}

		return registrationId;
	}


	// register this account/device pair within the server
	public static void register(final Context context, final String registrationId)
	{
		Logcat.d("GcmUtility.register(): registering device with registration id " + registrationId);

		// request url
		String requestUrl = ExampleConfig.GCM_REGISTER_URL;

		// request params
		Uri.Builder builder = new Uri.Builder();
		builder.appendQueryParameter("regId", registrationId);
		String params = builder.build().toString().substring(1);

		// initial sleep time before next try
		long backoff = BACKOFF_MILLI_SECONDS + sRandom.nextInt(1000);

		// Once GCM returns a registration id, we need to register it on the server.
		// As the server might be down, we will retry it a couple times.
		for(int i=1; i<=MAX_ATTEMPTS; i++)
		{
			Logcat.d("GcmUtility.register(): attempt #" + i + " to register");

			try
			{
				post(requestUrl, params); // TODO: use post or get

				Preferences preferences = new Preferences(context);
				preferences.setGcmRegistrationId(registrationId);
				preferences.setGcmVersionCode(Version.getVersionCode(context));

				Logcat.d("GcmUtility.register(): server successfully registered device");
				return;
			}
			catch(IOException e)
			{
				// Here we are simplifying and retrying on any error.
				// In a real application, it should retry only on unrecoverable errors (like HTTP error code 503).
				Logcat.d("GcmUtility.register(): server failed to register on attempt #" + i + " / " + e.getMessage());

				if(i == MAX_ATTEMPTS) break;

				try
				{
					Logcat.d("GcmUtility.register(): sleeping for " + backoff + " ms before retry");
					Thread.sleep(backoff);
				}
				catch(InterruptedException interruptedException)
				{
					// activity finished before we complete
					Logcat.d("GcmUtility.register(): thread interrupted so abort remaining retries");
					Thread.currentThread().interrupt();
					return;
				}

				// increase backoff exponentially
				backoff *= 2;
			}
		}

		Logcat.d("GcmUtility.register(): could not register device on server after " + MAX_ATTEMPTS + " attempts");
	}


	// unregister this account/device pair within the server
	public static void unregister(final Context context, final String registrationId)
	{
		Logcat.d("GcmUtility.unregister(): unregistering device with registration id " + registrationId);

		// request url
		String requestUrl = ExampleConfig.GCM_UNREGISTER_URL;

		// request params
		Uri.Builder builder = new Uri.Builder();
		builder.appendQueryParameter("regId", registrationId);
		String params = builder.build().toString().substring(1);

		try
		{
			post(requestUrl, params); // TODO: use post or get

			Preferences preferences = new Preferences(context);
			preferences.setGcmRegistrationId("");
			preferences.setGcmVersionCode(-1);

			Logcat.d("GcmUtility.unregister(): server successfully unregistered device");
		}
		catch(IOException e)
		{
			// At this point the device is unregistered from GCM, but still registered on the server.
			// We could try to unregister again, but it is not necessary: if the server tries to send a message to the device,
			// it will get a "NotRegistered" error message and should unregister the device.
			Logcat.d("GcmUtility.unregister(): could not unregister device on server / " + e.getMessage());
		}
	}


	// send a POST request to the server
	private static void post(String requestUrl, String params) throws IOException
	{
		// create new URL
		URL url;
		try
		{
			url = new URL(requestUrl);
		}
		catch(MalformedURLException e)
		{
			throw new IllegalArgumentException("Invalid url " + requestUrl);
		}

		// data
		byte[] requestData = params.getBytes();

		Logcat.d("GcmUtility.post(): sending '" + params + "' to " + url);

		// URL connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setFixedLengthStreamingMode(requestData.length);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.connect();

			// post the request
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(requestData);
			outputStream.close();

			// handle the response
			int status = connection.getResponseCode();
			if(status != 200)
			{
				throw new IOException("POST failed with error code " + status);
			}
		}
		finally
		{
			if(connection != null) connection.disconnect();
		}
	}


	// send a GET request to the server
	private static void get(String baseUrl, String params) throws IOException
	{
		// request URL
		StringBuilder builder = new StringBuilder();
		builder.append(baseUrl);
		if(params!=null && !params.equals(""))
		{
			builder.append("?");
			builder.append(params);
		}
		String requestUrl = builder.toString();

		// create new URL
		URL url;
		try
		{
			url = new URL(requestUrl);
		}
		catch(MalformedURLException e)
		{
			throw new IllegalArgumentException("Invalid url " + requestUrl);
		}

		Logcat.d("GcmUtility.get(): sending " + requestUrl);

		// URL connection
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setUseCaches(false);
			connection.connect();

			// handle the response
			int status = connection.getResponseCode();
			if(status != 200)
			{
				throw new IOException("GET failed with error code " + status);
			}
		}
		finally
		{
			if(connection != null) connection.disconnect();
		}
	}
}
