package com.example.database.query;

import java.util.List;

import com.example.database.data.Data;
import com.example.database.data.ProductReadAllData;
import com.example.database.model.ProductModel;


public class ProductReadAllQuery extends Query
{
	public ProductReadAllQuery()
	{
	
	}
	
	
	@Override
	public Data queryData()
	{
		List<ProductModel> list = ProductModel.readAll();
		
		ProductReadAllData data = new  ProductReadAllData();
		data.setProductModelList(list);
		
		return data;
	}
}
