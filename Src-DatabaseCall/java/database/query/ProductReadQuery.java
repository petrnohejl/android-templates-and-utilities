package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.entity.ProductEntity;


public class ProductReadQuery extends Query
{
	private long mId;


	public ProductReadQuery(long id)
	{
		mId = id;
	}


	@Override
	public Data<ProductEntity> processData()
	{
		ProductDAO dao = new ProductDAO();
		ProductEntity e = dao.read(mId);

		Data<ProductEntity> data = new Data<ProductEntity>();
		data.setDataObject(e);

		return data;
	}
}
