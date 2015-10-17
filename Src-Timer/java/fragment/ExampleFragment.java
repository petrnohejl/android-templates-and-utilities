package com.example.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.utility.Logcat;


public class ExampleFragment extends Fragment
{
	private static final long TIMER_DELAY = 5000L; // in milliseconds
	
	private View mRootView;
	private Handler mTimerHandler;
	private Runnable mTimerRunnable;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setupTimer();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		// timer
		startTimer();
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		// timer
		stopTimer();
	}


	private void setupTimer()
	{
		mTimerHandler = new Handler();
		mTimerRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				Logcat.d("timer");

				mTimerHandler.postDelayed(this, TIMER_DELAY);
			}
		};
	}
	
	
	private void startTimer()
	{
		mTimerHandler.postDelayed(mTimerRunnable, 0);
	}
	
	
	private void stopTimer()
	{
		mTimerHandler.removeCallbacks(mTimerRunnable);
	}
}
