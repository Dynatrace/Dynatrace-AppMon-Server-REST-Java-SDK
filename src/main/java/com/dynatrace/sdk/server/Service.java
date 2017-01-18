/*
 * Dynatrace Server SDK
 * Copyright (c) 2008-2016, DYNATRACE LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  Neither the name of the dynaTrace software nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.dynatrace.sdk.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.response.models.ErrorResponse;

public abstract class Service {

	static {
		/** Set jaxb factory to use eclipse implementation. */
		System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
	}

	/** Default API version URI prefix, may be overwritten in constructor. */
	public final static String API_VER_URI_PREFIX = "/api/v2";

	private final String apiVerURIPrefix;
	private final DynatraceClient client;

	protected Service(DynatraceClient client) {
		this(client, API_VER_URI_PREFIX);
	}

	protected Service(DynatraceClient client, String apiVerURIPrefix) {
		this.apiVerURIPrefix = apiVerURIPrefix;
		this.client = client;
	}


	private static <T> T jsonInputStreamToObject(InputStream json, Class<T> clazz) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		try {
			return unmarshaller.unmarshal(new StreamSource(json), clazz).getValue();
		} finally {
			json.close();
		}
	}


	protected static StringEntity jsonObjectToEntity(Object object) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			marshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
			marshaller.marshal(object, writer);
			String string = writer.getBuffer().toString();
			StringEntity entity = new StringEntity(string);
			entity.setContentType("application/json");

			return entity;
		} catch (JAXBException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(String.format("Provided request couldn't be serialized: %s", e.getMessage()), e);
		}
	}

	private URI buildURI(String path, NameValuePair... params) throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme(this.client.getConfiguration().isSSL() ? "https" : "http");
		uriBuilder.setHost(this.client.getConfiguration().getHost());
		uriBuilder.setPort(this.client.getConfiguration().getPort());
		uriBuilder.setPath(apiVerURIPrefix + path);
		uriBuilder.setParameters(params);
		return uriBuilder.build();
	}


	protected CloseableHttpResponse doRequest(HttpRequestBase request) throws ServerConnectionException, ServerResponseException {
		request.setHeader("Accept", "*/json");

		request.setHeader("Authorization", "Basic " + Base64.encodeBase64String((this.client.getConfiguration().getName() + ":" + this.client.getConfiguration().getPassword()).getBytes()));
		try {
			CloseableHttpResponse response = this.client.getClient().execute(request);
			if (response.getStatusLine().getStatusCode() >= 300 || response.getStatusLine().getStatusCode() < 200) {
				String error = null;
				// dynatrace often returns an error message along with a status code
				// we try to parse it, if that doesn't work we use a code bound message
				// as a reason in exception
				try (InputStream is = response.getEntity().getContent()) {
					error = jsonInputStreamToObject(is, ErrorResponse.class).getMessage();
				} catch (JAXBException e) {
					// error message might not exist
				}

				if (error == null || error.isEmpty()) {
					error = response.getStatusLine().getReasonPhrase();
				}
				throw new ServerResponseException(response.getStatusLine().getStatusCode(), error);
			}
			return response;
		} catch (IOException e) {
			throw new ServerConnectionException(String.format("Could not connect to Dynatrace Server: %s", e.getMessage()), e);
		}
	}

	private static <T> T parseResponse(CloseableHttpResponse response, Class<T> responseClass) throws ServerResponseException {
		try {
			return jsonInputStreamToObject(response.getEntity().getContent(), responseClass);
		} catch (IOException | JAXBException e) {
			throw new ServerResponseException(response.getStatusLine().getStatusCode(), String.format("Could not unmarshall response into given object: %s", e.getMessage()), e);
		}
	}

	private CloseableHttpResponse doPutRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
		HttpPut put = new HttpPut(uri);
		put.setEntity(entity);
		return this.doRequest(put);
	}


	public <T> T doPutRequest(String uriString, Object entityObject, ResponseResolver<T> resolver) throws ServerConnectionException, ServerResponseException {

		HttpEntity entity = entityObject == null ? null : Service.jsonObjectToEntity(entityObject);

		try (CloseableHttpResponse response = this.doPutRequest(buildURI(uriString), entity)) {

			return resolver.resolve(response);

		} catch (IOException e) {
			throw new RuntimeException("Could not close http response:" + e.getMessage(), e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid uri: " + uriString + ". " + e.getMessage(), e);
		}
	}

	public <T> T doGetRequest(String uriString, ResponseResolver<T> resolver, NameValuePair ... params) throws ServerConnectionException, ServerResponseException {

		try (CloseableHttpResponse response = this.doRequest(new HttpGet(buildURI(uriString, params)))) {

			return resolver.resolve(response);

		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid uri: " + uriString + ", params: " + params + " . " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Could not close http response: " + e.getMessage(), e);
		}

	}

	private CloseableHttpResponse doPostRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
		HttpPost post = new HttpPost(uri);
		post.setEntity(entity);
		return this.doRequest(post);
	}

	private <T> T doPostRequest(String uriString, ResponseResolver<T> resolver, HttpEntity entity) throws ServerConnectionException, ServerResponseException {

		try (CloseableHttpResponse response = this.doPostRequest(this.buildURI(uriString), entity)) {
			return resolver.resolve(response);
		} catch (IOException e) {
			throw new RuntimeException("Could not close http response:" + e.getMessage(), e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid uri: " + e.getMessage(), e);
		}
	}

	public <T> T doPostRequest(String uriString, ResponseResolver<T> resolver, Object entityObject) throws ServerConnectionException, ServerResponseException {

		HttpEntity entity = entityObject == null ? null : Service.jsonObjectToEntity(entityObject);

		return this.doPostRequest(uriString, resolver, entity);
	}

	/**
	 *
	 * @param clazz of response
	 * @return object of given class returned in response body
	 */
	protected static <T> ResponseResolver<T> getBodyResponseResolver(final Class<T> clazz) {
		return new ResponseResolver<T>() {
			public T resolve(CloseableHttpResponse response) throws ServerResponseException {
				return Service.parseResponse(response, clazz);
			}
		};
	}

	/**
	 *
	 * @return location param of response header.
	 */
	protected static ResponseResolver<String> getHeaderLocationResolver() {
		return headerLocationResolver;
	}

	/**
	 *
	 * @return always true
	 */
	protected static ResponseResolver<Boolean> getEmtpyResolver() {
		return emptyResolver;
	}

	static ResponseResolver<Boolean> emptyResolver = new ResponseResolver<Boolean>() {

		@Override
		public Boolean resolve(CloseableHttpResponse response) throws ServerResponseException {
			return true;
		}

	};

	static ResponseResolver<String> headerLocationResolver = new ResponseResolver<String>() {

		private static final String RESPONSE_LOCATION_HEADER_NAME = "Location";
		public String resolve(CloseableHttpResponse response) throws ServerResponseException {

			Header locationHeader = response.getLastHeader(RESPONSE_LOCATION_HEADER_NAME);

			if (locationHeader != null) {
				return locationHeader.getValue();
			} else {
				throw new ServerResponseException(response.getStatusLine().getStatusCode(), String.format("Invalid server response: %s header is not set", RESPONSE_LOCATION_HEADER_NAME));
			}
		}
	};
}
