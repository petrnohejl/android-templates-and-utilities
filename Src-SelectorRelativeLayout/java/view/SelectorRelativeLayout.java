package com.example.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


// code taken from: http://stackoverflow.com/questions/26693675/recyclerview-how-to-simulate-listviews-draw-selector-on-top
public class SelectorRelativeLayout extends RelativeLayout
{
	private static final int[] ATTR_LIST_SELECTOR = {android.R.attr.listSelector};

	private final Drawable mSelector;


	public SelectorRelativeLayout(Context context)
	{
		this(context, null);
	}


	public SelectorRelativeLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}


	public SelectorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, ATTR_LIST_SELECTOR, 0, 0);
		mSelector = typedArray.getDrawable(0);
		typedArray.recycle();

		if(mSelector!=null)
		{
			setWillNotDraw(false);
			mSelector.setCallback(this);
		}
	}


	@Override
	protected void drawableStateChanged()
	{
		super.drawableStateChanged();

		final Drawable d = mSelector;
		if(d!=null && d.isStateful())
		{
			d.setState(getDrawableState());
		}
	}


	@Override
	public void jumpDrawablesToCurrentState()
	{
		super.jumpDrawablesToCurrentState();

		final Drawable d = mSelector;
		if(d!=null)
		{
			d.jumpToCurrentState();
		}
	}


	@Override
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void drawableHotspotChanged(float x, float y)
	{
		if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP)
		{
			return;
		}

		super.drawableHotspotChanged(x, y);

		final Drawable d = mSelector;
		if(d!=null)
		{
			d.setHotspot(x, y);
		}
	}


	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		final Drawable d = mSelector;
		if(d!=null)
		{
			d.setBounds(0, 0, getWidth(), getHeight());
			d.draw(canvas);
		}
	}


	@Override
	protected boolean verifyDrawable(Drawable who)
	{
		return who==mSelector || super.verifyDrawable(who);
	}
}
