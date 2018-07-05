package com.example.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import org.alfonz.utility.ResourcesUtility;

public class ScalingFloatingActionButtonBehavior extends FloatingActionButton.Behavior {
	private int mActionBarSize;

	public ScalingFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActionBarSize = ResourcesUtility.getDimensionPixelSizeValueOfAttribute(context, android.R.attr.actionBarSize);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		return super.layoutDependsOn(parent, child, dependency) || dependency instanceof AppBarLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
		if (dependency instanceof AppBarLayout) {
			return updateFab(child, dependency);
		} else {
			return super.onDependentViewChanged(parent, child, dependency);
		}
	}

	private boolean updateFab(FloatingActionButton child, View dependency) {
		float toolbarPosition = dependency.getY();

		// toolbar is gone
		if (toolbarPosition + mActionBarSize == 0) {
			child.hide();
		}

		// toolbar is visible
		else {
			child.show();
		}

		return toolbarPosition != 0;
	}
}
