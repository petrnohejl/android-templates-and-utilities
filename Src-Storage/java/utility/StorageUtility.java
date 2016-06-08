package com.example.utility;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// requires android.permission.READ_EXTERNAL_STORAGE or android.permission.WRITE_EXTERNAL_STORAGE
public final class StorageUtility
{
	private StorageUtility() {}


	public static boolean isAvailable()
	{
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
	}


	public static boolean isWritable()
	{
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED);
	}


	public static File getStorageDirectory()
	{
		return Environment.getExternalStorageDirectory();
	}


	// publicDirectory can be for example Environment.DIRECTORY_PICTURES
	public static File getStorageDirectory(String publicDirectory)
	{
		return Environment.getExternalStoragePublicDirectory(publicDirectory);
	}


	public static File getSecondaryStorageDirectory()
	{
		return getSecondaryStorageDirectory(null);
	}


	// publicDirectory can be for example Environment.DIRECTORY_PICTURES
	public static File getSecondaryStorageDirectory(String publicDirectory)
	{
		File result = null;
		File primary = getStorageDirectory();
		Set<String> externalMounts = getExternalMounts();
		Iterator<String> iterator = externalMounts.iterator();

		while(iterator.hasNext())
		{
			String s = iterator.next();
			if(primary != null && s != null && !s.equals(primary.getAbsolutePath()))
			{
				File secondary = new File(s);
				if(secondary != null && secondary.exists() && secondary.isDirectory())
				{
					String canonicalPrimary = null;
					String canonicalSecondary = null;

					try
					{
						canonicalPrimary = primary.getCanonicalPath();
						canonicalSecondary = secondary.getCanonicalPath();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}

					if(canonicalPrimary != null && canonicalSecondary != null && !canonicalPrimary.equals(canonicalSecondary))
					{
						if(publicDirectory == null) result = secondary;
						else
						{
							String path = secondary.getAbsolutePath() + "/" + publicDirectory;
							result = new File(path);
						}
					}
				}
			}
		}

		return result;
	}


	public static File getApplicationCacheDirectory(Context context)
	{
		return context.getExternalCacheDir();
	}


	// type can be for example Environment.DIRECTORY_PICTURES
	public static File getApplicationFilesDirectory(Context context, String type)
	{
		return context.getExternalFilesDir(type);
	}


	public static List<File> getFiles(File directory, boolean recursive)
	{
		List<File> fileList = new ArrayList<>();
		if(directory != null && directory.exists() && directory.isDirectory())
		{
			walkFiles(directory, recursive, null, null, fileList);
		}
		return fileList;
	}


	// pattern can be for example "(.+(\\.(?i)(jpg|jpeg))$)"
	public static List<File> getFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter)
	{
		List<File> fileList = new ArrayList<>();
		if(directory != null && directory.exists() && directory.isDirectory())
		{
			walkFiles(directory, recursive, fileNameFilter, directoryNameFilter, fileList);
		}
		return fileList;
	}


	// source: http://stackoverflow.com/questions/11281010/how-can-i-get-external-sd-card-path-for-android-4-0
	public static Set<String> getExternalMounts()
	{
		final Set<String> externalMounts = new HashSet<>();
		String regex = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
		String mountOutput = "";

		// run mount process
		try
		{
			final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
			process.waitFor();
			final InputStream is = process.getInputStream();
			final byte[] buffer = new byte[1024];
			while(is.read(buffer) != -1)
			{
				mountOutput = mountOutput + new String(buffer);
			}
			is.close();
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}

		// parse mount output
		final String[] lines = mountOutput.split("\n");
		for(String line : lines)
		{
			if(!line.toLowerCase(Locale.US).contains("asec")) // skip lines with "asec"
			{
				if(line.matches(regex))
				{
					String[] parts = line.split(" ");
					for(String part : parts)
					{
						if(part.startsWith("/")) // starts with slash
							if(!part.toLowerCase(Locale.US).contains("vold")) // not contains "vold"
								externalMounts.add(part);
					}
				}
			}
		}
		return externalMounts;
	}


	private static void walkFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter, List<File> fileList)
	{
		File[] list = directory.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			File f = list[i];
			if(f.isDirectory())
			{
				if(recursive)
				{
					if(validateName(f.getName(), directoryNameFilter))
					{
						walkFiles(f, recursive, fileNameFilter, directoryNameFilter, fileList);
					}
				}
			}
			else if(validateName(f.getName(), fileNameFilter))
			{
				fileList.add(f);
			}
		}
	}


	private static boolean validateName(String name, Pattern pattern)
	{
		if(pattern == null) return true;
		else
		{
			Matcher matcher = pattern.matcher(name);
			return matcher.matches();
		}
	}
}
