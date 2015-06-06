package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


// code taken from: http://stackoverflow.com/questions/3948934/synchronise-scrollview-scroll-positions-android
public class ObservableScrollView extends ScrollView
{
	private ScrollViewListener scrollViewListener = null;


	public interface ScrollViewListener
	{
		void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
	}


	public ObservableScrollView(Context context)
	{
		super(context);
	}


	public ObservableScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public ObservableScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	public void setOnScrollViewListener(ScrollViewListener scrollViewListener)
	{
		this.scrollViewListener = scrollViewListener;
	}


	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy)
	{
		super.onScrollChanged(x, y, oldx, oldy);
		if(scrollViewListener != null)
		{
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
}
