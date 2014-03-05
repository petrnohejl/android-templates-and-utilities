package com.example.entity;


public enum StringEnum
{
	GET("GET"), POST("POST"), OPTIONS("OPTIONS"), HEAD("HEAD"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE");


	private final String value;


	private StringEnum(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return value;
	}
}
