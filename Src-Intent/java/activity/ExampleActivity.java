package com.example.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;
import com.example.utility.Logcat;


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


	private void startWebActivity(String url)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startStoreActivity()
	{
		try
		{
			String uri = getString(R.string.app_store_uri, getApplicationContext().getPackageName());
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startMapCoordinatesActivity(double lat, double lon, String label)
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("geo:");
			builder.append(lat);
			builder.append(",");
			builder.append(lon);
			builder.append("?z=16"); // zoom value: 2..23
			builder.append("&q="); // query allows to show pin
			builder.append(Uri.encode(lat + "," + lon + "(" + label + ")"));

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.toString()));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startMapSearchActivity(String query)
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("geo:0,0?q=");
			builder.append(Uri.encode(query));

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.toString()));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startNavigateActivity(double lat, double lon)
	{
		try
		{
			String uri = String.format("http://maps.google.com/maps?daddr=%s,%s", Double.toString(lat), Double.toString(lon));
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startSmsActivity(String phoneNumber, String text)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setType("vnd.android-dir/mms-sms");
			intent.putExtra("address", phoneNumber);
			intent.putExtra("sms_body", text);
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startShareActivity(String chooserTitle, String subject, String text)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
			intent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(Intent.createChooser(intent, chooserTitle));
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startEmailActivity(String email, String subject, String text)
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("mailto:");
			builder.append(email);

			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(builder.toString()));
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
			intent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startCalendarActivity(String title, String description, long beginTime, long endTime)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("title", title);
			intent.putExtra("description", description);
			intent.putExtra("beginTime", beginTime); // time in milliseconds
			intent.putExtra("endTime", endTime);
			startActivity(intent);
		}
		catch(android.content.ActivityNotFoundException e)
		{
			// can't start activity
		}
	}


	private void startCallActivity(String phoneNumber)
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("tel:");
			builder.append(phoneNumber);

			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(builder.toString()));
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


	private void startNewTask()
	{
		Intent intent = new Intent(this, ExampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
