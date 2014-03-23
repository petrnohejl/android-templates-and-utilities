package com.example.database.query;

import com.example.database.DatabaseHelper;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class ProductDeleteAllQuery extends Query
{
	public ProductDeleteAllQuery()
	{
	}


	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = null;

		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		Dao<ProductModel, Long> dao = databaseHelper.getProductDao();

		List<ProductModel> list = dao.queryForAll();
		int rows = dao.delete(list);

		data = new Data<Integer>();
		data.setDataObject(rows);
		return data;
	}
}
