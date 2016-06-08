package com.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


public class GridDividerItemDecoration extends RecyclerView.ItemDecoration
{
	private Drawable mDivider;
	private int mSpacing;


	public GridDividerItemDecoration(Context context, AttributeSet attrs, int spacingPixelSize)
	{
		final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
		mDivider = a.getDrawable(0);
		a.recycle();
		mSpacing = spacingPixelSize;
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state)
	{
		outRect.set(mSpacing, mSpacing, mSpacing, mSpacing);
	}


	@Override
	public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state)
	{
		drawHorizontal(canvas, recyclerView);
		drawVertical(canvas, recyclerView);
	}


	private void drawHorizontal(Canvas canvas, RecyclerView recyclerView)
	{
		if(recyclerView.getChildCount() == 0) return;
		final int left = recyclerView.getPaddingLeft();
		final int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
		final View child = recyclerView.getChildAt(0);
		if(child.getHeight() == 0) return;
		final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
		int top = child.getBottom() + params.bottomMargin + mSpacing;
		int bottom = top + mDivider.getIntrinsicHeight();
		final int parentBottom = recyclerView.getHeight() - recyclerView.getPaddingBottom();
		while(bottom < parentBottom)
		{
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(canvas);
			top += mSpacing + params.topMargin + child.getHeight() + params.bottomMargin + mSpacing;
			bottom = top + mDivider.getIntrinsicHeight();
		}
	}


	private void drawVertical(Canvas canvas, RecyclerView recyclerView)
	{
		final int top = recyclerView.getPaddingTop();
		final int bottom = recyclerView.getHeight() - recyclerView.getPaddingBottom();
		final int childCount = recyclerView.getChildCount();
		for(int i = 0; i < childCount; i++)
		{
			final View child = recyclerView.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int left = child.getRight() + params.rightMargin + mSpacing;
			final int right = left + mDivider.getIntrinsicWidth();
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(canvas);
		}
	}
}
