package com.example.database.query;

import com.example.database.data.Data;
import com.example.database.data.ProductDeleteAllData;
import com.example.database.model.ProductModel;


public class ProductDeleteAllQuery extends Query
{
	public ProductDeleteAllQuery()
	{
	
	}
	
	
	@Override
	public Data queryData()
	{
		ProductModel.deleteAll();
		
		ProductDeleteAllData data = new  ProductDeleteAllData();
		
		return data;
	}
}
