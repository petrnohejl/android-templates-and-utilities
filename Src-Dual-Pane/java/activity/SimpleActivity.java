package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;


public class SimpleActivity extends AppCompatActivity
{
	public static final String EXTRA_PRODUCT_ID = "product_id";


	public static Intent newIntent(Context context, int productId)
	{
		Intent intent = new Intent(context, SimpleActivity.class);

		// extras
		intent.putExtra(EXTRA_PRODUCT_ID, productId);

		return intent;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// if activity has dual pane layout, we don't need this activity
		if(getResources().getBoolean(R.bool.dual_pane))
		{
			finish();
			return;
		}
		
		setContentView(R.layout.activity_simple);
	}
}
