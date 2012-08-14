package com.example.client.entity;


public class Membership
{
	private long mUserId;
	private String mAccessToken;


	// empty constructor
	public Membership()
	{
	
	}
	
	
	// copy constructor
	public Membership(Membership copyModel)
	{
		mUserId = copyModel.mUserId;
		mAccessToken = new String(copyModel.mAccessToken);
	}
	
	
	public long getUserId()
	{
		return mUserId;
	}
	public void setUserId(long userId)
	{
		mUserId = userId;
	}
	public String getAccessToken()
	{
		return mAccessToken;
	}
	public void setAccessToken(String accessToken)
	{
		mAccessToken = accessToken;
	}
}
