package com.example.fragment;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;


public class ExampleFragment extends Fragment
{
	private View mRootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	private void setupLayoutTransition(ViewGroup layout)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			LayoutTransition transition = layout.getLayoutTransition();
			if(transition == null)
			{
				transition = new LayoutTransition();
				layout.setLayoutTransition(transition);
			}
			transition.enableTransitionType(LayoutTransition.CHANGING);
		}
	}
}
