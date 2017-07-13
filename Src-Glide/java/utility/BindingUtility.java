package com.example.utility;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.R;


public final class BindingUtility
{
	private BindingUtility() {}


	@BindingAdapter({"imageUrl"})
	public static void setImageUrl(ImageView imageView, String url)
	{
		Glide
				.with(imageView.getContext())
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.RESULT)
				.crossFade()
				.error(R.drawable.placeholder)
				.into(imageView);
	}


	@BindingAdapter(value = {"imageUrl", "imageCircular", "imagePlaceholder", "imageError"}, requireAll = false)
	public static void setImageUrl(ImageView imageView, String url, boolean circular, Drawable placeholder, Drawable error)
	{
		RequestManager requestManager = Glide.with(imageView.getContext());
		if(!circular)
		{
			DrawableTypeRequest builder = requestManager.load(url);
			GlideUtility.setupRequestBuilder(builder, placeholder, error);
			builder.into(imageView);
		}
		else
		{
			BitmapTypeRequest builder = requestManager.load(url).asBitmap();
			GlideUtility.setupRequestBuilder(builder, placeholder, error);
			builder.into(GlideUtility.createCircularTarget(imageView));
		}
	}
}
