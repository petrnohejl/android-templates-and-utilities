package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;

import org.alfonz.utility.Logcat;


public class ExampleActivity extends AppCompatActivity
{
	public static final int REQUEST_PICK_ITEM = 1;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == REQUEST_PICK_ITEM)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				Logcat.d("ok");
			}
			else if(resultCode == Activity.RESULT_CANCELED)
			{
				Logcat.d("canceled");
			}
		}
	}


	private void startPickerActivity()
	{
		Intent intent = PickerActivity.newIntent(this);
		startActivityForResult(intent, REQUEST_PICK_ITEM);
	}
}
