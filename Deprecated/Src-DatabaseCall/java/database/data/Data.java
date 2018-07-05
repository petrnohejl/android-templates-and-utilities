package com.example.database.data;

public class Data<T> {
	private T mDataObject;

	public Data() {
	}

	public T getDataObject() {
		return mDataObject;
	}

	public void setDataObject(T dataObject) {
		mDataObject = dataObject;
	}
}
