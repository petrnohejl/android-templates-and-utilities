package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.activity.SimpleActivity;
import com.example.task.TaskFragment;


public class SimpleFragment extends TaskFragment
{
	private View mRootView;
	private int mId = -1;
	
	
	public static SimpleFragment newInstance(int arg)
	{
		SimpleFragment fragment = new SimpleFragment();
		
		// arguments
		Bundle args = new Bundle();
		args.putInt(SimpleActivity.EXTRA_PRODUCT_ID, arg);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(false); // fragment in second pane shouldn't be retained
		
		// handle intent extras
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
		}
		else
		{
			// handle arguments
			Bundle arguments = getArguments();
			if(arguments != null)
			{
				handleExtras(arguments);
			}
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
	
	
	private void handleExtras(Bundle extras)
	{
		mId = extras.getInt(SimpleActivity.EXTRA_PRODUCT_ID, -1);
	}
	
	
	private void renderView()
	{
		// reference
		TextView nameTextView = (TextView) mRootView.findViewById(R.id.fragment_simple_name);
		
		// content
		nameTextView.setText(Integer.toString(mId));
	}
}
