package com.example.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements FixedTimePickerDialog.OnTimeSetListener {
	private static final String ARGUMENT_TIME = "time";

	private long mTime = -1L;
	private TimePickerDialogListener mListener;

	public interface TimePickerDialogListener {
		void onTimePickerDialogPositiveClick(DialogFragment dialog, int hour, int minute);
	}

	public static TimePickerDialogFragment newInstance(Calendar calendar) {
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putLong(ARGUMENT_TIME, calendar.getTimeInMillis());
		fragment.setArguments(arguments);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(true);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if (arguments != null) {
			handleArguments(arguments);
		}

		// set callback listener
		try {
			mListener = (TimePickerDialogListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + TimePickerDialogListener.class.getName());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// cancelable on touch outside
		if (getDialog() != null) getDialog().setCanceledOnTouchOutside(true);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar calendar = Calendar.getInstance();
		if (mTime != -1L) {
			calendar.setTimeInMillis(mTime);
		}

		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), getTheme(true));
		return new FixedTimePickerDialog(calendar, context, this);
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		mListener.onTimePickerDialogPositiveClick(TimePickerDialogFragment.this, hour, minute);
	}

	private int getTheme(boolean light) {
		return light ? android.R.style.Theme_DeviceDefault_Light_Dialog : android.R.style.Theme_DeviceDefault_Dialog;
	}

	private void handleArguments(Bundle arguments) {
		if (arguments.containsKey(ARGUMENT_TIME)) {
			mTime = (Long) arguments.get(ARGUMENT_TIME);
		}
	}
}
