package com.example.activity;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.example.R;


public class TransitionFirstActivity extends SherlockFragmentActivity
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
		MenuInflater menuInflater = new MenuInflater(this);
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
