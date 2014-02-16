package com.example.database;

import com.example.database.data.Data;


public interface DatabaseCallListener
{
	public void onDatabaseCallRespond(DatabaseCallTask task, Data data);
	public void onDatabaseCallFail(DatabaseCallTask task, Exception exception);
}
