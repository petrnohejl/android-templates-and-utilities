package com.example.utility;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.R;

import java.util.ArrayList;
import java.util.List;


public final class PermissionUtility
{
	public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
	public static final int REQUEST_PERMISSION_ACCESS_LOCATION = 2;
	public static final int REQUEST_PERMISSION_ALL = 3;


	private PermissionUtility() {}


	public static boolean checkPermissionWriteExternalStorage(final Fragment fragment)
	{
		return check(fragment,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				R.string.permission_write_external_storage,
				REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
	}


	public static boolean checkPermissionAccessLocation(final Fragment fragment)
	{
		return check(fragment,
				new String[] { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION },
				new int[] { R.string.permission_access_location, R.string.permission_access_location },
				REQUEST_PERMISSION_ACCESS_LOCATION);
	}


	public static boolean checkPermissionAll(final Fragment fragment)
	{
		return check(fragment,
				new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION },
				new int[] { R.string.permission_write_external_storage, R.string.permission_access_location, R.string.permission_access_location },
				REQUEST_PERMISSION_ALL);
	}


	private static boolean check(final Fragment fragment, final String permission, final int explanation, final int requestCode)
	{
		// check permission
		final int result = ContextCompat.checkSelfPermission(fragment.getActivity(), permission);

		// ask for permission
		if(result != PackageManager.PERMISSION_GRANTED)
		{
			// check if explanation is needed
			if(fragment.shouldShowRequestPermissionRationale(permission))
			{
				// show explanation
				Snackbar
						.make(fragment.getView(), explanation, Snackbar.LENGTH_INDEFINITE)
						.setAction(android.R.string.ok, new View.OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								// try again
								fragment.requestPermissions(new String[]{ permission }, requestCode);
							}
						}).show();
			}
			else
			{
				// no explanation needed
				fragment.requestPermissions(new String[]{ permission }, requestCode);
			}
		}

		// result
		return result == PackageManager.PERMISSION_GRANTED;
	}


	private static boolean check(final Fragment fragment, final String[] permissions, final int[] explanations, final int requestCode)
	{
		// check permissions
		final int[] results = new int[permissions.length];
		for(int i = 0; i < permissions.length; i++)
		{
			results[i] = ContextCompat.checkSelfPermission(fragment.getActivity(), permissions[i]);
		}

		// get denied permissions
		final List<String> deniedPermissions = new ArrayList<>();
		for(int i = 0; i < results.length; i++)
		{
			if(results[i] != PackageManager.PERMISSION_GRANTED)
			{
				deniedPermissions.add(permissions[i]);
			}
		}

		// ask for permissions
		if(!deniedPermissions.isEmpty())
		{
			final String[] params = deniedPermissions.toArray(new String[deniedPermissions.size()]);

			// check if explanation is needed
			boolean explanationShown = false;
			for(int i = 0; i < permissions.length; i++)
			{
				if(fragment.shouldShowRequestPermissionRationale(permissions[i]))
				{
					// show explanation
					Snackbar
							.make(fragment.getView(), explanations[i], Snackbar.LENGTH_INDEFINITE)
							.setAction(android.R.string.ok, new View.OnClickListener()
							{
								@Override
								public void onClick(View v)
								{
									// try again
									fragment.requestPermissions(params, requestCode);
								}
							}).show();
					explanationShown = true;
					break;
				}
			}

			// no explanation needed
			if(!explanationShown)
			{
				fragment.requestPermissions(params, requestCode);
			}
		}

		// result
		return deniedPermissions.isEmpty();
	}
}
