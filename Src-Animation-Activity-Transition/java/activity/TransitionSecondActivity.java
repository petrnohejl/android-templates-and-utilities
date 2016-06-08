package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.R;


public class TransitionSecondActivity extends AppCompatActivity
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, TransitionSecondActivity.class);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition_second);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.activity_transition_second, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behavior
		switch(item.getItemId())
		{
			case R.id.menu_activity_transition_second_first:
				Intent intent = TransitionFirstActivity.newIntent(this);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_enter, R.anim.push_right_exit);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onBackPressed()
	{
		// back button
		super.onBackPressed();
		setResult(RESULT_CANCELED);
		finish();
		overridePendingTransition(R.anim.push_right_enter, R.anim.push_right_exit);
	}
}
