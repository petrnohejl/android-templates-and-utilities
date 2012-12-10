package com.example.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.R;
import com.example.client.RequestManager;
import com.example.utility.FacebookUtility;
import com.example.utility.Logcat;
import com.example.utility.Preferences;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


public class FacebookFragment extends SherlockFragment
{
	private boolean mActionBarProgress = false;
	private View mRootView;
	private RequestManager mRequestManager = new RequestManager();
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncFacebookRunner;

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		// facebook init
		mFacebook = new Facebook(FacebookUtility.FACEBOOK_APP_ID);
		mAsyncFacebookRunner = new AsyncFacebookRunner(mFacebook);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.layout_facebook, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// load and show data
		renderView();

		// progress in action bar
		showActionBarProgress(mActionBarProgress);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		// facebook access token
		mFacebook.extendAccessTokenIfNeeded(getActivity(), null);
	}
	
	
	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}
	
	
	private void renderView()
	{
		Button buttonLogin = (Button) mRootView.findViewById(R.id.layout_facebook_login);
		Button buttonLogout = (Button) mRootView.findViewById(R.id.layout_facebook_logout);
		Button buttonProfile = (Button) mRootView.findViewById(R.id.layout_facebook_profile);
		Button buttonFriends = (Button) mRootView.findViewById(R.id.layout_facebook_friends);
		Button buttonWallPost = (Button) mRootView.findViewById(R.id.layout_facebook_wall_post);
		
		buttonLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(RequestManager.isOnline(getActivity()))
				{
					facebookAuthorize();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		buttonLogout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(RequestManager.isOnline(getActivity()))
				{
					facebookLogout();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		buttonProfile.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(RequestManager.isOnline(getActivity()))
				{
					facebookProfile();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		buttonFriends.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(RequestManager.isOnline(getActivity()))
				{
					facebookFriends();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		buttonWallPost.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(RequestManager.isOnline(getActivity()))
				{
					facebookWallPost();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	
	public void facebookActivityResult(int requestCode, int resultCode, Intent data)
	{
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
	
	
	public void facebookAuthorize()
	{
		// get existing access token if any
		Preferences preferences = new Preferences(getActivity());
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
				
		if(!mFacebook.isSessionValid())
		{
			// show progress in action bar
			showActionBarProgress(true);
						
			// facebook permissions: https://developers.facebook.com/docs/authentication/permissions/
			mFacebook.authorize(getActivity(), new String[] { "email", "user_birthday", "user_location" }, new DialogListener()
			{
				@Override
				public void onComplete(Bundle values)
				{
					// TODO: run callbacks in TaskSherlockFragment.runTaskCallback()

					Logcat.d("EXAMPLE", "fb.authorize.onComplete: " + mFacebook.getAccessToken());
					
					// save access token
					Preferences preferences = new Preferences(getActivity());
					preferences.setFacebookAccessToken(mFacebook.getAccessToken());
					preferences.setFacebookAccessExpiration(mFacebook.getAccessExpires());
					
					// hide progress in action bar
					if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);

					// toast
					Toast.makeText(getActivity(), "Successfully logged in.", Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onFacebookError(FacebookError e)
				{
					Logcat.d("EXAMPLE", "fb.authorize.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// hide progress in action bar
					if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onError(DialogError e)
				{
					Logcat.d("EXAMPLE", "fb.authorize.onError: "  + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// hide progress in action bar
					if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onCancel()
				{
					Logcat.d("EXAMPLE", "fb.authorize.onCancel");
					
					// hide progress in action bar
					if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are already logged in.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void facebookLogout()
	{
		// get existing access token if any
		Preferences preferences = new Preferences(getActivity());
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(mFacebook.isSessionValid())
		{
			// show progress in action bar
			showActionBarProgress(true);
			
			mAsyncFacebookRunner.logout(getActivity(), new RequestListener()
			{
				@Override
				public void onComplete(final String response, Object state)
				{
					// TODO: run callbacks in TaskSherlockFragment.runTaskCallback()

					Logcat.d("EXAMPLE", "fb.logout.onComplete: " + response);
					
					// clear access token
					Preferences preferences = new Preferences(getActivity());
					preferences.setFacebookAccessToken(Preferences.NULL_STRING);
					preferences.setFacebookAccessExpiration(Preferences.NULL_LONG);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), "Successfully logged out.", Toast.LENGTH_LONG).show();
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.logout.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.logout.onIOException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.logout.onFileNotFoundException");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.logout.onMalformedURLException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are already logged out.", Toast.LENGTH_LONG).show();
		}
	}
	

	public void facebookProfile()
	{
		// get existing access token if any
		Preferences preferences = new Preferences(getActivity());
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(mFacebook.isSessionValid())
		{
			// show progress in action bar
			showActionBarProgress(true);
			
			mAsyncFacebookRunner.request("me", new RequestListener()
			{
				@Override
				public void onComplete(final String response, Object state)
				{
					// TODO: run callbacks in TaskSherlockFragment.runTaskCallback()

					Logcat.d("EXAMPLE", "fb.profile.onComplete: " + response);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.profile.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.profile.onIOException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.profile.onFileNotFoundException");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.profile.onMalformedURLException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are logged out.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void facebookFriends()
	{
		// get existing access token if any
		Preferences preferences = new Preferences(getActivity());
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(mFacebook.isSessionValid())
		{
			// show progress in action bar
			showActionBarProgress(true);
			
			// parameters
			Bundle bundle = new Bundle();
			bundle.putString("fields", "id,name,gender");
			
			mAsyncFacebookRunner.request("me/friends", bundle, new RequestListener()
			{
				@Override
				public void onComplete(final String response, Object state)
				{
					// TODO: run callbacks in TaskSherlockFragment.runTaskCallback()

					Logcat.d("EXAMPLE", "fb.friends.onComplete: " + response);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.friends.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.friends.onIOException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.friends.onFileNotFoundException");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("EXAMPLE", "fb.friends.onMalformedURLException");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mRequestManager.getRequestsCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are logged out.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void facebookWallPost()
	{
		// get existing access token if any
		Preferences preferences = new Preferences(getActivity());
		String accessToken = preferences.getFacebookAccessToken();
		long expiration = preferences.getFacebookAccessExpiration();

		if(accessToken != null) mFacebook.setAccessToken(accessToken);
		if(expiration != 0) mFacebook.setAccessExpires(expiration);
		
		if(mFacebook.isSessionValid())
		{
			// parameters
			Bundle bundle = new Bundle();
			//bundle.putString("to", "123456789");
			bundle.putString("name", "Name");
			bundle.putString("description", "Description");
			bundle.putString("picture", "http://placedog.com/200/200");
			bundle.putString("link", "http://example.com");
			
			mFacebook.dialog(getActivity(), "feed", bundle, new DialogListener()
			{
				@Override
				public void onComplete(Bundle values)
				{
					// TODO: run callbacks in TaskSherlockFragment.runTaskCallback()
					
					Logcat.d("EXAMPLE", "fb.feed.onComplete: " + mFacebook.getAccessToken());
				}
	
				@Override
				public void onFacebookError(FacebookError e)
				{
					Logcat.d("EXAMPLE", "fb.feed.onFacebookError: " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onError(DialogError e)
				{
					Logcat.d("EXAMPLE", "fb.feed.onError: "  + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onCancel()
				{
					Logcat.d("EXAMPLE", "fb.feed.onCancel");
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are logged out.", Toast.LENGTH_LONG).show();
		}
	}
}
