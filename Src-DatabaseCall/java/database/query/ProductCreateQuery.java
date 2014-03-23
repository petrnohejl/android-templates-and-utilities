package com.example.database.query;

import com.example.database.DatabaseHelper;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;


public class ProductCreateQuery extends Query
{
	private ProductModel mProduct;
	
	
	public ProductCreateQuery(ProductModel product)
	{
		mProduct = product;
	}
	
	
	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = null;

		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		Dao<ProductModel, Long> dao = databaseHelper.getProductDao();

		int rows = dao.create(mProduct);

		data = new Data<Integer>();
		data.setDataObject(rows);
		return data;
	}
}
