package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;

import java.sql.SQLException;


public class ProductDeleteQuery extends Query
{
	private long mId;


	public ProductDeleteQuery(long id)
	{
		mId = id;
	}


	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = new Data<>();
		data.setDataObject(ProductDAO.delete(mId));
		return data;
	}
}
