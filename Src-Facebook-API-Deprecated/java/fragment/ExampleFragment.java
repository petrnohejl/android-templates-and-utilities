package com.example.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ExampleConfig;
import com.example.R;
import com.example.client.APICallManager;
import com.example.utility.Logcat;
import com.example.utility.NetworkManager;
import com.example.utility.Preferences;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


public class ExampleFragment extends Fragment
{
	private boolean mActionBarProgress = false;
	private View mRootView;
	private APICallManager mAPICallManager = new APICallManager();
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncFacebookRunner;

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		// facebook init
		mFacebook = new Facebook(ExampleConfig.FACEBOOK_APP_ID);
		mAsyncFacebookRunner = new AsyncFacebookRunner(mFacebook);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
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
					// TODO: run callbacks in TaskFragment.runTaskCallback()

					Logcat.d("Fragment.facebookAuthorize().onComplete(): " + mFacebook.getAccessToken());
					
					// save access token
					Preferences preferences = new Preferences(getActivity());
					preferences.setFacebookAccessToken(mFacebook.getAccessToken());
					preferences.setFacebookAccessExpiration(mFacebook.getAccessExpires());
					
					// hide progress in action bar
					if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);

					// toast
					Toast.makeText(getActivity(), "Successfully logged in.", Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onFacebookError(FacebookError e)
				{
					Logcat.d("Fragment.facebookAuthorize().onFacebookError(): " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// hide progress in action bar
					if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onError(DialogError e)
				{
					Logcat.d("Fragment.facebookAuthorize().onError(): " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// hide progress in action bar
					if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onCancel()
				{
					Logcat.d("Fragment.facebookAuthorize().onCancel()");
					
					// hide progress in action bar
					if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
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
					// TODO: run callbacks in TaskFragment.runTaskCallback()

					Logcat.d("Fragment.facebookLogout().onComplete(): " + response);
					
					// clear access token
					Preferences preferences = new Preferences(getActivity());
					preferences.setFacebookAccessToken(Preferences.NULL_STRING);
					preferences.setFacebookAccessExpiration(Preferences.NULL_LONG);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), "Successfully logged out.", Toast.LENGTH_LONG).show();
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("Fragment.facebookLogout().onFacebookError(): " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("Fragment.facebookLogout().onIOException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("Fragment.facebookLogout().onFileNotFoundException()");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("Fragment.facebookLogout().onMalformedURLException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
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
					// TODO: run callbacks in TaskFragment.runTaskCallback()

					Logcat.d("Fragment.facebookProfile().onComplete(): " + response);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("Fragment.facebookProfile().onFacebookError(): " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("Fragment.facebookProfile().onIOException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("Fragment.facebookProfile().onFileNotFoundException()");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("Fragment.facebookProfile().onMalformedURLException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
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
					// TODO: run callbacks in TaskFragment.runTaskCallback()

					Logcat.d("Fragment.facebookFriends().onComplete(): " + response);

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
						}
					});
				}
				
				@Override
				public void onFacebookError(final FacebookError e, Object state)
				{
					Logcat.d("Fragment.facebookFriends().onFacebookError(): " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onIOException(IOException e, Object state)
				{
					Logcat.d("Fragment.facebookFriends().onIOException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state)
				{
					Logcat.d("Fragment.facebookFriends().onFileNotFoundException()");
					
					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
							// toast
							Toast.makeText(getActivity(), R.string.global_server_fail_toast, Toast.LENGTH_LONG).show();
						}
					});
				}

				@Override
				public void onMalformedURLException(MalformedURLException e, Object state)
				{
					Logcat.d("Fragment.facebookFriends().onMalformedURLException()");

					getActivity().runOnUiThread(new Runnable()
					{
						public void run()
						{
							// hide progress in action bar
							if(mAPICallManager.getTasksCount()==0) showActionBarProgress(false);
							
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
					// TODO: run callbacks in TaskFragment.runTaskCallback()
					
					Logcat.d("Fragment.facebookWallPost().onComplete(): " + mFacebook.getAccessToken());
				}
	
				@Override
				public void onFacebookError(FacebookError e)
				{
					Logcat.d("Fragment.facebookWallPost().onFacebookError(): " + e.getErrorType() + " / " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onError(DialogError e)
				{
					Logcat.d("Fragment.facebookWallPost().onError(): " + e.getLocalizedMessage() + " / " + e.getMessage());
					
					// toast
					if(e.getMessage()!=null) Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
	
				@Override
				public void onCancel()
				{
					Logcat.d("Fragment.facebookWallPost().onCancel()");
				}
			});
		}
		else
		{
			Toast.makeText(getActivity(), "You are logged out.", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void showActionBarProgress(boolean visible)
	{
		// show action bar progress
		((AppCompatActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(visible);
		mActionBarProgress = visible;
	}
	
	
	private void renderView()
	{
		Button loginButton = (Button) mRootView.findViewById(R.id.fragment_example_login);
		Button logoutButton = (Button) mRootView.findViewById(R.id.fragment_example_logout);
		Button profileButton = (Button) mRootView.findViewById(R.id.fragment_example_profile);
		Button friendsButton = (Button) mRootView.findViewById(R.id.fragment_example_friends);
		Button wallPostButton = (Button) mRootView.findViewById(R.id.fragment_example_wall_post);
		
		loginButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(NetworkManager.isOnline(getActivity()))
				{
					facebookAuthorize();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(NetworkManager.isOnline(getActivity()))
				{
					facebookLogout();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		profileButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(NetworkManager.isOnline(getActivity()))
				{
					facebookProfile();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		friendsButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(NetworkManager.isOnline(getActivity()))
				{
					facebookFriends();
				}
				else
				{
					Toast.makeText(getActivity(), R.string.global_offline_toast, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		wallPostButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(NetworkManager.isOnline(getActivity()))
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
}
