package com.example.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;

public class BottomSheetFragment extends Fragment {
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupBottomSheet();
	}

	private void setupBottomSheet() {
		// reference
		final FloatingActionButton fab = mRootView.findViewById(R.id.fab);
		final View bottomSheet = mRootView.findViewById(R.id.bottom_sheet);

		// fab
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
				bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
			}
		});
	}
}
