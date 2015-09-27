package com.example.utility;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public final class Validator
{
	private Validator() {}


	public static boolean isEmailValid(CharSequence email)
	{
		if(email==null)
		{
			return false;
		}
		else
		{
			return Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}
	}


	public static boolean isDateValid(String date, String format)
	{
		if(date==null)
		{
			return false;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.setLenient(false);

		try
		{
			simpleDateFormat.parse(date);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
