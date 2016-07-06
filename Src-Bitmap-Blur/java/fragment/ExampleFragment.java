package com.example.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.R;
import com.example.graphics.BitmapBlur;


public class ExampleFragment extends Fragment
{
	private View mRootView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}


	private void bindData()
	{
		// reference
		ImageView photoImageView = (ImageView) mRootView.findViewById(R.id.fragment_example_photo);

		// blur
		Bitmap originalBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.photo);
		Bitmap blurredBitmap = BitmapBlur.getBlurredBitmap(getActivity(), originalBitmap);
		originalBitmap.recycle();
		photoImageView.setImageBitmap(blurredBitmap);
	}
}
