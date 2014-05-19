package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;

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
		Data<ProductModel> data = new Data<ProductModel>();
		data.setDataObject(ProductDAO.read(mId));
		return data;
	}
}
