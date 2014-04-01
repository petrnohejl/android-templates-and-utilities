package com.example.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.R;
import com.example.utility.Preferences;


public class ExampleFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		addPreferencesFromResource(R.xml.prefs);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// register listener
		PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

		// render view
		renderView();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// unregister listener
		PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		renderView();
	}


	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
	{
		super.onPreferenceTreeClick(preferenceScreen, preference);

		// if the user has clicked on a preference screen, setup the action bar
		if(preference instanceof PreferenceScreen)
		{
			setupActionBar((PreferenceScreen) preference);
		}

		return false;
	}


	public static void setupActionBar(PreferenceScreen preferenceScreen)
	{
		final Dialog dialog = preferenceScreen.getDialog();
		if(dialog!=null)
		{
			// initialize the action bar
			dialog.getActionBar().setDisplayHomeAsUpEnabled(true);

			// apply custom home button area click listener to close the PreferenceScreen because PreferenceScreens are dialogs which swallow
			// events instead of passing to the activity, related issue: https://code.google.com/p/android/issues/detail?id=4611
			View homeButton = dialog.findViewById(android.R.id.home);
			if(homeButton!=null)
			{
				View.OnClickListener dismissDialogClickListener = new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
					}
				};

				// prepare yourselves for some hacky programming
				ViewParent homeButtonContainer = homeButton.getParent();

				// home button is an ImageView inside a FrameLayout
				if(homeButtonContainer instanceof FrameLayout)
				{
					ViewGroup containerParent = (ViewGroup) homeButtonContainer.getParent();

					if(containerParent instanceof LinearLayout)
					{
						// this view also contains the title text, set the whole view as clickable
						((LinearLayout) containerParent).setOnClickListener(dismissDialogClickListener);
					}
					else
					{
						// just set it on the home button
						((FrameLayout) homeButtonContainer).setOnClickListener(dismissDialogClickListener);
					}
				}
				else
				{
					// the 'if all else fails' default case
					homeButton.setOnClickListener(dismissDialogClickListener);
				}
			}
		}
	}


	private void renderView()
	{
		// references
		PreferenceScreen rootPreferenceScreen = getPreferenceScreen();
		EditTextPreference displayNameEditTextPreference = (EditTextPreference) findPreference(getString(R.string.prefs_key_display_name));

		// preferences
		Preferences preferences = new Preferences(getActivity());

		// summary
		displayNameEditTextPreference.setSummary(preferences.getDisplayName());
	}
}
