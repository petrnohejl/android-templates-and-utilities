package com.example.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


public final class HashUtility
{
	private HashUtility() {}


	public static String getMd5(byte[] data)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(data);
			StringBuffer stringBuffer = new StringBuffer();
			for(int i=0; i<bytes.length; ++i)
			{
				stringBuffer.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return stringBuffer.toString();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String getMd5(String data)
	{
		try
		{
			return getMd5(data.getBytes("UTF-8"));
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String getSha1(byte[] data)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(data);
			return byteToHex(md.digest());
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static String getSha1(String data)
	{
		try
		{
			return getSha1(data.getBytes("UTF-8"));
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	private static String byteToHex(final byte[] hash)
	{
		Formatter formatter = new Formatter();
		for(byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
