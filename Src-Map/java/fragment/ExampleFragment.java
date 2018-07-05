package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.alfonz.utility.VersionUtility;

public class ExampleFragment extends Fragment {
	private View mRootView;
	private MapView mMapView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		initMap();
		mMapView = mRootView.findViewById(R.id.example_map);
		mMapView.onCreate(savedInstanceState);
		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupMap();
		bindData();
	}

	@Override
	public void onResume() {
		super.onResume();

		// map
		if (mMapView != null) mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();

		// map
		if (mMapView != null) mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// map
		if (mMapView != null) mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		// map
		if (mMapView != null) mMapView.onLowMemory();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);

		// map
		if (mMapView != null) mMapView.onSaveInstanceState(outState);
	}

	private void initMap() {
		if (!VersionUtility.isSupportedOpenGlEs2(getActivity())) {
			Toast.makeText(getActivity(), R.string.global_map_fail_toast, Toast.LENGTH_LONG).show();
		}

		try {
			MapsInitializer.initialize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupMap() {
		mRootView.findViewById(R.id.example_map).getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap) {
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				googleMap.setMyLocationEnabled(true);

				UiSettings settings = googleMap.getUiSettings();
				settings.setAllGesturesEnabled(true);
				settings.setMyLocationButtonEnabled(true);
				settings.setZoomControlsEnabled(true);

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(49.194696, 16.608595))
						.zoom(11)
						.bearing(0)
						.tilt(30)
						.build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			}
		});
	}

	private void bindData() {
		mRootView.findViewById(R.id.example_map).getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap) {
				BitmapDescriptor marker1 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
				BitmapDescriptor marker2 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
				BitmapDescriptor marker3 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
				BitmapDescriptor marker4 = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
				BitmapDescriptor[] markers = {marker1, marker2, marker3, marker4};

				for (int i = 0; i < 16; i++) {
					googleMap.addMarker(new MarkerOptions()
							.position(new LatLng(49.194696 + 0.1 * Math.sin(i * Math.PI / 8), 16.608595 + 0.1 * Math.cos(i * Math.PI / 8)))
							.title("Example " + i)
							.icon(markers[i % 4])
					);
				}
			}
		});
	}
}
