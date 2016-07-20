package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.R;
import com.example.dialog.DatePickerDialogFragment;
import com.example.dialog.ItemsDialogFragment;
import com.example.dialog.MultiChoiceItemsDialogFragment;
import com.example.dialog.ProgressDialogFragment;
import com.example.dialog.SheetDialogFragment;
import com.example.dialog.SimpleDialogFragment;
import com.example.dialog.SingleChoiceItemsDialogFragment;
import com.example.dialog.TimePickerDialogFragment;
import com.example.dialog.ViewDialogFragment;
import com.example.utility.Logcat;

import java.util.Calendar;


public class ExampleFragment extends TaskFragment implements
		SimpleDialogFragment.SimpleDialogListener,
		ItemsDialogFragment.ItemsDialogListener,
		SingleChoiceItemsDialogFragment.SingleChoiceItemsDialogListener,
		MultiChoiceItemsDialogFragment.MultiChoiceItemsDialogListener,
		ViewDialogFragment.ViewDialogListener,
		TimePickerDialogFragment.TimePickerDialogListener,
		DatePickerDialogFragment.DatePickerDialogListener,
		SheetDialogFragment.SheetDialogListener
{
	private static final String DIALOG_SIMPLE = "simple";
	private static final String DIALOG_ITEMS = "items";
	private static final String DIALOG_SINGLE_CHOICE_ITEMS = "single_choice_items";
	private static final String DIALOG_MULTI_CHOICE_ITEMS = "multi_choice_items";
	private static final String DIALOG_VIEW = "view";
	private static final String DIALOG_TIME_PICKER = "time_picker";
	private static final String DIALOG_DATE_PICKER = "date_picker";
	private static final String DIALOG_PROGRESS = "progress";
	private static final String DIALOG_SHEET = "sheet";

	private View mRootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		bindData();
	}


	@Override
	public void onSimpleDialogPositiveClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSimpleDialogPositiveClick()");
			}
		});
	}


	@Override
	public void onSimpleDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSimpleDialogNegativeClick()");
			}
		});
	}


	@Override
	public void onItemsDialogItemClick(final DialogFragment dialog, final int which)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onItemsDialogItemClick() = " + which);
			}
		});
	}


	@Override
	public void onItemsDialogPositiveClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onItemsDialogPositiveClick()");
			}
		});
	}


	@Override
	public void onItemsDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onItemsDialogNegativeClick()");
			}
		});
	}


	@Override
	public void onSingleChoiceItemsDialogPositiveClick(final DialogFragment dialog, final int checkedItem)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSingleChoiceItemsDialogPositiveClick() = " + checkedItem);
			}
		});
	}


	@Override
	public void onSingleChoiceItemsDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSingleChoiceItemsDialogNegativeClick()");
			}
		});
	}


	@Override
	public void onMultiChoiceItemsDialogPositiveClick(final DialogFragment dialog, final boolean[] checkedItems)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < checkedItems.length; i++)
				{
					builder.append(checkedItems[i]);
					builder.append(" ");
				}
				Logcat.d("onMultiChoiceItemsDialogPositiveClick() = " + builder.toString());
			}
		});
	}


	@Override
	public void onMultiChoiceItemsDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onMultiChoiceItemsDialogNegativeClick()");
			}
		});
	}


	@Override
	public void onViewDialogPositiveClick(final DialogFragment dialog, final String username, final String password)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onViewDialogPositiveClick() = " + username + " / " + password);
			}
		});
	}


	@Override
	public void onViewDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onViewDialogNegativeClick()");
			}
		});
	}


	@Override
	public void onTimePickerDialogPositiveClick(final DialogFragment dialog, final int hour, final int minute)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onTimePickerDialogPositiveClick() = " + hour + ":" + minute);
			}
		});
	}


	@Override
	public void onDatePickerDialogPositiveClick(final DialogFragment dialog, final int year, final int month, final int day)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onDatePickerDialogPositiveClick() = " + day + "." + (month + 1) + "." + year);
			}
		});
	}


	@Override
	public void onSheetDialogStateChanged(final DialogFragment dialog, final int newState, final String username, final String password)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSheetDialogStateChanged() = " + newState + " / " + username + " / " + password);
			}
		});
	}


	@Override
	public void onSheetDialogDismiss(final DialogFragment dialog, final String username, final String password)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("onSheetDialogDismiss() = " + username + " / " + password);
			}
		});
	}


	private void bindData()
	{
		// reference
		Button button1 = (Button) mRootView.findViewById(R.id.fragment_example_button1);
		Button button2 = (Button) mRootView.findViewById(R.id.fragment_example_button2);
		Button button3 = (Button) mRootView.findViewById(R.id.fragment_example_button3);
		Button button4 = (Button) mRootView.findViewById(R.id.fragment_example_button4);
		Button button5 = (Button) mRootView.findViewById(R.id.fragment_example_button5);
		Button button6 = (Button) mRootView.findViewById(R.id.fragment_example_button6);
		Button button7 = (Button) mRootView.findViewById(R.id.fragment_example_button7);
		Button button8 = (Button) mRootView.findViewById(R.id.fragment_example_button8);
		Button button9 = (Button) mRootView.findViewById(R.id.fragment_example_button9);

		// content
		button1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showSimpleDialog("hello");
			}
		});
		button2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showItemsDialog("hello");
			}
		});
		button3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showSingleChoiceItemsDialog(-1);
			}
		});
		button4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final boolean[] checkedItems = {
						true,
						false,
						true,
						false};
				showMultiChoiceItemsDialog(checkedItems);
			}
		});
		button5.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showViewDialog("hello");
			}
		});
		button6.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showTimePickerDialog(Calendar.getInstance());
			}
		});
		button7.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showDatePickerDialog(Calendar.getInstance());
			}
		});
		button8.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showProgressDialog();
			}
		});
		button9.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showSheetDialog("hello");
			}
		});
	}


	private void showSimpleDialog(String arg)
	{
		// create and show the dialog
		DialogFragment newFragment = SimpleDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_SIMPLE);
	}


	private void showItemsDialog(String arg)
	{
		// create and show the dialog
		DialogFragment newFragment = ItemsDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_ITEMS);
	}


	private void showSingleChoiceItemsDialog(int checkedItem)
	{
		// create and show the dialog
		DialogFragment newFragment = SingleChoiceItemsDialogFragment.newInstance(checkedItem);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_SINGLE_CHOICE_ITEMS);
	}


	private void showMultiChoiceItemsDialog(boolean checkedItems[])
	{
		// create and show the dialog
		DialogFragment newFragment = MultiChoiceItemsDialogFragment.newInstance(checkedItems);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_MULTI_CHOICE_ITEMS);
	}


	private void showViewDialog(String arg)
	{
		// create and show the dialog
		DialogFragment newFragment = ViewDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_VIEW);
	}


	private void showTimePickerDialog(Calendar time)
	{
		// create and show the dialog
		DialogFragment newFragment = TimePickerDialogFragment.newInstance(time);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_TIME_PICKER);
	}


	private void showDatePickerDialog(Calendar date)
	{
		// create and show the dialog
		DialogFragment newFragment = DatePickerDialogFragment.newInstance(date);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_DATE_PICKER);
	}


	private void showProgressDialog()
	{
		// create dialog
		DialogFragment newFragment = ProgressDialogFragment.newInstance();
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_PROGRESS);
	}


	private void hideProgressDialog()
	{
		getFragmentManager().executePendingTransactions();
		DialogFragment dialogFragment = (DialogFragment) getFragmentManager().findFragmentByTag(DIALOG_PROGRESS);
		if(dialogFragment != null) dialogFragment.dismiss();
	}


	private void showSheetDialog(String arg)
	{
		// create and show the dialog
		DialogFragment newFragment = SheetDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_SHEET);
	}
}
