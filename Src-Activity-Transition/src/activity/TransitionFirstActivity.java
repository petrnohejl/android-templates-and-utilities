package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.example.R;


public class TransitionFirstActivity extends ActionBarActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_transition_first);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_transition_first, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behaviour
		switch (item.getItemId())
		{
			case R.id.ab_button_transition_second:
				Intent intent = new Intent(this, TransitionSecondActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_enter, R.anim.push_left_exit);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
