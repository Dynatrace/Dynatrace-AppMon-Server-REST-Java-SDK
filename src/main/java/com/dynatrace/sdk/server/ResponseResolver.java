package com.dynatrace.sdk.server;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.dynatrace.sdk.server.exceptions.ServerResponseException;

public interface ResponseResolver<T> {
	T resolve(CloseableHttpResponse response) throws ServerResponseException;
}