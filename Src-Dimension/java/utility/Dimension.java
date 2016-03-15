package com.example.utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Dimension {

    public static float dp2px(final Context context, final  float dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static float sp2px(final Context context, final  float sp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, dm);
    }
}