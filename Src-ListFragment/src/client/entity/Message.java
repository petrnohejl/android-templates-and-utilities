package com.example.client.entity;


public class Message
{
	private long mId;
	private String mName;


	// empty constructor
	public Message()
	{
	
	}
	
	
	// copy constructor
	public Message(Message copyModel)
	{
		mId = copyModel.mId;
		if(copyModel.mName!=null) mName = new String(copyModel.mName);
	}
	
	
	public long getId()
	{
		return mId;
	}
	public void setId(long id)
	{
		mId = id;
	}
	public String getName()
	{
		return mName;
	}
	public void setName(String name)
	{
		mName = name;
	}
}
