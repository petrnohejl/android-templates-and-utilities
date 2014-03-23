package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.entity.ProductEntity;

import java.util.List;


public class ProductReadAllQuery extends Query
{
	private int mLimit = -1;
	private int mOffset = -1;


	public ProductReadAllQuery()
	{
	}


	public ProductReadAllQuery(int limit, int offset)
	{
		mLimit = limit;
		mOffset = offset;
	}


	@Override
	public Data<List<ProductEntity>> processData()
	{
		ProductDAO dao = new ProductDAO();
		List<ProductEntity> list;

		if(mLimit==-1 && mOffset==-1)
		{
			list = dao.readAll();
		}
		else
		{
			list = dao.readAll(mLimit, mOffset);
		}

		Data<List<ProductEntity>> data = new Data<List<ProductEntity>>();
		data.setDataObject(list);

		return data;
	}
}
