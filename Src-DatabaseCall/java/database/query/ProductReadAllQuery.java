package com.example.database.query;

import com.example.database.DatabaseHelper;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;


public class ProductReadAllQuery extends Query
{
	private long mSkip = -1l;
	private long mTake = -1l;


	public ProductReadAllQuery()
	{
	}


	public ProductReadAllQuery(long skip, long take)
	{
		mSkip = skip;
		mTake = take;
	}


	@Override
	public Data<List<ProductModel>> processData() throws SQLException
	{
		Data<List<ProductModel>> data = null;

		DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
		Dao<ProductModel, Long> dao = databaseHelper.getProductDao();

		List<ProductModel> list;
		if(mSkip==-1l && mTake==-1l)
		{
			list = dao.queryForAll();
		}
		else
		{
			QueryBuilder<ProductModel, Long> queryBuilder = dao.queryBuilder();
			queryBuilder.offset(mSkip).limit(mTake);
			list = dao.query(queryBuilder.prepare());
		}

		data = new Data<List<ProductModel>>();
		data.setDataObject(list);
		return data;
	}
}
