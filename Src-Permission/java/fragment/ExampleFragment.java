package com.example.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.R;
import com.example.utility.PermissionUtility;


public class ExampleFragment extends Fragment
{
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behavior
		switch(item.getItemId())
		{
			case R.id.menu_fragment_example_storage:
				if(PermissionUtility.checkPermissionWriteExternalStorage(this)) storage();
				return true;

			case R.id.menu_fragment_example_location:
				if(PermissionUtility.checkPermissionAccessLocation(this)) location();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch(requestCode)
		{
			case PermissionUtility.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
			case PermissionUtility.REQUEST_PERMISSION_ACCESS_LOCATION:
			case PermissionUtility.REQUEST_PERMISSION_ALL:
			{
				// if request is cancelled, the result arrays are empty
				if(grantResults.length > 0)
				{
					for(int i = 0; i < grantResults.length; i++)
					{
						if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
						{
							// permission granted
							String permission = permissions[i];
							if(permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
							{
								storage(); // TODO
							}
							else if(permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION) || permission.equals(Manifest.permission.ACCESS_FINE_LOCATION))
							{
								location(); // TODO
							}
						}
						else
						{
							// permission denied
							// TODO
						}
					}
				}
				else
				{
					// all permissions denied
					// TODO
				}
				break;
			}
		}
	}


	private void storage()
	{
		// TODO
	}


	private void location()
	{
		// TODO
	}
}
