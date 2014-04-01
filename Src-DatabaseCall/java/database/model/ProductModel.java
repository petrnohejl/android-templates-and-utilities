package com.example.database.model;

import com.example.entity.ProductEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class ProductModel
{
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_PRICE = "price";

	@DatabaseField(columnName=COLUMN_ID, generatedId=true) private long id;
	@DatabaseField(columnName=COLUMN_NAME) private String name;
	@DatabaseField(columnName=COLUMN_QUANTITY) private int quantity;
	@DatabaseField(columnName=COLUMN_TIMESTAMP) private long timestamp;
	@DatabaseField(columnName=COLUMN_PRICE) private double price;


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
