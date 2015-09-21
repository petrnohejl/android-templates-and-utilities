package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.fragment.ExampleFragment;


public class TabHostFragmentPagerAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener // TODO: use FragmentPagerAdapter or FragmentStatePagerAdapter
{
	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final List<TabInfo> mTabList = new ArrayList<>();


	public static String getFragmentTag(int viewPagerId, int position)
	{
		return "android:switcher:" + viewPagerId + ":" + position;
	}


	public TabHostFragmentPagerAdapter(FragmentActivity activity, TabHost tabHost, ViewPager viewPager)
	{
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mTabHost = tabHost;
		mViewPager = viewPager;
		mTabHost.setOnTabChangedListener(this);
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}


	@Override
	public int getCount()
	{
		return mTabList.size();
	}


	@Override
	public Fragment getItem(int position)
	{
		//TabInfo info = mTabList.get(position);
		//return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		return ExampleFragment.newInstance(Integer.toString(position)); // TODO
	}
	
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		String title = "Fragment " + position;
		return title;
	}

	
	@Override
	public void onTabChanged(String tabId)
	{
		int position = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(position);
	}


	@Override
	public void onPageSelected(int position)
	{
		// this hack tries to prevent this from pulling focus out of our ViewPager
		TabWidget widget = mTabHost.getTabWidget();
		int oldFocusability = widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		mTabHost.setCurrentTab(position);
		widget.setDescendantFocusability(oldFocusability);
	}

	
	@Override
	public void onPageScrollStateChanged(int state)
	{
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
	}
	
	
	public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
	{
		tabSpec.setContent(new DummyTabFactory(mContext));
		String tag = tabSpec.getTag();
		TabInfo info = new TabInfo(tag, clss, args);
		mTabList.add(info);
		mTabHost.addTab(tabSpec);
		notifyDataSetChanged();
	}
	
	
	public void refill()
	{
		notifyDataSetChanged();
	}
	
	
	public static final class TabInfo
	{
		private final String mTag;
		private final Class<?> mClss;
		private final Bundle mArgs;
		
		
		TabInfo(String tag, Class<?> clss, Bundle args)
		{
			mTag = tag;
			mClss = clss;
			mArgs = args;
		}
	}
	
	
	public static class DummyTabFactory implements TabHost.TabContentFactory
	{
		private final Context mContext;


		public DummyTabFactory(Context context)
		{
			mContext = context;
		}


		@Override
		public View createTabContent(String tag)
		{
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
}
