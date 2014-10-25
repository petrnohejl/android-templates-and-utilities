package com.example.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Environment;


// requires android.permission.READ_EXTERNAL_STORAGE or android.permission.WRITE_EXTERNAL_STORAGE
public class StorageUtility
{
	public static boolean isAvailable()
	{
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
			return true;
		}
		return false;
	}


	public static boolean isWritable()
	{
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		}
		return false;
	}
	
	
	public static File getStorageDirectory()
	{
		File file = Environment.getExternalStorageDirectory();
		return file;
	}
	
	
	// publicDirectory can be for example Environment.DIRECTORY_PICTURES
	public static File getStorageDirectory(String publicDirectory)
	{
		File file = Environment.getExternalStoragePublicDirectory(publicDirectory);
		return file;
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
		HashSet<String> hashSet = getExternalMounts();
		Iterator<String> iterator = hashSet.iterator();
		
		while(iterator.hasNext())
		{
			String s = (String) iterator.next();
			if(primary!=null && s!=null && !s.equals(primary.getAbsolutePath()))
			{
				File secondary = new File(s);
				if(secondary!=null && secondary.exists() && secondary.isDirectory())
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
					
					if(canonicalPrimary!=null && canonicalSecondary!=null && !canonicalPrimary.equals(canonicalSecondary))
					{
						if(publicDirectory==null) result = secondary;
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
		File file = context.getExternalCacheDir();
		return file;
	}
	
	
	// type can be for example Environment.DIRECTORY_PICTURES
	public static File getApplicationFilesDirectory(Context context, String type)
	{
		File file = context.getExternalFilesDir(type);
		return file;
	}
	
	
	public static ArrayList<File> getFiles(File directory, boolean recursive)
	{
		ArrayList<File> fileList = new ArrayList<File>();
		if(directory!=null && directory.exists() && directory.isDirectory())
		{
			walkFiles(directory, recursive, null, null, fileList);
		}
		return fileList;
	}
	
	
	// pattern can be for example "(.+(\\.(?i)(jpg|jpeg))$)"
	public static ArrayList<File> getFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter)
	{
		ArrayList<File> fileList = new ArrayList<File>();
		if(directory!=null && directory.exists() && directory.isDirectory())
		{
			walkFiles(directory, recursive, fileNameFilter, directoryNameFilter, fileList);
		}
		return fileList;
	}


	private static void walkFiles(File directory, boolean recursive, Pattern fileNameFilter, Pattern directoryNameFilter, ArrayList<File> fileList)
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
		if(pattern==null) return true;
		else
		{
			Matcher matcher = pattern.matcher(name);
			return matcher.matches();
		}
	}


	// code taken from: http://stackoverflow.com/questions/11281010/how-can-i-get-external-sd-card-path-for-android-4-0
	public static HashSet<String> getExternalMounts()
	{
		final HashSet<String> externalMounts = new HashSet<String>();
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
		for(String line:lines)
		{
			if(!line.toLowerCase(Locale.US).contains("asec")) // skip lines with "asec"
			{
				if(line.matches(regex))
				{
					String[] parts = line.split(" ");
					for(String part:parts)
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
}
