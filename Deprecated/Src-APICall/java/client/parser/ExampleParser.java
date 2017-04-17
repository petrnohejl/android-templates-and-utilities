package com.example.client.parser;

import com.example.client.response.Response;
import com.example.entity.ProductEntity;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ExampleParser extends Parser
{
	public static Response<List<ProductEntity>> parse(InputStream stream) throws IOException, JsonParseException
	{
		Response<List<ProductEntity>> response = null;

		// init parser
		JsonFactory factory = new JsonFactory();
		JsonParser parser = null;
		parser = factory.createJsonParser(stream);

		// parse JSON
		if(parser.nextToken() == JsonToken.START_OBJECT)
			while(parser.nextToken() != JsonToken.END_OBJECT)
			{
				// error
				if(parser.getCurrentName().equals("error"))
				{
					String type = null;
					String message = null;

					if(parser.nextToken() == JsonToken.START_OBJECT)
						while(parser.nextToken() != JsonToken.END_OBJECT)
						{
							if(parser.getCurrentName().equals("type"))
							{
								if(parser.getCurrentToken() == JsonToken.VALUE_STRING) type = parser.getText();
							}
							else if(parser.getCurrentName().equals("message"))
							{
								if(parser.getCurrentToken() == JsonToken.VALUE_STRING) message = parser.getText();
							}
						}

					response = new Response<>();
					response.setError(true);
					response.setErrorType(type);
					response.setErrorMessage(message);
				}

				// response
				else if(parser.getCurrentName().equals("product"))
				{
					long id = -1L;
					String name = null;

					if(parser.nextToken() == JsonToken.START_OBJECT)
						while(parser.nextToken() != JsonToken.END_OBJECT)
						{
							if(parser.getCurrentName().equals("id"))
							{
								if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) id = parser.getLongValue();
							}
							else if(parser.getCurrentName().equals("name"))
							{
								if(parser.getCurrentToken() == JsonToken.VALUE_STRING) name = parser.getText();
							}
							else
							{
								// unknown parameter
								handleUnknownParameter(parser);
							}
						}

					ProductEntity product = new ProductEntity();
					product.setId(id);
					product.setName(name);

					List<ProductEntity> productList = new ArrayList<>();
					productList.add(product);

					response = new Response<>();
					response.setResponseObject(productList);
				}
			}

		// close parser
		if(parser != null) parser.close();
		return response;
	}
}
