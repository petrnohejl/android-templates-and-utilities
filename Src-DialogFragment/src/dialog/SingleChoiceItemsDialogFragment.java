package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.R;


public class SingleChoiceItemsDialogFragment extends DialogFragment
{
	public static final String EXTRA_CHECKED_ITEM = "checked_item";
	
	private int mCheckedItem = -1;
	private SingleChoiceItemsDialogListener mListener;
	
	
	public interface SingleChoiceItemsDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog, int checkedItem);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static SingleChoiceItemsDialogFragment newInstance(int checkedItem)
	{
		SingleChoiceItemsDialogFragment fragment = new SingleChoiceItemsDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putInt(EXTRA_CHECKED_ITEM, checkedItem);
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
			mListener = (SingleChoiceItemsDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement SingleChoiceItemsDialogListener");
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
		final CharSequence items[] = {
				"item1",
				"item2",
				"item3",
				"item4" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
		.setTitle("title")
		.setIcon(R.drawable.ic_launcher)
		.setSingleChoiceItems(items, mCheckedItem, new  DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				mCheckedItem = which;
			}
		})
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogPositiveClick(SingleChoiceItemsDialogFragment.this, mCheckedItem);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogNegativeClick(SingleChoiceItemsDialogFragment.this);
			}
		});
		
		return builder.create();
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_CHECKED_ITEM))
		{
			mCheckedItem = (Integer) extras.get(EXTRA_CHECKED_ITEM);
		}
	}
}
