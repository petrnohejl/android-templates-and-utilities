package com.example.utility;

import android.util.Patterns;


public class Validator
{
	public static boolean checkEmail(CharSequence email)
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
}
