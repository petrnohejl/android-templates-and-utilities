package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.R;


public class SimpleDialogFragment extends DialogFragment
{
	public static final String EXTRA_ARG = "arg";
	
	private String mArg;
	private SimpleDialogListener mListener;
	
	
	public interface SimpleDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static SimpleDialogFragment newInstance(String arg)
	{
		SimpleDialogFragment fragment = new SimpleDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putString(EXTRA_ARG, arg);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(true);
		setRetainInstance(true);
		
		// handle intent extras
		if(getArguments() != null)
		{
			handleExtras(getArguments());
		}
		
		// set callback listener
		try
		{
			mListener = (SimpleDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement SimpleDialogListener");
		}
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
		.setTitle("title")
		.setIcon(R.drawable.ic_launcher)
		.setMessage(mArg)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogPositiveClick(SimpleDialogFragment.this);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogNegativeClick(SimpleDialogFragment.this);
			}
		});
		
		return builder.create();
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_ARG))
		{
			mArg = (String) extras.get(EXTRA_ARG);
		}
	}
}
