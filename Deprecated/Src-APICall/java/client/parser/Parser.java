package com.example.client.parser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public class Parser {
	protected static void handleUnknownParameter(JsonParser parser) throws IOException, JsonParseException {
		if (parser.getCurrentToken() == JsonToken.START_OBJECT)
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				handleUnknownParameter(parser);
			}

		if (parser.getCurrentToken() == JsonToken.START_ARRAY)
			while (parser.nextToken() != JsonToken.END_ARRAY) {
				handleUnknownParameter(parser);
			}
	}
}
