/***************************************************
 *
 * dynaTrace Diagnostics (c) dynaTrace software GmbH
 * @file: ResultResponse.java
 * @date: 11.01.2017
 * @author: tomasz.chojnacki
 */
package com.dynatrace.sdk.server;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.dynatrace.sdk.server.exceptions.ServerResponseException;

/**
 *
 * @param <T> object type to be built
 * @author tomasz.chojnacki
 */
public interface ResponseResolver<T> {
	T resolve(CloseableHttpResponse response) throws ServerResponseException;
}