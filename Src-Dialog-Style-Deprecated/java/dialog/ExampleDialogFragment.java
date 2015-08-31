package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import com.example.utility.DialogStyle;


public class ExampleDialogFragment extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder
		.setTitle("title")
		.setMessage("message");
		
		// create dialog from builder
		final Dialog dialog = builder.create();
		
		// override style
		dialog.setOnShowListener(new DialogInterface.OnShowListener()
		{
			@Override
			public void onShow(DialogInterface dialogInterface)
			{
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				{
					try
					{
						DialogStyle.overrideStyle(getActivity(), dialog, true);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		return dialog;
	}
	
	
	private int getTheme(boolean light)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			return light ? android.R.style.Theme_DeviceDefault_Light_Dialog : android.R.style.Theme_DeviceDefault_Dialog;
		}
		else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return light ? android.R.style.Theme_Holo_Light_Dialog : android.R.style.Theme_Holo_Dialog;
		}
		else
		{
			return android.R.style.Theme_Dialog;
		}
	}
}
