package com.example.geolocation;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.example.utility.Logcat;


public class Geolocation implements LocationListener
{
	private static final int LOCATION_AGE = 60000 * 30; // milliseconds
	private static final int LOCATION_TIMEOUT = 30000; // milliseconds
	
	private WeakReference<OnGeolocationListener> mOnGeolocationListener;
	private LocationManager mLocationManager;
	private Location mCurrentLocation;
	private Timer mTimer;
	
	
	public Geolocation(LocationManager locationManager, OnGeolocationListener onGeolocationListener)
	{
		mLocationManager = locationManager; // (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE); 
		mOnGeolocationListener = new WeakReference<OnGeolocationListener>(onGeolocationListener);
		mTimer = new Timer();
		init();
	}
	
	
	private void init()
	{
		// get last known location
		Location lastKnownLocation = getLastKnownLocation(mLocationManager);
		
		// try to listen last known location
		if(lastKnownLocation != null)
		{
			onLocationChanged(lastKnownLocation);
		}
		
		if(mCurrentLocation == null)
		{
			// start timer to check timeout
			TimerTask task = new TimerTask()
			{
				public void run()
				{
					if(mCurrentLocation == null)
					{
						Logcat.d("Geolocation.timer: timeout");
						stop();
						OnGeolocationListener listener = mOnGeolocationListener.get();
						if(listener != null) listener.onGeolocationFail(Geolocation.this);
					}
				}
			};
			mTimer.schedule(task, LOCATION_TIMEOUT);
			
			// register location updates
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0l, 0.0f, this);
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l, 0.0f, this);
		}
	}
		
		
	public void stop()
	{
		Logcat.d("Geolocation.stop()");
		if(mTimer!=null) mTimer.cancel();
		if(mLocationManager!=null) 
		{
			mLocationManager.removeUpdates(this);
			mLocationManager = null;
		}
	}
	
	
	@Override
	public void onLocationChanged(Location location)
	{
		Logcat.d("Geolocation.onLocationChanged(): " + location.getProvider() + " / " + location.getLatitude() + " / " + location.getLongitude() + " / " + new Date(location.getTime()).toString());
		
		// check location age
		long timeDelta = System.currentTimeMillis() - location.getTime();
		if(timeDelta > LOCATION_AGE)
		{
			Logcat.d("Geolocation.onLocationChanged(): gotten location is too old");
			// gotten location is too old
			return;
		}
		
		// return location
		mCurrentLocation = new Location(location);
		stop();
		OnGeolocationListener listener = mOnGeolocationListener.get();
		if(listener!=null && location!=null) listener.onGeolocationRespond(Geolocation.this, mCurrentLocation);
	}


	@Override
	public void onProviderDisabled(String provider)
	{
		Logcat.d("Geolocation.onProviderDisabled(): " + provider);
	}


	@Override
	public void onProviderEnabled(String provider)
	{
		Logcat.d("Geolocation.onProviderEnabled(): " + provider);
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		Logcat.d("Geolocation.onStatusChanged(): " + provider);
		switch(status) 
		{
			case LocationProvider.OUT_OF_SERVICE:
				Logcat.d("Geolocation.onStatusChanged(): status OUT_OF_SERVICE");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Logcat.d("Geolocation.onStatusChanged(): status TEMPORARILY_UNAVAILABLE");
				break;
			case LocationProvider.AVAILABLE:
				Logcat.d("Geolocation.onStatusChanged(): status AVAILABLE");
				break;
		}
	}
	
	
	// returns last known freshest location from network or GPS
	private Location getLastKnownLocation(LocationManager locationManager)
	{
		Logcat.d("Geolocation.getLastKnownLocation()");

		Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		long timeNet = 0;
		long timeGps = 0;
		
		if(locationNet!=null)
		{
			timeNet = locationNet.getTime();
		}
		
		if(locationGps!=null)
		{
			timeGps = locationGps.getTime();
		}
		
		if(timeNet>timeGps) return locationNet;
		else return locationGps;
	}
}
