package com.example.utility;


public class StringConvertor
{
	public static String capitalize(String str)
	{
		char[] chars = str.toLowerCase().toCharArray();
		boolean found = false;
		for(int i = 0; i < chars.length; i++)
		{
			if(!found && Character.isLetter(chars[i]))
			{
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			}
			else if(Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
			{
				found = false;
			}
		}
		return String.valueOf(chars);
	}
}
