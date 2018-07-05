package com.example;

public class ExampleConfig {
	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_ENVIRONMENT = BuildConfig.DEV_ENVIRONMENT;

	public static final String REST_BASE_URL_PROD = "http://example.com/api/";
	public static final String REST_BASE_URL_DEV = "http://dev.example.com/api/";
	public static final String REST_BASE_URL = ExampleConfig.DEV_ENVIRONMENT ? ExampleConfig.REST_BASE_URL_DEV : ExampleConfig.REST_BASE_URL_PROD;
}
