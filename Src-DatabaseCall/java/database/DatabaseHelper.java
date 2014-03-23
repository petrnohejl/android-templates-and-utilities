package com.example.database;

import android.database.sqlite.SQLiteDatabase;

import com.example.ExampleApplication;
import com.example.database.model.ProductModel;
import com.example.utility.Logcat;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private static final String DATABASE_NAME = "example.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<ProductModel, Long> mProductDao = null;


	// singleton
	private static DatabaseHelper instance;
	public static synchronized DatabaseHelper getInstance()
	{
		if(instance==null) instance = new DatabaseHelper();
		return instance;
	}


	private DatabaseHelper()
	{
		super(ExampleApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{
		try
		{
			Logcat.d("DatabaseHelper.onCreate()");
			TableUtils.createTable(connectionSource, ProductModel.class);
		}
		catch(android.database.SQLException e)
		{
			Logcat.e("DatabaseHelper.onCreate(): can't create database", e);
			e.printStackTrace();
		}
		catch(java.sql.SQLException e)
		{
			Logcat.e("DatabaseHelper.onCreate(): can't create database", e);
			e.printStackTrace();
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		try
		{
			Logcat.d("DatabaseHelper.onUpgrade()");

			// TODO: database migration
		}
		catch(android.database.SQLException e)
		{
			Logcat.e("DatabaseHelper.onUpgrade(): can't upgrade database", e);
			e.printStackTrace();
		}
	}


	@Override
	public void close()
	{
		super.close();
		mProductDao = null;
	}


	public synchronized void clearDatabase()
	{
		try
		{
			Logcat.d("DatabaseHelper.clearDatabase()");

			TableUtils.dropTable(getConnectionSource(), ProductModel.class, true);

			TableUtils.createTable(getConnectionSource(), ProductModel.class);
		}
		catch(android.database.SQLException e)
		{
			Logcat.e("DatabaseHelper.clearDatabase(): can't clear database", e);
			e.printStackTrace();
		}
		catch(java.sql.SQLException e)
		{
			Logcat.e("DatabaseHelper.clearDatabase(): can't clear database", e);
			e.printStackTrace();
		}
	}


	public synchronized Dao<ProductModel, Long> getProductDao() throws java.sql.SQLException
	{
		if(mProductDao==null)
		{
			mProductDao = getDao(ProductModel.class);
		}
		return mProductDao;
	}
}
