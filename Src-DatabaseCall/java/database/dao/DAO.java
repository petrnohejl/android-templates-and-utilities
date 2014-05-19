package com.example.database.dao;

import com.example.database.DatabaseHelper;
import com.example.utility.Logcat;

import java.sql.SQLException;


public class DAO
{
	public static void printDatabaseInfo()
	{
		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		try
		{
			Logcat.d("DAO.printDatabaseInfo(): products " + databaseHelper.getProductDao().countOf());
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
