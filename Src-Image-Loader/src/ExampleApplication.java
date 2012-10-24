package com.example;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class ExampleApplication extends Application
{
	private static ExampleApplication mInstance;


	public ExampleApplication()
	{
		mInstance = this;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		
		// init image caching
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		cacheDir.mkdirs(); // needs android.permission.WRITE_EXTERNAL_STORAGE
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
			.discCache(new TotalSizeLimitedDiscCache(cacheDir, 32 * 1024 * 1024))
			.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
			.build();
			
		ImageLoader.getInstance().init(config);
	}


	public static Context getContext()
	{
		return mInstance;
	}
}
