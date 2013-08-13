package com.example.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.TimePicker;


public class TimePickerDialogFragment extends DialogFragment implements FixedTimePickerDialog.OnTimeSetListener
{
	public static final String EXTRA_TIME = "time";
	
	private long mTime = -1l;
	private TimePickerDialogListener mListener;
	
	
	public interface TimePickerDialogListener
	{
		public void onDialogPositiveClick(DialogFragment dialog, int hour, int minute);
	}
	
	
	public static TimePickerDialogFragment newInstance(Calendar calendar)
	{
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putLong(EXTRA_TIME, calendar.getTimeInMillis());
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setCancelable(true);
		
		// handle fragment arguments
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			handleArguments(arguments);
		}
		
		// set callback listener
		try
		{
			mListener = (TimePickerDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + TimePickerDialogListener.class.getName());
		}
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// cancelable on touch outside
		if(getDialog()!=null) getDialog().setCanceledOnTouchOutside(true);
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Calendar calendar = Calendar.getInstance();
		if(mTime!=-1l)
		{
			calendar.setTimeInMillis(mTime);
		}
		
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		FixedTimePickerDialog dialog = new FixedTimePickerDialog(calendar, context, this);
		return dialog;
	}
	
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute)
	{
		mListener.onDialogPositiveClick(TimePickerDialogFragment.this, hour, minute);
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
	
	
	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(EXTRA_TIME))
		{
			mTime = (Long) arguments.get(EXTRA_TIME);
		}
	}
}
