package com.example.database.dao;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.database.model.ProductModel;
import com.example.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;


public class ProductDAO implements DAO<ProductEntity>
{
	@Override
	public long create(ProductEntity product)
	{
		ProductModel m = new ProductModel();
		m.set(product);
		return m.save();
	}


	@Override
	public ProductEntity read(long id)
	{
		ProductModel m = new Select().from(ProductModel.class).where("Id=?", id).executeSingle();
		return m.toEntity();
	}


	@Override
	public ProductEntity readFirst()
	{
		ProductModel m = new Select().from(ProductModel.class).limit(1).executeSingle();
		return m.toEntity();
	}


	@Override
	public List<ProductEntity> readAll()
	{
		List<ProductModel> modelList = new Select().from(ProductModel.class).orderBy("Timestamp ASC").execute();
		List<ProductEntity> entityList = new ArrayList<>();

		for(ProductModel m : modelList)
		{
			ProductEntity e = m.toEntity();
			entityList.add(e);
		}

		return entityList;
	}


	@Override
	public List<ProductEntity> readAll(int limit, int offset)
	{
		List<ProductModel> modelList = new Select().from(ProductModel.class).limit(limit).offset(offset).orderBy("Timestamp ASC").execute();
		List<ProductEntity> entityList = new ArrayList<>();

		for(ProductModel m : modelList)
		{
			ProductEntity e = m.toEntity();
			entityList.add(e);
		}

		return entityList;
	}


	@Override
	public long update(ProductEntity product)
	{
		ProductModel m = new Select().from(ProductModel.class).where("Id=?", product.getId()).executeSingle();
		m.set(product);
		return m.save();
	}


	@Override
	public void delete(long id)
	{
		new Delete().from(ProductModel.class).where("Id=?", id).executeSingle();
	}


	@Override
	public void deleteAll()
	{
		new Delete().from(ProductModel.class).execute();
	}
}
