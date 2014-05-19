package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;

import java.sql.SQLException;


public class ProductDeleteAllQuery extends Query
{
	public ProductDeleteAllQuery()
	{
	}


	@Override
	public Data<Integer> processData() throws SQLException
	{
		Data<Integer> data = new Data<Integer>();
		data.setDataObject(ProductDAO.deleteAll());
		return data;
	}
}
