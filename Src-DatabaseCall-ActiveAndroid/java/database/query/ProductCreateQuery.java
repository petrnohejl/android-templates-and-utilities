package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.entity.ProductEntity;


public class ProductCreateQuery extends Query
{
	private ProductEntity mProduct;
	
	
	public ProductCreateQuery(ProductEntity product)
	{
		mProduct = product;
	}
	
	
	@Override
	public Data<Long> processData()
	{
		ProductDAO dao = new ProductDAO();
		long id = dao.create(mProduct);

		Data<Long> data = new Data<>();
		data.setDataObject(id);

		return data;
	}
}
