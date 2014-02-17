package com.example.client.response;

import com.example.entity.MembershipEntity;


public class LoginResponse extends Response
{
	private MembershipEntity mMembership;
	
	
	public LoginResponse()
	{

	}


	public MembershipEntity getMembership()
	{
		return mMembership;
	}
	public void setMembership(MembershipEntity membership)
	{
		mMembership = membership;
	}
}
