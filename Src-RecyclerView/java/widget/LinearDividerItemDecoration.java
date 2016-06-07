package com.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


// source: https://gist.github.com/lapastillaroja/858caf1a82791b6c1a36
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration
{
	private Drawable mDivider;
	private boolean mShowFirstDivider = false;
	private boolean mShowLastDivider = false;


	public LinearDividerItemDecoration(Context context, AttributeSet attrs)
	{
		final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
		mDivider = a.getDrawable(0);
		a.recycle();
	}


	public LinearDividerItemDecoration(Context context, AttributeSet attrs, boolean showFirstDivider, boolean showLastDivider)
	{
		this(context, attrs);
		mShowFirstDivider = showFirstDivider;
		mShowLastDivider = showLastDivider;
	}


	public LinearDividerItemDecoration(Drawable divider)
	{
		mDivider = divider;
	}


	public LinearDividerItemDecoration(Drawable divider, boolean showFirstDivider, boolean showLastDivider)
	{
		this(divider);
		mShowFirstDivider = showFirstDivider;
		mShowLastDivider = showLastDivider;
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state)
	{
		super.getItemOffsets(outRect, view, recyclerView, state);

		if(mDivider==null)
		{
			return;
		}

		if(recyclerView.getChildLayoutPosition(view)<1)
		{
			return;
		}

		if(getOrientation(recyclerView)==LinearLayoutManager.VERTICAL)
		{
			outRect.top = mDivider.getIntrinsicHeight();
		}
		else
		{
			outRect.left = mDivider.getIntrinsicWidth();
		}
	}


	@Override
	public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state)
	{
		if(mDivider==null)
		{
			super.onDrawOver(canvas, recyclerView, state);
			return;
		}

		// initialization
		int left = 0, right = 0, top = 0, bottom = 0, size;
		int orientation = getOrientation(recyclerView);
		int childCount = recyclerView.getChildCount();

		if(orientation==LinearLayoutManager.VERTICAL)
		{
			size = mDivider.getIntrinsicHeight();
			left = recyclerView.getPaddingLeft();
			right = recyclerView.getWidth() - recyclerView.getPaddingRight();
		}
		else
		{
			size = mDivider.getIntrinsicWidth();
			top = recyclerView.getPaddingTop();
			bottom = recyclerView.getHeight() - recyclerView.getPaddingBottom();
		}

		for(int i = mShowFirstDivider ? 0 : 1; i<childCount; i++)
		{
			View child = recyclerView.getChildAt(i);
			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

			if(orientation==LinearLayoutManager.VERTICAL)
			{
				top = child.getTop() - params.topMargin - size;
				bottom = top + size;
			}
			else
			{
				left = child.getLeft() - params.leftMargin - size;
				right = left + size;
			}

			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(canvas);
		}

		// show last divider
		if(mShowLastDivider && childCount>0)
		{
			View child = recyclerView.getChildAt(childCount - 1);
			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

			if(orientation==LinearLayoutManager.VERTICAL)
			{
				top = child.getBottom() + params.bottomMargin;
				bottom = top + size;
			}
			else
			{
				left = child.getRight() + params.rightMargin;
				right = left + size;
			}

			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(canvas);
		}
	}


	private int getOrientation(RecyclerView recyclerView)
	{
		if(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
		{
			LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
			return linearLayoutManager.getOrientation();
		}
		else
		{
			throw new IllegalStateException(this.getClass().getSimpleName() + " can only be used with a " + LinearLayoutManager.class.getSimpleName());
		}
	}
}
