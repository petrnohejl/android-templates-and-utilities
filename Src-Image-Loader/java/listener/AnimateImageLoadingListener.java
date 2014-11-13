package com.example.listener;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class AnimateImageLoadingListener extends SimpleImageLoadingListener
{
	private final List<String> mDisplayedImageList = Collections.synchronizedList(new LinkedList<String>());


	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
	{
		if(loadedImage!=null)
		{
			ImageView imageView = (ImageView) view;
			boolean firstDisplay = !mDisplayedImageList.contains(imageUri);
			if(firstDisplay)
			{
				FadeInBitmapDisplayer.animate(imageView, 500);
				mDisplayedImageList.add(imageUri);
			}
		}
	}
}
