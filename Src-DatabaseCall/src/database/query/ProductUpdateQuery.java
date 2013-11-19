package com.example.database.query;

import com.example.database.data.Data;
import com.example.database.data.ProductUpdateData;
import com.example.database.model.ProductModel;


public class ProductUpdateQuery extends Query
{
	private long mId;
	private String mName;
	private int mQuantity;
	private long mTimestamp;
	private double mPrice;
	
	
	public ProductUpdateQuery(long id, String name, int quantity, long timestamp, double price)
	{
		mId = id;
		mName = name;
		mQuantity = quantity;
		mTimestamp = timestamp;
		mPrice = price;
	}
	
	
	@Override
	public Data queryData()
	{
		ProductModel m = ProductModel.update(mId, mName, mQuantity, mTimestamp, mPrice);
		
		ProductUpdateData data = new ProductUpdateData();
		data.setProductModel(m);
		
		return data;
	}
}
