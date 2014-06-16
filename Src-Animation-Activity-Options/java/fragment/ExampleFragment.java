package com.example.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.activity.ExampleActivity;


public class ExampleFragment extends Fragment
{
	private View mRootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	private void startExampleActivity()
	{
		Intent intent = ExampleActivity.newIntent(getActivity());
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.push_right_enter, R.anim.push_right_exit);
			((ActionBarActivity) getActivity()).startActivity(intent, options.toBundle());
		}
		else
		{
			startActivity(intent);
		}
	}


	private void startExampleActivity(View view)
	{
		Intent intent = ExampleActivity.newIntent(getActivity());
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
			((ActionBarActivity) getActivity()).startActivity(intent, options.toBundle());
		}
		else
		{
			startActivity(intent);
		}
	}
}
