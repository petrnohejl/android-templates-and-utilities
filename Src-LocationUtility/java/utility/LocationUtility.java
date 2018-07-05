package com.example.utility;

import android.location.Location;
import android.location.LocationManager;

import com.example.ExampleApplication;
import com.example.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public final class LocationUtility {
	private LocationUtility() {}

	public static int getDistance(LatLng l1, LatLng l2) {
		Location location1 = new Location(LocationManager.PASSIVE_PROVIDER);
		location1.setLatitude(l1.latitude);
		location1.setLongitude(l1.longitude);

		Location location2 = new Location(LocationManager.PASSIVE_PROVIDER);
		location2.setLatitude(l2.latitude);
		location2.setLongitude(l2.longitude);

		return (int) location1.distanceTo(location2);
	}

	public static String getDistanceString(double distance, boolean useMetricSystem) {
		String result;

		if (useMetricSystem) {
			if (distance < 1000.0) {
				result = String.format(Locale.US, "%d " + ExampleApplication.getContext().getString(R.string.unit_meter), (int) distance);
			} else if (distance < 5000.0) {
				result = String.format(Locale.US, "%.1f " + ExampleApplication.getContext().getString(R.string.unit_kilometer), distance / 1000);
			} else {
				result = String.format(Locale.US, "%d " + ExampleApplication.getContext().getString(R.string.unit_kilometer), (int) distance / 1000);
			}
		} else {
			double distanceMiles = distance * 0.000621371192; // distance in miles

			if (distanceMiles < 0.1) {
				result = String.format(Locale.US, "%.2f " + ExampleApplication.getContext().getString(R.string.unit_mile), distanceMiles);
			} else if (distanceMiles < 10.0) {
				result = String.format(Locale.US, "%.1f " + ExampleApplication.getContext().getString(R.string.unit_mile), distanceMiles);
			} else {
				result = String.format(Locale.US, "%d " + ExampleApplication.getContext().getString(R.string.unit_mile), (int) distanceMiles);
			}
		}

		return result;
	}

	public static String getDistanceString(int distance, boolean useMetricSystem) {
		return getDistanceString((double) distance, useMetricSystem);
	}

	public static boolean isMetricSystem() {
		Locale locale = Locale.getDefault();
		String countryCode = locale.getCountry();
		return (!"US".equals(countryCode) && !"LR".equals(countryCode) && !"MM".equals(countryCode));
	}

	public static int getZoom(int distance) {
		if (distance < 50) return 18;
		else if (distance < 100) return 17;
		else if (distance < 500) return 16;
		else if (distance < 1000) return 15;
		else if (distance < 2000) return 14;
		else if (distance < 5000) return 13;
		else if (distance < 10000) return 12;
		else if (distance < 50000) return 11;
		else return 10;
	}

	public static boolean isPointInPolygon(LatLng location, List<LatLng> vertices) {
		if (location == null) return false;

		LatLng lastPoint = vertices.get(vertices.size() - 1);
		boolean isInside = false;
		double x = location.longitude;

		for (LatLng point : vertices) {
			double x1 = lastPoint.longitude;
			double x2 = point.longitude;
			double dx = x2 - x1;

			if (Math.abs(dx) > 180.0) {
				if (x > 0) {
					while (x1 < 0) x1 += 360;
					while (x2 < 0) x2 += 360;
				} else {
					while (x1 > 0) x1 -= 360;
					while (x2 > 0) x2 -= 360;
				}
				dx = x2 - x1;
			}

			if ((x1 <= x && x2 > x) || (x1 >= x && x2 < x)) {
				double grad = (point.latitude - lastPoint.latitude) / dx;
				double intersectAtLat = lastPoint.latitude + ((x - x1) * grad);

				if (intersectAtLat > location.latitude) isInside = !isInside;
			}

			lastPoint = point;
		}

		return isInside;
	}
}
