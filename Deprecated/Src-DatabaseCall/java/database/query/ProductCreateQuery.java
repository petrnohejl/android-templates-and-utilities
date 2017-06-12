package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;

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
		Data<Integer> data = new Data<>();
		data.setDataObject(ProductDAO.create(mProduct));
		return data;
	}
}
