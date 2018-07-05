package com.example.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.entity.ProductEntity;

@Table(name = "Products")
public class ProductModel extends Model {
	@Column(name = "Name") public String name;
	@Column(name = "Quantity") public int quantity;
	@Column(name = "Timestamp") public long timestamp;
	@Column(name = "Price") public double price;

	// empty constructor
	public ProductModel() {
		super();
	}

	public ProductEntity toEntity() {
		ProductEntity e = new ProductEntity();
		e.setId(getId());
		e.setName(name);
		e.setQuantity(quantity);
		e.setTimestamp(timestamp);
		e.setPrice(price);
		return e;
	}

	public void set(ProductEntity e) {
		name = e.getName();
		quantity = e.getQuantity();
		timestamp = e.getTimestamp();
		price = e.getPrice();
	}
}
