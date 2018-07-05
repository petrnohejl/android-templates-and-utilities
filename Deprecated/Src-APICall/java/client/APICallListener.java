package com.example.client;

import com.example.client.response.Response;

public interface APICallListener {
	void onAPICallRespond(APICallTask task, ResponseStatus status, Response<?> response);
	void onAPICallFail(APICallTask task, ResponseStatus status, Exception exception);
}
