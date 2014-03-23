package com.example.database.query;

import com.example.database.DatabaseHelper;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;


public class ProductReadQuery extends Query
{
	private long mId;


	public ProductReadQuery(long id)
	{
		mId = id;
	}


	@Override
	public Data<ProductModel> processData() throws SQLException
	{
		Data<ProductModel> data = null;

		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		Dao<ProductModel, Long> dao = databaseHelper.getProductDao();

		ProductModel m = dao.queryForId(mId);

		data = new Data<ProductModel>();
		data.setDataObject(m);
		return data;
	}
}
