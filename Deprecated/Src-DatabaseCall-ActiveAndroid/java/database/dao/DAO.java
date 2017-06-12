package com.example.database.dao;

import java.util.List;


public interface DAO<T>
{
	long create(T t);
	T read(long id);
	T readFirst();
	List<T> readAll();
	List<T> readAll(int limit, int offset);
	long update(T t);
	void delete(long id);
	void deleteAll();
}
