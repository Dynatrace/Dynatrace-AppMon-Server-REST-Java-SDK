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
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.xml.sax.InputSource;

import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.response.models.ResultResponse;

public abstract class Service {
    private final DynatraceClient client;

    protected Service(DynatraceClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    private static <T> T xmlInputStreamToObject(InputStream xml, Class<T> clazz) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        try {
            return (T) unmarshaller.unmarshal(xml);
        } finally {
            xml.close();
        }
    }

    protected static StringEntity xmlObjectToEntity(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(object, writer);
            StringEntity entity = new StringEntity(writer.getBuffer().toString());
            entity.setContentType("application/xml");
            return entity;
        } catch (JAXBException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException(String.format("Provided request couldn't be serialized: %s", e.getMessage()), e);
        }
    }

    protected URI buildURI(String path, NameValuePair... params) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(this.client.getConfiguration().isSSL() ? "https" : "http");
        uriBuilder.setHost(this.client.getConfiguration().getHost());
        uriBuilder.setPort(this.client.getConfiguration().getPort());
        uriBuilder.setPath(path);
        uriBuilder.setParameters(params);
        return uriBuilder.build();
    }

    private CloseableHttpResponse doRequest(HttpRequestBase request) throws ServerConnectionException, ServerResponseException {
        request.setHeader("Accept", "*/xml");

        request.setHeader("Authorization", "Basic " + Base64.encodeBase64String((this.client.getConfiguration().getName() + ":" + this.client.getConfiguration().getPassword()).getBytes()));
        try {
            CloseableHttpResponse response = this.client.getClient().execute(request);
            if (response.getStatusLine().getStatusCode() >= 300 || response.getStatusLine().getStatusCode() < 200) {
                String error = null;
                // dynatrace often returns an error message along with a status code
                // we try to parse it, if that doesn't work we use a code bound message
                // as a reason in exception
                try (InputStream is = response.getEntity().getContent()) {
                    // xpath is reasonable for parsing such a small entity
                    error = XPathFactory.newInstance().newXPath().compile("/error/@reason").evaluate(new InputSource(is));
                } catch (XPathExpressionException e) {
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

    private <T> T parseResponse(CloseableHttpResponse response, Class<T> responseClass) throws ServerResponseException {
        try {
            return xmlInputStreamToObject(response.getEntity().getContent(), responseClass);
        } catch (IOException | JAXBException e) {
            throw new ServerResponseException(response.getStatusLine().getStatusCode(), String.format("Could not unmarshall response into given object: %s", e.getMessage()), e);
        }
    }

    protected CloseableHttpResponse doPutRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
        HttpPut put = new HttpPut(uri);
        put.setEntity(entity);
        return this.doRequest(put);
    }


    public <T> T doPutRequest(String uriString, Object entityObject, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {

    	HttpEntity entity = entityObject == null ? null : Service.xmlObjectToEntity(entityObject);

        try (CloseableHttpResponse response = this.doPutRequest(buildURI(uriString), entity)) {

        	return this.parseResponse(response, responseClass);

        } catch (IOException e) {
        	throw new RuntimeException("Could not close http response:" + e.getMessage(), e);
        } catch (URISyntaxException e) {
        	throw new IllegalArgumentException("Invalid uri: " + uriString + ". " + e.getMessage(), e);
		}
    }

    public <T> T doGetRequest(String uriString, Class<T> responseClass, NameValuePair ... params) throws ServerConnectionException, ServerResponseException {

    	try (CloseableHttpResponse response = this.doRequest(new HttpGet(buildURI(uriString, params)))) {

    		return this.parseResponse(response, responseClass);

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid uri: " + uriString + ", params: " + params + " . " + e.getMessage(), e);
        } catch (IOException e) {
        	throw new RuntimeException("Could not close http response: " + e.getMessage(), e);
		}

    }

	public ResultResponse doGetRequest(String uriString, NameValuePair ... params) throws ServerResponseException, ServerConnectionException {

		return this.doGetRequest(uriString, ResultResponse.class, params);
	}


    private CloseableHttpResponse doPostRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);
        return this.doRequest(post);
    }

    private <T> T doPostRequest(String uriString, Class<T> responseClass, HttpEntity entity) throws ServerConnectionException, ServerResponseException {

    	try (CloseableHttpResponse response = this.doPostRequest(this.buildURI(uriString), entity)) {
            return this.parseResponse(response, responseClass);
        } catch (IOException e) {
            throw new RuntimeException("Could not close http response:" + e.getMessage(), e);
        } catch (URISyntaxException e) {
        	throw new IllegalArgumentException("Invalid uri: " + e.getMessage(), e);
		}
    }

	public <T> T doPostRequest(String uriString, Class<T> responseClass, List<NameValuePair> entityParams) throws ServerResponseException, ServerConnectionException {

		HttpEntity entity;
		try {
			entity = entityParams == null || entityParams.isEmpty() ? null : new UrlEncodedFormEntity(entityParams);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(String.format("Invalid parameters[%s] format: %s", entityParams.toString(), e.getMessage()), e);
		}

		return this.doPostRequest(uriString, responseClass, entity);
	}

    public <T> T doPostRequest(String uriString, Class<T> responseClass, Object entityObject) throws ServerConnectionException, ServerResponseException {

		HttpEntity entity = entityObject == null ? null : Service.xmlObjectToEntity(entityObject);
		return this.doPostRequest(uriString, responseClass, entity);
    }

	public ResultResponse doPostRequest(String uriString, List<NameValuePair> entityParams) throws ServerResponseException, ServerConnectionException {

		return this.doPostRequest(uriString, ResultResponse.class, entityParams);
	}


}
