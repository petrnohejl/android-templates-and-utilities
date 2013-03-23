package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.example.fragment.SimpleFragment;


public class TabsFragmentPagerAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener // TODO: use FragmentPagerAdapter or FragmentStatePagerAdapter
{
	private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	
	
	public TabsFragmentPagerAdapter(FragmentActivity activity, ActionBar actionBar, ViewPager viewPager)
	{
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mActionBar = actionBar;
		mViewPager = viewPager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}


	@Override
	public int getCount()
	{
		return mTabs.size();
	}


	@Override
	public Fragment getItem(int position)
	{
		//TabInfo info = mTabs.get(position);
		//return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		return SimpleFragment.newInstance(Integer.toString(position)); // TODO
	}
	
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		String title = "Fragment " + position;
		return title;
	}


	@Override
	public void onPageSelected(int position)
	{
		try
		{
			mActionBar.setSelectedNavigationItem(position);
		}
		catch(IllegalStateException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onPageScrollStateChanged(int state)
	{

	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{

	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{

	}
	
	
	public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) 
	{
		String tag = (String) tab.getTag();
		TabInfo info = new TabInfo(tag, clss, args);
		mTabs.add(info);
		mActionBar.addTab(tab.setTabListener(this));
		notifyDataSetChanged();
	}
	
	
	public void refill()
	{
		notifyDataSetChanged();
	}
	
	
	public static String getFragmentTag(int viewPagerId, int position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}
	
	
	public static final class TabInfo
	{
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		
		
		TabInfo(String tag, Class<?> clss, Bundle args)
		{
			this.tag = tag;
			this.clss = clss;
			this.args = args;
		}
	}
}
