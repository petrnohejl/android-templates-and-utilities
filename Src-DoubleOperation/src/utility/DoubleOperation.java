package com.example.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class DoubleOperation
{
	public static final double EPSILON = 0.000001d;
	
	
	public static boolean equals(double val1, double val2)
	{
		return (Math.abs(val1 - val2) < EPSILON);
	}
	
	
	public static boolean divisible(double dividend, double divisor)
	{
		double divisionRemainder = dividend % divisor;
		return (equals(divisionRemainder, 0.0d) || equals(divisionRemainder, divisor));
	}
	
	
	public static double floorDivisor(double dividend, double divisor)
	{
		double divisionRemainder = dividend % divisor;
		return (dividend - divisionRemainder);
	}
	
	
	public static String toString(double value)
	{
		return new DecimalFormat("#.####", new DecimalFormatSymbols(Locale.getDefault())).format(value);
	}
}
