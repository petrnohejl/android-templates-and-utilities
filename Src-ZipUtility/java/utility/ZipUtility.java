package com.example.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


// requires android.permission.WRITE_EXTERNAL_STORAGE
public class ZipUtility
{
	public static boolean unpackZip(String path, String zipname)
	{
		InputStream inputStream;
		ZipInputStream zipInputStream;
		
		try
		{
			int count;
			String filename;
			ZipEntry zipEntry;
			byte[] buffer = new byte[1024];
			
			inputStream = new FileInputStream(path + zipname);
			zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
			
			while((zipEntry = zipInputStream.getNextEntry()) != null)
			{
				filename = zipEntry.getName();
				
				if(zipEntry.isDirectory())
				{
					File f = new File(path + filename);
					f.mkdirs();
					continue;
				}
				
				FileOutputStream fileOutputStream = new FileOutputStream(path + filename);
				
				while((count = zipInputStream.read(buffer)) != -1)
				{
					fileOutputStream.write(buffer, 0, count);
				}
				
				fileOutputStream.close();
				zipInputStream.closeEntry();
			}
			
			zipInputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
