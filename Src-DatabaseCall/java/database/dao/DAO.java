package com.example.database.dao;

import java.util.List;


public interface DAO<T>
{
	public long create(T t);
	public T read(long id);
	public T readFirst();
	public List<T> readAll();
	public List<T> readAll(int limit, int offset);
	public long update(T t);
	public void delete(long id);
	public void deleteAll();
}
