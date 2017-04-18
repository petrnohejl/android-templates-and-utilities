package com.example.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.geolocation.Geolocation;
import com.example.geolocation.GeolocationListener;

import org.alfonz.utility.Logcat;

import java.util.Date;


public class ExampleFragment extends TaskFragment implements GeolocationListener
{
	private View mRootView;
	private Geolocation mGeolocation = null;
	private Location mLocation = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// start geolocation
		if(mLocation == null)
		{
			mGeolocation = null;
			mGeolocation = new Geolocation((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), this);
		}
	}


	@Override
	public void onPause()
	{
		super.onPause();

		// stop geolocation
		if(mGeolocation != null) mGeolocation.stop();
	}


	@Override
	public void onGeolocationRespond(Geolocation geolocation, final Location location)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				Logcat.d("onGeolocationRespond() = " + location.getProvider() + " / " + location.getLatitude() + " / " + location.getLongitude() + " / " + new Date(location.getTime()).toString());
				mLocation = new Location(location);

				// TODO
			}
		});
	}


	@Override
	public void onGeolocationFail(Geolocation geolocation)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				Logcat.d("onGeolocationFail()");

				// TODO
			}
		});
	}
}
