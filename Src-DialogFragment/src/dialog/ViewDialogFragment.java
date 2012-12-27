package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;


public class ViewDialogFragment extends DialogFragment
{
	public static final String EXTRA_ARG = "arg";
	
	private String mArg;
	private View mRootView;
	private String mUsername = "";
	private String mPassword = "";
	private ViewDialogListener mListener;
	
	
	public interface ViewDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog, String username, String password);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	
	public static ViewDialogFragment newInstance(String arg)
	{
		ViewDialogFragment fragment = new ViewDialogFragment();
		
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
			mListener = (ViewDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement ViewDialogListener");
		}
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// restore saved state
		handleSavedInstanceState();
	}
	
	
	@Override
	public void onDestroyView()
	{
		// http://code.google.com/p/android/issues/detail?id=17423
		if(getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);

		EditText editTextUsername = (EditText) mRootView.findViewById(R.id.dialog_view_username);
		EditText editTextPassword = (EditText) mRootView.findViewById(R.id.dialog_view_password);
		
		mUsername = editTextUsername.getText().toString();
		mPassword = editTextPassword.getText().toString();
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mRootView = inflater.inflate(R.layout.dialog_view, null);
		
		builder
		.setTitle(mArg)
		.setIcon(R.drawable.ic_launcher)
		.setView(mRootView)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				// overriden below
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				mListener.onDialogNegativeClick(ViewDialogFragment.this);
			}
		});
		
		// create dialog from builder
		final AlertDialog dialog = builder.create();
		
		// override positive button
		dialog.setOnShowListener(new DialogInterface.OnShowListener()
		{
			@Override
			public void onShow(DialogInterface dialogInterface)
			{
				Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				button.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						EditText editTextUsername = (EditText) mRootView.findViewById(R.id.dialog_view_username);
						EditText editTextPassword = (EditText) mRootView.findViewById(R.id.dialog_view_password);
						
						String username = editTextUsername.getText().toString();
						String password = editTextPassword.getText().toString();
						
						// TODO: data validation
						
						mListener.onDialogPositiveClick(ViewDialogFragment.this, username, password);
						dialog.dismiss();
					}
				});
			}
		});
		
		return dialog;
	}
	
	
	private void handleSavedInstanceState()
	{
		EditText editTextUsername = (EditText) mRootView.findViewById(R.id.dialog_view_username);
		EditText editTextPassword = (EditText) mRootView.findViewById(R.id.dialog_view_password);
		
		editTextUsername.setText(mUsername);
		editTextPassword.setText(mPassword);
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_ARG))
		{
			mArg = (String) extras.get(EXTRA_ARG);
		}
	}
}
