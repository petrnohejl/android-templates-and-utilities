package com.example.client.entity;


public class Membership
{
	private long userId;
	private String accessToken;


	// empty constructor
	public Membership()
	{
	
	}
	
	
	// copy constructor
	public Membership(Membership copyModel)
	{
		userId = copyModel.userId;
		if(copyModel.accessToken!=null) accessToken = new String(copyModel.accessToken);
	}
	
	
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public String getAccessToken()
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
}
