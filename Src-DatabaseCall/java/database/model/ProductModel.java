package com.example.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.entity.ProductEntity;


@Table(name = "Products")
public class ProductModel extends Model
{
	@Column(name = "Name")
	public String name;
	
	@Column(name = "Quantity")
	public int quantity;
	
	@Column(name = "Timestamp")
	public long timestamp;
	
	@Column(name = "Price")
	public double price;


	// empty constructor
	public ProductModel()
	{
		super();
	}


	public ProductEntity toEntity()
	{
		ProductEntity e = new ProductEntity();
		e.setId(this.getId());
		e.setName(this.name);
		e.setQuantity(this.quantity);
		e.setTimestamp(this.timestamp);
		e.setPrice(this.price);
		return e;
	}
}
