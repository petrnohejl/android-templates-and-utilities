package com.example.database.query;

import com.example.database.data.Data;
import com.example.database.data.ProductCreateData;
import com.example.database.model.ProductModel;


public class ProductCreateQuery extends Query
{
	private String mName;
	private int mQuantity;
	private long mTimestamp;
	private double mPrice;
	
	
	public ProductCreateQuery(String name, int quantity, long timestamp, double price)
	{
		mName = name;
		mQuantity = quantity;
		mTimestamp = timestamp;
		mPrice = price;
	}
	
	
	@Override
	public Data queryData()
	{
		ProductModel m = ProductModel.create(mName, mQuantity, mTimestamp, mPrice);
		
		ProductCreateData data = new ProductCreateData();
		data.setProductModel(m);
		
		return data;
	}
}
