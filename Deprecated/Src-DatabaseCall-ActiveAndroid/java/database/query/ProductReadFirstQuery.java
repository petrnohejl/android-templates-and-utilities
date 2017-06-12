package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.entity.ProductEntity;


public class ProductReadFirstQuery extends Query
{
	public ProductReadFirstQuery()
	{
	}


	@Override
	public Data<ProductEntity> processData()
	{
		ProductDAO dao = new ProductDAO();
		ProductEntity e = dao.readFirst();

		Data<ProductEntity> data = new Data<>();
		data.setDataObject(e);

		return data;
	}
}
