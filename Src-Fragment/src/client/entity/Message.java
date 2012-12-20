package com.example.client.entity;


public class Message
{
	private long id;
	private String name;


	// empty constructor
	public Message()
	{
	
	}
	
	
	// copy constructor
	public Message(Message copyModel)
	{
		id = copyModel.id;
		if(copyModel.name!=null) name = new String(copyModel.name);
	}
	
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
