package com.example.utility;

import android.location.Location;
import android.location.LocationManager;

import com.example.ExampleApplication;
import com.example.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;


public class LocationUtility
{
	public static int getDistance(LatLng l1, LatLng l2)
	{
		Location location1 = new Location(LocationManager.PASSIVE_PROVIDER);
		location1.setLatitude(l1.latitude);
		location1.setLongitude(l1.longitude);

		Location location2 = new Location(LocationManager.PASSIVE_PROVIDER);
		location2.setLatitude(l2.latitude);
		location2.setLongitude(l2.longitude);

		int distance = (int) location1.distanceTo(location2);
		return distance;
	}
	
	
	public static String getDistanceString(double distance)
	{
		String result;
		
		if(distance<1000.0d)
		{
			result = String.format(Locale.US, "%d " + ExampleApplication.getContext().getString(R.string.unit_meter), (int) distance);
		}
		else if(distance<5000.0d)
		{
			result = String.format(Locale.US, "%.1f " + ExampleApplication.getContext().getString(R.string.unit_kilometer), distance/1000);
		}
		else
		{
			result = String.format(Locale.US, "%d " + ExampleApplication.getContext().getString(R.string.unit_kilometer), (int) distance/1000);
		}
		
		return result;
	}
	
	
	public static String getDistanceString(int distance)
	{
		return getDistanceString((double) distance);
	}
	
	
	public static int getZoom(int distance)
	{
		if(distance<50) return 18;
		else if(distance<100) return 17;
		else if(distance<500) return 16;
		else if(distance<1000) return 15;
		else if(distance<2000) return 14;
		else if(distance<5000) return 13;
		else if(distance<10000) return 12;
		else if(distance<50000) return 11;
		else return 10;
	}
}
