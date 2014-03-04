package com.example.entity;


public enum IntegerEnum
{
	UNDEFINED(0), GRAY_SCALE(1), RGB(2), CMYK(3);


	private final int value;


	private IntegerEnum(int value)
	{
		this.value = value;
	}


	public int getValue()
	{
		return value;
	}


	public static IntegerEnum valueToIntegerEnum(int value)
	{
		IntegerEnum[] values = IntegerEnum.values();
		return values[value];
	}
}
