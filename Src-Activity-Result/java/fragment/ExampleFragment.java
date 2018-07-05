package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.activity.ExampleActivity;
import com.example.activity.PickerActivity;

import org.alfonz.utility.Logcat;

public class ExampleFragment extends Fragment {
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_simple, container, false);
		return mRootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ExampleActivity.REQUEST_PICK_ITEM) {
			if (resultCode == Activity.RESULT_OK) {
				Logcat.d("ok");
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Logcat.d("canceled");
			}
		}
	}

	private void startPickerActivity() {
		Intent intent = PickerActivity.newIntent(getActivity());
		startActivityForResult(intent, ExampleActivity.REQUEST_PICK_ITEM);
	}
}
