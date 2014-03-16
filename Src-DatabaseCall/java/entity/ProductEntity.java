package com.example.entity;


public class ProductEntity
{
	private long id;
	private String name;
	public int quantity;
	public long timestamp;
	public double price;


	// empty constructor
	public ProductEntity()
	{
	
	}


	// copy constructor
	public ProductEntity(ProductEntity origin)
	{
		id = origin.id;
		if(origin.name!=null) name = new String(origin.name);
		quantity = origin.quantity;
		timestamp = origin.timestamp;
		price = origin.price;
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
