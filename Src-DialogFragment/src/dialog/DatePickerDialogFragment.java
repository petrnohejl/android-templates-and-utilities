package com.example.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.DatePicker;


public class DatePickerDialogFragment extends DialogFragment implements FixedDatePickerDialog.OnDateSetListener
{
	public static final String EXTRA_DATE = "date";
	
	private long mDate = -1l;
	private DatePickerDialogListener mListener;
	
	
	public interface DatePickerDialogListener
	{
		public void onDatePickerDialogPositiveClick(DialogFragment dialog, int year, int month, int day);
	}
	
	
	public static DatePickerDialogFragment newInstance(Calendar calendar)
	{
		DatePickerDialogFragment fragment = new DatePickerDialogFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putLong(EXTRA_DATE, calendar.getTimeInMillis());
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
			mListener = (DatePickerDialogListener) getTargetFragment();
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + DatePickerDialogListener.class.getName());
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
		if(mDate!=-1l)
		{
			calendar.setTimeInMillis(mDate);
		}
		
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		FixedDatePickerDialog dialog = new FixedDatePickerDialog(calendar, context, this);
		return dialog;
	}
	
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		mListener.onDatePickerDialogPositiveClick(DatePickerDialogFragment.this, year, month, day);
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
		if(arguments.containsKey(EXTRA_DATE))
		{
			mDate = (Long) arguments.get(EXTRA_DATE);
		}
	}
}
