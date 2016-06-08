package com.example.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;

import java.util.Calendar;


// source: http://stackoverflow.com/questions/11444238/jelly-bean-datepickerdialog-is-there-a-way-to-cancel
public class FixedDatePickerDialog extends DatePickerDialog
{
	public FixedDatePickerDialog(Calendar dateToShow, Context context, OnDateSetListener callBack)
	{
		super(context, null, dateToShow.get(Calendar.YEAR), dateToShow.get(Calendar.MONTH), dateToShow.get(Calendar.DAY_OF_MONTH));
		initializePicker(callBack);
	}


	public FixedDatePickerDialog(Calendar dateToShow, Context context, int theme, OnDateSetListener callBack)
	{
		super(context, theme, null, dateToShow.get(Calendar.YEAR), dateToShow.get(Calendar.MONTH), dateToShow.get(Calendar.DAY_OF_MONTH));
		initializePicker(callBack);
	}


	private void initializePicker(final OnDateSetListener callback)
	{
		try
		{
			// if you're only using Honeycomb+ then you can just call getDatePicker() instead of using reflection
			java.lang.reflect.Field pickerField = DatePickerDialog.class.getDeclaredField("mDatePicker");
			pickerField.setAccessible(true);
			final DatePicker picker = (DatePicker) pickerField.get(this);
			this.setCancelable(true);
			this.setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getText(android.R.string.cancel), (OnClickListener) null);
			this.setButton(DialogInterface.BUTTON_POSITIVE, getContext().getText(android.R.string.ok), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					picker.clearFocus(); // focus must be cleared so the value change listener is called
					callback.onDateSet(picker, picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
				}
			});
		}
		catch(Exception e)
		{
			// reflection probably failed
		}
	}
}
