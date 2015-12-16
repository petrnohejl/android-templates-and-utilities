package com.example.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;

import com.example.R;


public class ExampleFragment extends Fragment
{
	private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behavior
		switch(item.getItemId())
		{
			case R.id.menu_fragment_example_storage:
				if(checkPermissionStorage()) showStorage();
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
			case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
			{
				// if request is cancelled, the result arrays are empty
				if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					// permission granted
					showStorage(); // TODO
				}
				else
				{
					// permission denied
					// TODO
				}
				break;
			}
		}
	}


	private boolean checkPermissionStorage()
	{
		int permissionReadExternalStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

		if(permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED)
		{
			if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
			{
				// show explanation
				Snackbar
						.make(mRootView, R.string.fragment_example_permission_storage, Snackbar.LENGTH_INDEFINITE)
						.setAction(android.R.string.ok, new View.OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								// try again
								requestPermissions(
										new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
										REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
							}
						}).show();
			}
			else
			{
				// no explanation needed
				requestPermissions(
						new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
						REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
			}
		}

		return permissionReadExternalStorage == PackageManager.PERMISSION_GRANTED;
	}


	private void showStorage()
	{
		// TODO
	}
}
