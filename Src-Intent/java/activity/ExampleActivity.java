package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;

import org.alfonz.utility.Logcat;


public class ExampleActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);

		Logcat.d("getIntent().getAction() = " + getIntent().getAction());
		Logcat.d("getIntent().getCategories() = " + (getIntent().getCategories() != null ? getIntent().getCategories() : "null"));
		Logcat.d("getIntent().getDataString() = " + getIntent().getDataString());
		Logcat.d("getIntent().getScheme() = " + getIntent().getScheme());
		Logcat.d("getIntent().getType() = " + getIntent().getType());
		Logcat.d("getIntent().getExtras() = " + (getIntent().getExtras() != null ? getIntent().getExtras().keySet() : "null"));
	}


	private void startNewTask()
	{
		Intent intent = new Intent(this, ExampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
