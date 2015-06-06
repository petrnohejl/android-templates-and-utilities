package com.example.database.dao;

import com.example.database.DatabaseHelper;
import com.example.database.model.ProductModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;


public class ProductDAO extends DAO
{
	public static int create(ProductModel product) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		return dao.create(product);
	}
	
	
	public static ProductModel read(long id) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		return dao.queryForId(id);
	}
	
	
	public static List<ProductModel> readAll(long skip, long take) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		List<ProductModel> list;
		if(skip==-1l && take==-1l)
		{
			list = dao.queryForAll();
		}
		else
		{
			QueryBuilder<ProductModel, Long> queryBuilder = dao.queryBuilder();
			queryBuilder.offset(skip).limit(take);
			list = dao.query(queryBuilder.prepare());
		}
		return list;
	}
	
	
	public static int update(ProductModel product) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		return dao.update(product);
	}
	
	
	public static int delete(long id) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		return dao.deleteById(id);
	}
	
	
	public static int deleteAll() throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		DeleteBuilder<ProductModel, Long> deleteBuilder = dao.deleteBuilder();
		return dao.delete(deleteBuilder.prepare());
	}
	
	
	public static int refresh(ProductModel product) throws SQLException
	{
		Dao<ProductModel, Long> dao = getDao();
		return dao.refresh(product);
	}
	
	
	private static Dao<ProductModel, Long> getDao() throws SQLException
	{
		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		return databaseHelper.getProductDao();
	}
}
