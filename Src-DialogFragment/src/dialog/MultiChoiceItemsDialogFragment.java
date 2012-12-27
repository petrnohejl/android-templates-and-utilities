package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.R;


public class MultiChoiceItemsDialogFragment extends DialogFragment
{
	public static final String EXTRA_CHECKED_ITEMS = "checked_items";
	
	private boolean mCheckedItems[] = null;
	private MultiChoiceItemsDialogListener mListener;
	
	
	public interface MultiChoiceItemsDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog, boolean checkedItems[]);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static MultiChoiceItemsDialogFragment newInstance(boolean checkedItems[])
	{
		MultiChoiceItemsDialogFragment fragment = new MultiChoiceItemsDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putBooleanArray(EXTRA_CHECKED_ITEMS, checkedItems);
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
			mListener = (MultiChoiceItemsDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement MultiChoiceItemsDialogListener");
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
		.setMultiChoiceItems(items, mCheckedItems, new OnMultiChoiceClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked)
			{
				mCheckedItems[which] = isChecked;
			}
		})
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogPositiveClick(MultiChoiceItemsDialogFragment.this, mCheckedItems);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogNegativeClick(MultiChoiceItemsDialogFragment.this);
			}
		});
		
		return builder.create();
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_CHECKED_ITEMS))
		{
			mCheckedItems = (boolean[]) extras.get(EXTRA_CHECKED_ITEMS);
		}
	}
}
