package com.example.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.dialog.SimpleDialogFragment;
import com.example.dialog.SingleChoiceItemsDialogFragment;
import com.example.dialog.TimePickerDialogFragment;
import com.example.dialog.ViewDialogFragment;
import com.example.task.TaskSherlockFragment;
import com.example.utility.Logcat;


public class ExampleFragment extends TaskSherlockFragment implements
		SimpleDialogFragment.SimpleDialogListener,
		ItemsDialogFragment.ItemsDialogListener,
		SingleChoiceItemsDialogFragment.SingleChoiceItemsDialogListener,
		MultiChoiceItemsDialogFragment.MultiChoiceItemsDialogListener,
		ViewDialogFragment.ViewDialogListener,
		TimePickerDialogFragment.TimePickerDialogListener,
		DatePickerDialogFragment.DatePickerDialogListener
{
	private final String DIALOG_SIMPLE = "simple";
	private final String DIALOG_ITEMS = "items";
	private final String DIALOG_SINGLE_CHOICE_ITEMS = "single_choice_items";
	private final String DIALOG_MULTI_CHOICE_ITEMS = "multi_choice_items";
	private final String DIALOG_VIEW = "view";
	private final String DIALOG_TIME_PICKER = "time_picker";
	private final String DIALOG_DATE_PICKER = "date_picker";
	private final String DIALOG_PROGRESS = "progress";
	
	private View mRootView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}
	
	
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
		
		renderView();
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogPositiveClick()");
			}
		});
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog, final int which)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogPositiveClick(): " + which);
			}
		});
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog, final boolean checkedItems[])
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				StringBuilder builder = new StringBuilder();
				for(int i=0; i<checkedItems.length; i++)
				{
					builder.append(checkedItems[i]);
					builder.append(" ");
				}
				Logcat.d("Fragment.onDialogPositiveClick(): " + builder.toString());
			}
		});
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog, final String username, final String password)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogPositiveClick(): " + username + " / " + password);
			}
		});
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog, final int hour, final int minute)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogPositiveClick(): " + hour + ":" + minute);
			}
		});
	}
	
	
	@Override
	public void onDialogPositiveClick(final DialogFragment dialog, final int year, final int month, final int day)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogPositiveClick(): " + day + "." + (month+1) + "." + year);
			}
		});
	}
	
	
	@Override
	public void onDialogNegativeClick(final DialogFragment dialog)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogNegativeClick()");
			}
		});
	}
	
	
	@Override
	public void onDialogItemClick(final DialogFragment dialog, final int which)
	{
		runTaskCallback(new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("Fragment.onDialogItemClick(): " + which);
			}
		});
	}
	
	
	private void renderView()
	{
		// reference
		Button button1 = (Button) mRootView.findViewById(R.id.fragment_example_button1);
		Button button2 = (Button) mRootView.findViewById(R.id.fragment_example_button2);
		Button button3 = (Button) mRootView.findViewById(R.id.fragment_example_button3);
		Button button4 = (Button) mRootView.findViewById(R.id.fragment_example_button4);
		Button button5 = (Button) mRootView.findViewById(R.id.fragment_example_button5);
		Button button6 = (Button) mRootView.findViewById(R.id.fragment_example_button6);
		Button button7 = (Button) mRootView.findViewById(R.id.fragment_example_button7);
		
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
						false };
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
	}
	
	
	private void showSimpleDialog(String arg)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_SIMPLE);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = SimpleDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_SIMPLE);
	}
	
	
	private void showItemsDialog(String arg)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_ITEMS);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = ItemsDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_ITEMS);
	}
	
	
	private void showSingleChoiceItemsDialog(int checkedItem)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_SINGLE_CHOICE_ITEMS);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = SingleChoiceItemsDialogFragment.newInstance(checkedItem);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_SINGLE_CHOICE_ITEMS);
	}
	
	
	private void showMultiChoiceItemsDialog(boolean checkedItems[])
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_MULTI_CHOICE_ITEMS);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = MultiChoiceItemsDialogFragment.newInstance(checkedItems);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_MULTI_CHOICE_ITEMS);
	}
	
	
	private void showViewDialog(String arg)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_VIEW);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = ViewDialogFragment.newInstance(arg);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_VIEW);
	}
	
	
	private void showTimePickerDialog(Calendar time)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TIME_PICKER);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = TimePickerDialogFragment.newInstance(time);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_TIME_PICKER);
	}
	
	
	private void showDatePickerDialog(Calendar date)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_DATE_PICKER);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create and show the dialog
		DialogFragment newFragment = DatePickerDialogFragment.newInstance(date);
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_DATE_PICKER);
	}
	
	
	private void showProgressDialog()
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_PROGRESS);
		if(prev != null) transaction.remove(prev);
		transaction.addToBackStack(null);
		
		// create dialog
		DialogFragment newFragment = ProgressDialogFragment.newInstance();
		newFragment.setTargetFragment(this, 0);
		newFragment.show(transaction, DIALOG_PROGRESS);
	}
	
	
	private void hideProgressDialog()
	{
		getFragmentManager().executePendingTransactions();
		DialogFragment dialogFragment = (DialogFragment) getFragmentManager().findFragmentByTag(DIALOG_PROGRESS); 
		if(dialogFragment != null) dialogFragment.dismiss();
	}
}
