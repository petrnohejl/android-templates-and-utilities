package com.example.entity;


public class MembershipEntity
{
	private long userId;
	private String accessToken;


	// empty constructor
	public MembershipEntity()
	{
	
	}
	
	
	// copy constructor
	public MembershipEntity(MembershipEntity origin)
	{
		userId = origin.userId;
		if(origin.accessToken!=null) accessToken = new String(origin.accessToken);
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
