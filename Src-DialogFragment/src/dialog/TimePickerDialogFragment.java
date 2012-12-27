package com.example.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
		
		// handle intent extras
		if(getArguments() != null)
		{
			handleExtras(getArguments());
		}
		
		// set callback listener
		try
		{
			mListener = (TimePickerDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement TimePickerDialogListener");
		}
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Calendar calendar = Calendar.getInstance();
		if(mTime!=-1l)
		{
			calendar.setTimeInMillis(mTime);
		}
		
		FixedTimePickerDialog dialog = new FixedTimePickerDialog(calendar, getActivity(), this);
		return dialog;
	}
	
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute)
	{
		mListener.onDialogPositiveClick(TimePickerDialogFragment.this, hour, minute);
	}
	
	
	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(EXTRA_TIME))
		{
			mTime = (Long) extras.get(EXTRA_TIME);
		}
	}
}
