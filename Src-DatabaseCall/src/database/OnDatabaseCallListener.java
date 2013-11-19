package com.example.database;

import com.example.database.data.Data;


public interface OnDatabaseCallListener
{
	public void onDatabaseCallRespond(DatabaseCall call, Data data);
	public void onDatabaseCallFail(DatabaseCall call, Exception exception);
}
