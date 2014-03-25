package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;


public class ExampleFragment extends Fragment
{
	private static final String ARGUMENT_EXAMPLE = "example";
	
	private String mExample;
	private View mRootView;
	
	
	public static ExampleFragment newInstance(String example)
	{
		ExampleFragment fragment = new ExampleFragment();
		
		// arguments
		Bundle arguments = new Bundle();
		arguments.putString(ARGUMENT_EXAMPLE, example);
		fragment.setArguments(arguments);
		
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
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
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
		if(arguments.containsKey(ARGUMENT_EXAMPLE))
		{
			mExample = (String) arguments.get(ARGUMENT_EXAMPLE);
		}
	}
	
	
	private void renderView()
	{
		// reference
		TextView nameTextView = (TextView) mRootView.findViewById(R.id.fragment_example_name);
		
		// content
		nameTextView.setText("Fragment " + mExample);
	}
}
