package com.example.client.response;

import com.example.client.entity.Membership;


public class LoginResponse extends Response
{
	private Membership mMembership;
	
	
	public LoginResponse()
	{

	}


	public Membership getMembership()
	{
		return mMembership;
	}
	public void setMembership(Membership membership)
	{
		mMembership = membership;
	}
}
