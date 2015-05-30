package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;


public class ExampleActivity extends AppCompatActivity
{
	public static final String EXTRA_PRODUCT_ID = "product_id";
	public static final String EXTRA_PRODUCT_TITLE = "product_title";

	private static final String SAVED_PAGER_POSITION = "pager_position";


	public static Intent newIntent(Context context, String productId, String productTitle)
	{
		Intent intent = new Intent(context, ExampleActivity.class);

		// extras
		intent.putExtra(EXTRA_PRODUCT_ID, productId);
		intent.putExtra(EXTRA_PRODUCT_TITLE, productTitle);

		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);

		// restore saved state
		if(savedInstanceState != null)
		{
			handleSavedInstanceState(savedInstanceState);
		}

		// handle intent extras
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
		}
	}


	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);

		// TODO
	}


	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// restore saved state
		super.onRestoreInstanceState(savedInstanceState);

		if(savedInstanceState != null)
		{
			// TODO
		}
	}


	private void handleSavedInstanceState(Bundle savedInstanceState)
	{
		// TODO
	}


	private void handleExtras(Bundle extras)
	{
		// TODO
	}
}
