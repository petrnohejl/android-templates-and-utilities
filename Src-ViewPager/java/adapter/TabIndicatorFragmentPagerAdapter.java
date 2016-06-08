package com.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.ExampleFragment;


public class TabIndicatorFragmentPagerAdapter extends FragmentPagerAdapter // TODO: use FragmentPagerAdapter or FragmentStatePagerAdapter
{
	private static final int FRAGMENT_COUNT = 8;


	public TabIndicatorFragmentPagerAdapter(FragmentManager fragmentManager)
	{
		super(fragmentManager);
	}


	public static String getFragmentTag(int viewPagerId, int position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}


	@Override
	public int getCount()
	{
		return FRAGMENT_COUNT;
	}


	@Override
	public Fragment getItem(int position)
	{
		return ExampleFragment.newInstance(Integer.toString(position));
	}


	@Override
	public CharSequence getPageTitle(int position)
	{
		String title = "Fragment " + position;
		return title;
	}


	public void refill()
	{
		notifyDataSetChanged();
	}
}
