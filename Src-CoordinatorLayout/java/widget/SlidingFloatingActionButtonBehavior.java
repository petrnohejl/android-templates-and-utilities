package com.example.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import org.alfonz.utility.ResourcesUtility;

public class SlidingFloatingActionButtonBehavior extends FloatingActionButton.Behavior {
	private int mActionBarSize;
	private float mSnackbarTranslation = 0;

	public SlidingFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActionBarSize = ResourcesUtility.getDimensionPixelSizeValueOfAttribute(context, android.R.attr.actionBarSize);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		return super.layoutDependsOn(parent, child, dependency) ||
				dependency instanceof Snackbar.SnackbarLayout ||
				dependency instanceof AppBarLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		if (dependency instanceof AppBarLayout) {
			return updateFab(child, dependency);
		} else if (dependency instanceof Snackbar.SnackbarLayout) {

			updateSnackbarTranslationField(dependency);
			return super.onDependentViewChanged(parent, child, dependency);
		} else {
			return super.onDependentViewChanged(parent, child, dependency);
		}
	}

	private boolean updateFab(FloatingActionButton child, View dependency) {
		float toolbarPosition = dependency.getY();
		float fabTranslation = -(toolbarPosition * 2) - mSnackbarTranslation;

		// snackbar is visible
		if (mSnackbarTranslation > 0) {
			// toolbar is gone
			if ((-dependency.getY()) > dependency.getHeight() / 8) {
				child.setVisibility(View.GONE);
			}

			// toolbar is visible
			else {
				child.setVisibility(View.VISIBLE);
			}
		}

		// snackbar is gone
		else {
			// toolbar is gone
			if (toolbarPosition + mActionBarSize == 0) {
				child.setVisibility(View.GONE);
			}

			// toolbar is visible
			else {
				child.setVisibility(View.VISIBLE);
			}
		}

		ViewCompat.setTranslationY(child, fabTranslation);
		return fabTranslation != 0;
	}

	private void updateSnackbarTranslationField(View dependency) {
		float snackbarTranslation = dependency.getHeight() - dependency.getTranslationY();
		boolean isDismissed = dependency.getX() > 0 && dependency.getX() > dependency.getWidth() / 2; // is dismissed by swipe

		// snackbar is gone
		if (snackbarTranslation < 1 || isDismissed || dependency.getVisibility() != View.VISIBLE) {
			mSnackbarTranslation = 0;
		}

		// snackbar is visible
		else {
			mSnackbarTranslation = snackbarTranslation;
		}
	}
}
