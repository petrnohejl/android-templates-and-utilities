package com.example.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;


// source: http://stackoverflow.com/questions/8155257/slowing-speed-of-viewpager-controller-in-android/
public class FixedScroller extends Scroller
{
	private int mFixedDuration = 100;


	public static void setViewPagerDuration(ViewPager viewPager, int duration)
	{
		try
		{
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			
			FixedScroller fixedScroller = new FixedScroller(viewPager.getContext(), new DecelerateInterpolator());
			fixedScroller.setFixedDuration(duration);
			
			field.set(viewPager, fixedScroller);
		}
		catch(NoSuchFieldException e)
		{
		}
		catch(IllegalArgumentException e)
		{
		}
		catch(IllegalAccessException e)
		{
		}
	}


	public FixedScroller(Context context)
	{
		super(context);
	}


	public FixedScroller(Context context, Interpolator interpolator)
	{
		super(context, interpolator);
	}


	public FixedScroller(Context context, Interpolator interpolator, boolean flywheel)
	{
		super(context, interpolator, flywheel);
	}


	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration)
	{
		// Ignore received duration, use fixed one instead
		super.startScroll(startX, startY, dx, dy, mFixedDuration);
	}


	@Override
	public void startScroll(int startX, int startY, int dx, int dy)
	{
		// Ignore received duration, use fixed one instead
		super.startScroll(startX, startY, dx, dy, mFixedDuration);
	}


	public int getFixedDuration()
	{
		return mFixedDuration;
	}


	public void setFixedDuration(int duration)
	{
		this.mFixedDuration = duration;
	}
}
