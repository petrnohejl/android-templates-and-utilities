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
	public MembershipEntity(MembershipEntity copyModel)
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
