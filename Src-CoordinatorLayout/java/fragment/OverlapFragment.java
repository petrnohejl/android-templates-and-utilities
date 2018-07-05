package com.example.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;

public class OverlapFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {
	private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;

	private View mRootView;
	private View mFab;
	private int mMaxScrollSize;
	private boolean mIsImageHidden;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_overlap, container, false);
		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupAppBar();
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.getTotalScrollRange();
		int currentScrollPercentage = (Math.abs(i)) * 100 / mMaxScrollSize;

		if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
			if (!mIsImageHidden) {
				mIsImageHidden = true;
				ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
			}
		}

		if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
			if (mIsImageHidden) {
				mIsImageHidden = false;
				ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
			}
		}
	}

	private void setupAppBar() {
		// appbar
		AppBarLayout appBar = mRootView.findViewById(R.id.appbar);
		appBar.addOnOffsetChangedListener(this);

		// toolbar
		Toolbar toolbar = mRootView.findViewById(R.id.toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});

		// fab
		mFab = mRootView.findViewById(R.id.fab);
	}
}
