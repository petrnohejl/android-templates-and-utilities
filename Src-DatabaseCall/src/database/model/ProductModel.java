package com.example.database.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;


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


	// copy constructor
	public ProductModel(ProductModel origin)
	{
		super();
		if(origin.name != null) name = new String(origin.name);
		quantity = origin.quantity;
		timestamp = origin.timestamp;
		price = origin.price;
	}
	
	
	public static ProductModel create(String name, int quantity, long timestamp, double price)
	{
		ProductModel m = new ProductModel();
		m.name = name;
		m.quantity = quantity;
		m.timestamp = timestamp;
		m.price = price;
		m.save();
		return m;
	}
	
	
	public static ProductModel read(long id)
	{
		return new Select().from(ProductModel.class).where("Id=?", id).executeSingle();
	}
	
	
	public static ProductModel readFirst()
	{
		return new Select().from(ProductModel.class).limit(1).executeSingle();
	}
	
	
	public static List<ProductModel> readAll()
	{
		return new Select().from(ProductModel.class).orderBy("Timestamp ASC").execute();
	}
	
	
	public static List<ProductModel> readAll(int limit, int offset)
	{
		return new Select().from(ProductModel.class).limit(limit).offset(offset).orderBy("Timestamp ASC").execute();
	}
	
	
	public static ProductModel update(long id, String name, int quantity, long timestamp, double price)
	{
		ProductModel m = read(id);
		m.name = name;
		m.quantity = quantity;
		m.timestamp = timestamp;
		m.price = price;
		m.save();
		return m;
	}
	
	
	public static void delete(long id)
	{
		new Delete().from(ProductModel.class).where("Id=?", id).executeSingle();
	}
	
	
	public static void deleteAll()
	{
		new Delete().from(ProductModel.class).execute();
	}
}
