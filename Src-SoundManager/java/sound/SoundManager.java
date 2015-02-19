package com.example.sound;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.example.ExampleApplication;
import com.example.utility.Logcat;


public class SoundManager
{
	private Map<String, MediaPlayer> mMediaMap;
	private Mode mMode;
	
	
	public enum Mode
	{
		PLAY_SINGLE,			// play 1 sound at the moment, immediately stop all currently playing sounds
		PLAY_SINGLE_CONTINUE,	// play 1 sound at the moment, if the sound is same as currently playing sound, continue playing
		PLAY_MULTIPLE_CONTINUE	// play multiple sounds at the moment, if the sound is same as currently playing sound, continue playing
	}
	
	
	public SoundManager(Mode mode)
	{
		mMediaMap = new HashMap<>();
		mMode = mode;
	}
	
	
	public void play(String path)
	{
		playSound(path, null);
	}
	
	
	public void playAsset(String filename)
	{
		// context
		Context context = ExampleApplication.getContext();
		
		// get sound
		AssetFileDescriptor assetFileDescriptor;
		try
		{
			assetFileDescriptor = context.getAssets().openFd(filename);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		// play
		playSound(filename, assetFileDescriptor);
	}
	
	
	private void playSound(final String path, AssetFileDescriptor assetFileDescriptor)
	{
		// stop all currently playing sounds
		if(mMode.equals(Mode.PLAY_SINGLE))
		{
			stopAll();
		}
		
		// sound already playing
		if(mMediaMap.containsKey(path))
		{
			Logcat.d("SoundManager.play(): sound is already playing");
			printMediaList();
			return;
		}
		
		// stop all currently playing sounds
		if(mMode.equals(Mode.PLAY_SINGLE_CONTINUE))
		{
			stopAll();
		}
		
		// init media player
		MediaPlayer mediaPlayer;
		try
		{
			Logcat.d("SoundManager.prepareAsync(): " + mMediaMap.size());
			mediaPlayer = new MediaPlayer();
			mMediaMap.put(path, mediaPlayer);
			
			// data source
			if(assetFileDescriptor!=null)
			{
				mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
			}
			else
			{
				mediaPlayer.setDataSource(path);
			}
			
			mediaPlayer.prepareAsync();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			return;
		}
		catch(IllegalStateException e)
		{
			e.printStackTrace();
			return;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		// play sound
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mediaPlayer)
			{
				Logcat.d("SoundManager.onPrepared(): " + mMediaMap.size());
				mediaPlayer.start();
			}
		});
		
		// release media player
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				Logcat.d("SoundManager.onCompletion(): " + mMediaMap.size());
				mMediaMap.remove(path);
				if(mediaPlayer!=null)
				{
					mediaPlayer.release();
					mediaPlayer = null;
				}
				printMediaList();
			}
		});
	}
	
	
	// should be called in Activity.onStop()
	public void stopAll()
	{
		Logcat.d("SoundManager.stopAll(): " + mMediaMap.size());
		
		Collection<MediaPlayer> collection = mMediaMap.values();
		Iterator<MediaPlayer> iterator = collection.iterator();
		
		while(iterator.hasNext())
		{
			MediaPlayer mediaPlayer = iterator.next();
			if(mediaPlayer!=null)
			{
				Logcat.d("SoundManager.stopAll(): release");
				if(mediaPlayer.isPlaying()) mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
			}
		}
		
		mMediaMap.clear();
		printMediaList();
	}
	
	
	public void printMediaList()
	{
		Logcat.d("SoundManager.printMediaList(): " + mMediaMap.size());
	}
}
