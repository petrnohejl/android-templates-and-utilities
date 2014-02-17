package com.example.entity;


public class ProductEntity
{
	private long id;
	private String name;


	// empty constructor
	public ProductEntity()
	{
	
	}
	
	
	// copy constructor
	public ProductEntity(ProductEntity copyModel)
	{
		id = copyModel.id;
		if(copyModel.name!=null) name = new String(copyModel.name);
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
}
