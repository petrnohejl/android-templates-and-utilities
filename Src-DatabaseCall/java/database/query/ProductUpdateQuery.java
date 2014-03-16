package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.entity.ProductEntity;


public class ProductUpdateQuery extends Query
{
	private ProductEntity mProduct;


	public ProductUpdateQuery(ProductEntity product)
	{
		mProduct = product;
	}


	@Override
	public Data<Long> processData()
	{
		ProductDAO dao = new ProductDAO();
		long id = dao.update(mProduct);

		Data<Long> data = new Data<Long>();
		data.setDataObject(id);

		return data;
	}
}
