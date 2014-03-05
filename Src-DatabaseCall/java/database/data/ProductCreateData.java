package com.example.database.data;

import com.example.database.model.ProductModel;


public class ProductCreateData extends Data
{
	private ProductModel mProductModel;
	
	
	public ProductCreateData()
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
