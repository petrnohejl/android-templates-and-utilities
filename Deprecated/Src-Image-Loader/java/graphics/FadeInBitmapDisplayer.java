package com.example.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class FadeInBitmapDisplayer implements BitmapDisplayer {
	private int mDurationMillis;

	public FadeInBitmapDisplayer(int durationMillis) {
		mDurationMillis = durationMillis;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		setImageBitmapWithFade(bitmap, imageAware.getWrappedView());
	}

	private void setImageBitmapWithFade(final Bitmap bitmap, final View view) {
		Resources resources = view.getResources();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
		setImageDrawableWithFade(bitmapDrawable, (ImageView) view);
	}

	private void setImageDrawableWithFade(final Drawable drawable, final ImageView imageView) {
		Drawable currentDrawable = imageView.getDrawable();
		if (currentDrawable != null) {
			Drawable[] drawableArray = new Drawable[2];
			drawableArray[0] = currentDrawable;
			drawableArray[1] = drawable;
			TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArray);
			transitionDrawable.setCrossFadeEnabled(true);
			imageView.setImageDrawable(transitionDrawable);
			transitionDrawable.startTransition(mDurationMillis);
		} else {
			imageView.setImageDrawable(drawable);
		}
	}
}
