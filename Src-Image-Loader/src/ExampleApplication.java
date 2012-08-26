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


public class ExampleApplication extends Application
{
	private static ExampleApplication instance;


	public ExampleApplication()
	{
		instance = this;
	}


	public static Context getContext()
	{
		return instance;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();
		
		File cacheDir;
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			cacheDir = new File(Environment.getExternalStorageDirectory(), "/Android/data/" + this.getPackageName() + "/cache/");
		}
		else
		{
			cacheDir = getApplicationContext().getCacheDir();
		}
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
}
