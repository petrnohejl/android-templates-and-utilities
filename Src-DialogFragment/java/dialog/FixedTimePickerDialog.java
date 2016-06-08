package com.example.dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;


// source: http://stackoverflow.com/questions/11444238/jelly-bean-datepickerdialog-is-there-a-way-to-cancel
public class FixedTimePickerDialog extends TimePickerDialog
{
	public FixedTimePickerDialog(Calendar timeToShow, Context context, OnTimeSetListener callBack)
	{
		super(context, null, timeToShow.get(Calendar.HOUR_OF_DAY), timeToShow.get(Calendar.MINUTE), DateFormat.is24HourFormat(context));
		initializePicker(callBack);
	}


	public FixedTimePickerDialog(Calendar timeToShow, Context context, int theme, OnTimeSetListener callBack)
	{
		super(context, theme, null, timeToShow.get(Calendar.HOUR_OF_DAY), timeToShow.get(Calendar.MINUTE), DateFormat.is24HourFormat(context));
		initializePicker(callBack);
	}


	private void initializePicker(final OnTimeSetListener callback)
	{
		try
		{
			// if you're only using Honeycomb+ then you can just call getTimePicker() instead of using reflection
			java.lang.reflect.Field pickerField = TimePickerDialog.class.getDeclaredField("mTimePicker");
			pickerField.setAccessible(true);
			final TimePicker picker = (TimePicker) pickerField.get(this);
			this.setCancelable(true);
			this.setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getText(android.R.string.cancel), (OnClickListener) null);
			this.setButton(DialogInterface.BUTTON_POSITIVE, getContext().getText(android.R.string.ok), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					picker.clearFocus(); // focus must be cleared so the value change listener is called
					callback.onTimeSet(picker, picker.getCurrentHour(), picker.getCurrentMinute());
				}
			});
		}
		catch(Exception e)
		{
			// reflection probably failed
		}
	}
}
