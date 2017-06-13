package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.activity.SimpleActivity;


public class SimpleFragment extends Fragment
{
	private static final String ARGUMENT_PRODUCT_ID = "product_id";

	private View mRootView;
	private int mId = -1;


	public static SimpleFragment newInstance(int productId)
	{
		SimpleFragment fragment = new SimpleFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_PRODUCT_ID, productId);
		fragment.setArguments(arguments);

		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			handleArguments(arguments);
		}

		// handle intent extras
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras != null)
		{
			handleExtras(extras);
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
		setupView();
	}


	private void handleArguments(Bundle arguments)
	{
		if(arguments.containsKey(ARGUMENT_PRODUCT_ID))
		{
			mId = arguments.getInt(ARGUMENT_PRODUCT_ID);
		}
	}


	private void handleExtras(Bundle extras)
	{
		if(extras.containsKey(SimpleActivity.EXTRA_PRODUCT_ID))
		{
			mId = extras.getInt(SimpleActivity.EXTRA_PRODUCT_ID);
		}
	}


	private void setupView()
	{
		// reference
		TextView nameTextView = (TextView) mRootView.findViewById(R.id.fragment_simple_name);

		// content
		nameTextView.setText(Integer.toString(mId));
	}
}
