package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.R;


// code taken from: http://stackoverflow.com/questions/5875877/setting-a-maximum-width-on-a-viewgroup
public class MaxWidthLinearLayout extends LinearLayout
{
	private int mMaxWidth;


	public MaxWidthLinearLayout(Context context)
	{
		super(context);
		mMaxWidth = 0;
	}


	public MaxWidthLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaxWidthLinearLayout);
		mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.MaxWidthLinearLayout_maxWidth, Integer.MAX_VALUE);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		if(mMaxWidth>0 && mMaxWidth<measuredWidth)
		{
			int measureMode = MeasureSpec.getMode(widthMeasureSpec);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
