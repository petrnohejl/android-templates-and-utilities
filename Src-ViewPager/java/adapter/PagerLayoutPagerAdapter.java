package com.example.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;

public class PagerLayoutPagerAdapter extends PagerAdapter {
	private static final int LAYOUT_COUNT = 8;

	private Context mContext;

	public PagerLayoutPagerAdapter(Context context) {
		mContext = context;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_example, container, false);
		container.addView(layout);
		return layout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public int getCount() {
		return LAYOUT_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Layout " + position;
	}

	public void refill() {
		notifyDataSetChanged();
	}
}
