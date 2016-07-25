package com.example.utility;

import android.os.FileObserver;


public class ExampleFileObserver extends FileObserver
{
	private static final int MASK = (FileObserver.CREATE |
			FileObserver.MODIFY |
			FileObserver.CLOSE_WRITE |
			FileObserver.DELETE |
			FileObserver.DELETE_SELF |
			FileObserver.MOVED_FROM |
			FileObserver.MOVED_TO |
			FileObserver.MOVE_SELF);

	public String mAbsolutePath;


	public ExampleFileObserver(String absolutePath)
	{
		super(absolutePath, MASK); // or use FileObserver.ALL_EVENTS
		mAbsolutePath = absolutePath;

		Logcat.d(absolutePath);
	}


	@Override
	public void onEvent(int event, String path)
	{
		if(path == null) return;

		// a new file or subdirectory was created under the monitored directory
		if((FileObserver.CREATE & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is created");
		}

		// a file or directory was opened
		if((FileObserver.OPEN & event) != 0)
		{
			Logcat.d(path + " is opened");
		}

		// data was read from a file
		if((FileObserver.ACCESS & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is accessed/read");
		}

		// data was written to a file
		if((FileObserver.MODIFY & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is modified");
		}

		// someone has a file or directory open read-only, and closed it
		if((FileObserver.CLOSE_NOWRITE & event) != 0)
		{
			Logcat.d(path + " is closed");
		}

		// someone has a file or directory open for writing, and closed it
		if((FileObserver.CLOSE_WRITE & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is written and closed");
		}

		// a file was deleted from the monitored directory
		if((FileObserver.DELETE & event) != 0)
		{
			// for testing copy file
			// FileUtils.copyFile(mAbsolutePath + "/" + path);
			Logcat.d(mAbsolutePath + "/" + path + " is deleted");
		}

		// the monitored file or directory was deleted, monitoring effectively stops
		if((FileObserver.DELETE_SELF & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + " is deleted");
		}

		// a file or subdirectory was moved from the monitored directory
		if((FileObserver.MOVED_FROM & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is moved to somewhere " + "");
		}

		// a file or subdirectory was moved to the monitored directory
		if((FileObserver.MOVED_TO & event) != 0)
		{
			Logcat.d("file is moved to " + mAbsolutePath + "/" + path);
		}

		// the monitored file or directory was moved; monitoring continues
		if((FileObserver.MOVE_SELF & event) != 0)
		{
			Logcat.d(path + " is moved");
		}

		// metadata (permissions, owner, timestamp) was changed explicitly
		if((FileObserver.ATTRIB & event) != 0)
		{
			Logcat.d(mAbsolutePath + "/" + path + " is changed (permissions, owner, timestamp)");
		}
	}
}
