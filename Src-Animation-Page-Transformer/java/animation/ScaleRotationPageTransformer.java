package com.example.animation;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleRotationPageTransformer implements ViewPager.PageTransformer {
	private static final float MIN_SCALE = 0.75F;
	private static final float ANGLE = 20F;

	@Override
	public void transformPage(View view, float position) {
		float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
		view.setScaleX(scaleFactor);
		view.setScaleY(scaleFactor);
		view.setRotationY(position * -ANGLE);
		view.setAlpha(1 - Math.abs(position));
	}
}
