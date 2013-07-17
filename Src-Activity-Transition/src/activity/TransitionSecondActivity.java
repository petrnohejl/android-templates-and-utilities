package com.example.activity;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.example.R;


public class TransitionSecondActivity extends SherlockFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_transition_second);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = new MenuInflater(this);
		menuInflater.inflate(R.menu.menu_transition_second, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behaviour
		switch (item.getItemId())
		{
			case R.id.ab_button_transition_first:
				Intent intent = new Intent(this, TransitionFirstActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
