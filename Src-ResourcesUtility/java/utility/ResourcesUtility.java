package com.example.utility;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;


public final class ResourcesUtility
{
	private ResourcesUtility() {}


	public static int getValueOfAttribute(Context context, int attr)
	{
		TypedValue typedValue = new TypedValue();
		Resources.Theme theme = context.getTheme();
		theme.resolveAttribute(attr, typedValue, true);
		return typedValue.data;
	}


	public static int getColorValueOfAttribute(Context context, int attr)
	{
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		int value = typedArray.getColor(0, 0);
		typedArray.recycle();
		return value;
	}


	public static float getDimensionValueOfAttribute(Context context, int attr)
	{
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		float value = typedArray.getDimension(0, 0);
		typedArray.recycle();
		return value;
	}


	public static int getDimensionPixelSizeValueOfAttribute(Context context, int attr)
	{
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		int value = typedArray.getDimensionPixelSize(0, 0);
		typedArray.recycle();
		return value;
	}


	public static Drawable getDrawableValueOfAttribute(Context context, int attr)
	{
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		Drawable value = typedArray.getDrawable(0);
		typedArray.recycle();
		return value;
	}
}
