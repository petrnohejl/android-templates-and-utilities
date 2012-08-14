package com.example.facebooktest.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.example.facebooktest.R;
import com.example.facebooktest.utility.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


public class FacebookTestActivity extends Activity
{
	public static final String FACEBOOK_APP_ID = "0123456789";
	
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncFacebookRunner;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_test);
		
		// facebook init
		mFacebook = new Facebook(FACEBOOK_APP_ID);
		mAsyncFacebookRunner = new AsyncFacebookRunner(mFacebook);
		
		renderView();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		mFacebook.extendAccessTokenIfNeeded(this, null);
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
	
	
	private void facebookAuthorize()
	{
		// get existing access_token if any
		Preferences preferences = new Preferences(this);
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(!mFacebook.isSessionValid())
		{
			// https://developers.facebook.com/docs/authentication/permissions/
			mFacebook.authorize(this, new String[] { "email", "user_birthday", "user_location" }, new DialogListener()
			{
				@Override
				public void onComplete(Bundle values)
				{
					Preferences preferences = new Preferences(FacebookTestActivity.this);
					preferences.setFacebookAccessToken(mFacebook.getAccessToken());
					preferences.setFacebookAccessExpiration(mFacebook.getAccessExpires());
					
					Toast.makeText(FacebookTestActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();

					Log.d("FB", "authorize.onComplete: " + mFacebook.getAccessToken());
				}
	
				@Override
				public void onFacebookError(FacebookError e)
				{
					Log.d("FB", "authorize.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
				}
	
				@Override
				public void onError(DialogError e)
				{
					Log.d("FB", "authorize.onError: "  + e.getLocalizedMessage() + " / " + e.getMessage());
				}
	
				@Override
				public void onCancel()
				{
					Log.d("FB", "authorize.onCancel");
				}
			});
		}
		else
		{
			Toast.makeText(FacebookTestActivity.this, "You are already logged in.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void facebookLogout()
	{
		// get existing access_token if any
		Preferences preferences = new Preferences(this);
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(mFacebook.isSessionValid())
		{
			mAsyncFacebookRunner.logout(this, new RequestListener()
			{
				@Override
				public void onComplete(String response, Object state)
				{
					Preferences preferences = new Preferences(FacebookTestActivity.this);
					preferences.setFacebookAccessToken(Preferences.NULL_STRING);
					preferences.setFacebookAccessExpiration(Preferences.NULL_LONG);
					
					runOnUiThread(new Runnable()
					{
						public void run()
						{
							Toast.makeText(FacebookTestActivity.this, "Successfully logged out.", Toast.LENGTH_LONG).show();
						}
					});
	
					Log.d("FB", "logout.onComplete: " + response);
				}
				
				@Override
				public void onFacebookError(FacebookError e, Object state)
				{
					Log.d("FB", "logout.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
				}
	
				@Override
				public void onIOException(IOException e, Object state)
				{
					Log.d("FB", "logout.onIOException");
				}
	
				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Log.d("FB", "logout.onFileNotFoundException");
				}
	
				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Log.d("FB", "logout.onMalformedURLException");
				}
			});
		}
		else
		{
			Toast.makeText(FacebookTestActivity.this, "You are already logged out.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void facebookRequest()
	{
		mAsyncFacebookRunner.request("me", new RequestListener()
		{
			@Override
			public void onComplete(String response, Object state)
			{
				Log.d("FB", "request.onComplete: " + response);
			}
			
			@Override
			public void onFacebookError(FacebookError e, Object state)
			{
				Log.d("FB", "request.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
			}

			@Override
			public void onIOException(IOException e, Object state)
			{
				Log.d("FB", "request.onIOException");
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e, Object state)
			{
				Log.d("FB", "request.onFileNotFoundException");
			}

			@Override
			public void onMalformedURLException(MalformedURLException e, Object state)
			{
				Log.d("FB", "request.onMalformedURLException");
			}
		});
	}
	
	
	private void renderView()
	{
		Button login = (Button) findViewById(R.id.login);
		Button logout = (Button) findViewById(R.id.logout);
		Button request = (Button) findViewById(R.id.request);
		
		login.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO: check online state
				facebookAuthorize();
			}
		});
		
		logout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO: check online state
				facebookLogout();
			}
		});
		
		request.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO: check online state
				facebookRequest();
			}
		});
	}
}
