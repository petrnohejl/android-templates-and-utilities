package com.example.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;


public class ProgressDialogFragment extends DialogFragment
{
	public static ProgressDialogFragment newInstance()
	{
		ProgressDialogFragment fragment = new ProgressDialogFragment();
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(false);
		setRetainInstance(true);
	}
	
	
	@Override
	public void onDestroyView()
	{
		// http://code.google.com/p/android/issues/detail?id=17423
		if(getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage("progress");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}
	
	
	public void dismiss()
	{
		getDialog().dismiss();
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
