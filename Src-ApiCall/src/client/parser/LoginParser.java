package com.example.client.parser;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;

import com.example.client.response.LoginResponse;
import com.example.entity.Membership;


public class LoginParser
{
	public static LoginResponse parse(InputStream stream) throws IOException, JsonParseException
	{
		LoginResponse response = null;
		
		
		// init parser
		JsonFactory factory = new JsonFactory();
		org.codehaus.jackson.JsonParser parser = null;
		parser = factory.createJsonParser(stream);

		
		// parse JSON
		if(parser.nextToken() == JsonToken.START_OBJECT)
		while(parser.nextToken() != JsonToken.END_OBJECT)
		{
			// error
			if(parser.getCurrentName().equals("Error"))
			{
				String type = null;
				String message = null;
				
				if(parser.nextToken() == JsonToken.START_OBJECT)
				while(parser.nextToken() != JsonToken.END_OBJECT)
				{
					if(parser.getCurrentName().equals("Type"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_STRING) type=parser.getText();
					}
					else if(parser.getCurrentName().equals("Message"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_STRING) message=parser.getText();
					}
				}
				
				response = new LoginResponse();
				response.setError(true);
				response.setErrorType(type);
				response.setErrorMessage(message);
			}
			
			// response
			else if(parser.getCurrentName().equals("Membership"))
			{
				long userId = -1l;
				String accessToken = null;

				if(parser.nextToken() == JsonToken.START_OBJECT)
				while(parser.nextToken() != JsonToken.END_OBJECT)
				{
					if(parser.getCurrentName().equals("UserId"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) userId=parser.getLongValue();
					}
					else if(parser.getCurrentName().equals("AccessToken"))
					{
						if(parser.getCurrentToken() == JsonToken.VALUE_STRING) accessToken=parser.getText();
					}
				}

				Membership membership = new Membership();
				membership.setUserId(userId);
				membership.setAccessToken(accessToken);
				
				response = new LoginResponse();
				response.setMembership(membership);
			}
		}
		

		// close parser
		if(parser!=null) parser.close();
		return response;
	}
}
