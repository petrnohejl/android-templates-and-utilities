package com.example.utility;

import android.content.Context;
import android.content.res.Resources;
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
}
