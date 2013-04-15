package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.R;


public class SimpleFragment extends SherlockFragment
{
	public static final String EXTRA_ARG = "arg";
	
	private String mArg;
	private View mRootView;
	
	
	public static SimpleFragment newInstance(String arg)
	{
		SimpleFragment fragment = new SimpleFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putString(EXTRA_ARG, arg);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		// handle fragment arguments
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			handleArguments(arguments);
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_simple, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		renderView();
	}
	
	
	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(EXTRA_ARG))
		{
			mArg = (String) arguments.get(EXTRA_ARG);
		}
	}
	
	
	private void renderView()
	{
		// reference
		TextView nameTextView = (TextView) mRootView.findViewById(R.id.fragment_simple_content_name);
		
		// content
		nameTextView.setText("Fragment " + mArg);
	}
}
