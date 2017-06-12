package com.example.database;

import com.example.database.data.Data;


public interface DatabaseCallListener
{
	void onDatabaseCallRespond(DatabaseCallTask task, Data<?> data);
	void onDatabaseCallFail(DatabaseCallTask task, Exception exception);
}
