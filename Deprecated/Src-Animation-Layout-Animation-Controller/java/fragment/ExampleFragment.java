package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.example.R;

public class ExampleFragment extends Fragment {
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}

	private void setupView() {
		// reference
		ListView listView = getListView();

		// animation
		Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
		animation.setDuration(100);
		LayoutAnimationController controller = new LayoutAnimationController(animation);
		listView.setLayoutAnimation(controller);
	}
}
