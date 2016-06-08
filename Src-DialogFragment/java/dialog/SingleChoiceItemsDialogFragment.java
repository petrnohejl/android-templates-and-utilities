package com.example.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.R;


public class SingleChoiceItemsDialogFragment extends DialogFragment
{
	private static final String ARGUMENT_CHECKED_ITEM = "checked_item";

	private int mCheckedItem = -1;
	private SingleChoiceItemsDialogListener mListener;


	public interface SingleChoiceItemsDialogListener
	{
		void onSingleChoiceItemsDialogPositiveClick(DialogFragment dialog, int checkedItem);
		void onSingleChoiceItemsDialogNegativeClick(DialogFragment dialog);
	}


	public static SingleChoiceItemsDialogFragment newInstance(int checkedItem)
	{
		SingleChoiceItemsDialogFragment fragment = new SingleChoiceItemsDialogFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_CHECKED_ITEM, checkedItem);
		fragment.setArguments(arguments);

		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setCancelable(true);
		setRetainInstance(true);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			handleArguments(arguments);
		}

		// set callback listener
		try
		{
			mListener = (SingleChoiceItemsDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + SingleChoiceItemsDialogListener.class.getName());
		}
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// cancelable on touch outside
		if(getDialog() != null) getDialog().setCanceledOnTouchOutside(true);
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
				"item4"};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder
				.setTitle("title")
				.setIcon(R.mipmap.ic_launcher)
				.setSingleChoiceItems(items, mCheckedItem, new DialogInterface.OnClickListener()
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
						mListener.onSingleChoiceItemsDialogPositiveClick(SingleChoiceItemsDialogFragment.this, mCheckedItem);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						mListener.onSingleChoiceItemsDialogNegativeClick(SingleChoiceItemsDialogFragment.this);
					}
				});

		return builder.create();
	}


	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(ARGUMENT_CHECKED_ITEM))
		{
			mCheckedItem = (Integer) arguments.get(ARGUMENT_CHECKED_ITEM);
		}
	}
}
