package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;

import java.sql.SQLException;

public class ProductUpdateQuery extends Query {
	private ProductModel mProduct;

	public ProductUpdateQuery(ProductModel product) {
		mProduct = product;
	}

	@Override
	public Data<Integer> processData() throws SQLException {
		Data<Integer> data = new Data<>();
		data.setDataObject(ProductDAO.update(mProduct));
		return data;
	}
}
