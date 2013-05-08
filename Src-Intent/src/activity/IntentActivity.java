package com.example.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.example.R;


public class IntentActivity extends SherlockFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_intent);
	}
	
	
	private void startWebActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://petrnohejl.cz"));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startMarketActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_uri)));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startMapCoordinatesActivity()
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("geo:");
			builder.append("49.123456");
			builder.append(",");
			builder.append("16.123456");
			builder.append("?z=16"); // zoom value: 2..23
			builder.append("&q="); // query allows to show pin
			builder.append(lat);
			builder.append(",");
			builder.append(lon);
			
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(builder.toString()));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startMapSearchActivity()
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("geo:0,0?q=");
			builder.append("svata+hora");
			
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(builder.toString()));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startSmsActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
			intent.setType("vnd.android-dir/mms-sms");
			intent.putExtra("address", "+420123456789");
			intent.putExtra("sms_body", "Body");
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startShareActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
			startActivity(Intent.createChooser(intent, "Share"));
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startEmailActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "email@email.com" });
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
			startActivity(Intent.createChooser(intent, "Feedback"));
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startCalendarActivity()
	{
		try
		{
			Intent intent = new Intent(android.content.Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("title", "Title");
			intent.putExtra("description", "Description");
			intent.putExtra("beginTime", 1400000000000l);
			intent.putExtra("endTime", 1400010000000l);
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startCallActivity()
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("tel:");
			builder.append("+420123456789");
			
			Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse(builder.toString()));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
	
	
	private void startSettingsActivity()
	{
		try
		{
			startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}
}
