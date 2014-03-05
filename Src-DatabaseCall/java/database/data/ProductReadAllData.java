package com.example.database.data;

import java.util.List;

import com.example.database.model.ProductModel;


public class ProductReadAllData extends Data
{
	private List<ProductModel> mProductModelList;
	
	
	public ProductReadAllData()
	{
	
	}


	public List<ProductModel> getProductModelList()
	{
		return mProductModelList;
	}
	public void setProductModelList(List<ProductModel> productModelList)
	{
		mProductModelList = productModelList;
	}
}
