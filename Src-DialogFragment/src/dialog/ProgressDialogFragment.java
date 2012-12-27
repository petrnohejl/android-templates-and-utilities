package com.example.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


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
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage("progress");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}
	
	
	public void dismiss()
	{
		getDialog().dismiss();
	}
}
