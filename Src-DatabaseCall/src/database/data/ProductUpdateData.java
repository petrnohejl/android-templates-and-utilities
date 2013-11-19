package com.example.database.data;

import com.example.database.model.ProductModel;


public class ProductUpdateData extends Data
{
	private ProductModel mProductModel;
	
	
	public ProductUpdateData()
	{
	
	}


	public ProductModel getProductModel()
	{
		return mProductModel;
	}
	public void setProductModel(ProductModel productModel)
	{
		mProductModel = productModel;
	}
}
