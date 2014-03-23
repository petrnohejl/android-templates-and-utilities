package com.example.database.model;

import com.example.entity.ProductEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class ProductModel
{
	@DatabaseField(generatedId = true) private long id;
	@DatabaseField private String name;
	@DatabaseField private int quantity;
	@DatabaseField private long timestamp;
	@DatabaseField private double price;


	// empty constructor
	public ProductModel()
	{
	}


	public ProductEntity toEntity()
	{
		ProductEntity e = new ProductEntity();
		e.setId(id);
		e.setName(name);
		e.setQuantity(quantity);
		e.setTimestamp(timestamp);
		e.setPrice(price);
		return e;
	}


	public void set(ProductEntity e)
	{
		name = e.getName();
		quantity = e.getQuantity();
		timestamp = e.getTimestamp();
		price = e.getPrice();
	}


	public long getId()
	{
		return id;
	}


	public void setId(long id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public int getQuantity()
	{
		return quantity;
	}


	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}


	public long getTimestamp()
	{
		return timestamp;
	}


	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}


	public double getPrice()
	{
		return price;
	}


	public void setPrice(double price)
	{
		this.price = price;
	}
}
