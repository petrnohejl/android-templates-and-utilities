package com.example.entity;


public enum StringEnum
{
	GET("GET"), POST("POST"), OPTIONS("OPTIONS"), HEAD("HEAD"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE");

	private final String mValue;


	private StringEnum(String value)
	{
		mValue = value;
	}


	@Override
	public String toString()
	{
		return mValue;
	}
}
