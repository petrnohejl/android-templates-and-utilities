package com.example.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.R;

import org.alfonz.utility.Logcat;

public class ExampleFragment extends Fragment {
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}

	private void animateView1(View view) {
		PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0F, 400F);
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0F, 200F);
		PropertyValuesHolder holderSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 2F);
		PropertyValuesHolder holderSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 2F);
		PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F);
		PropertyValuesHolder holderA = PropertyValuesHolder.ofFloat(View.ALPHA, 0.5F, 1F);
		ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY, holderSX, holderSY, holderR, holderA);
		animator1.setDuration(2000);
		animator1.setRepeatCount(ObjectAnimator.INFINITE);
		animator1.setRepeatMode(ObjectAnimator.REVERSE);
		animator1.setInterpolator(new AccelerateDecelerateInterpolator());
		animator1.setEvaluator(new FloatEvaluator());
		animator1.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animator) {
				Logcat.d("");
			}

			@Override
			public void onAnimationRepeat(Animator animator) {
				Logcat.d("");
			}

			@Override
			public void onAnimationCancel(Animator animator) {
				Logcat.d("");
			}

			@Override
			public void onAnimationEnd(Animator animator) {
				Logcat.d("");
			}
		});

		ObjectAnimator animator2 = ObjectAnimator.ofInt(view, "backgroundColor", 0xffff0000, 0xff0000ff);
		animator2.setDuration(2000);
		animator2.setRepeatCount(ObjectAnimator.INFINITE);
		animator2.setRepeatMode(ObjectAnimator.REVERSE);
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());
		animator2.setEvaluator(new ArgbEvaluator());

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(animator1, animator2);
		animatorSet.start();
	}

	private void animateView2(final View view) {
		Integer colorFrom = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
		Integer colorTo = ContextCompat.getColor(getActivity(), android.R.color.holo_green_light);
		ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo, colorFrom);
		animator.setDuration(2000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				view.setBackgroundColor((Integer) animator.getAnimatedValue());
			}
		});
		animator.start();
	}
}
