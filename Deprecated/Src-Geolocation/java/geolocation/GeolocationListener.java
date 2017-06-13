package com.example.geolocation;

import android.location.Location;


public interface GeolocationListener
{
	void onGeolocationRespond(Geolocation geolocation, Location location);
	void onGeolocationFail(Geolocation geolocation);
}
