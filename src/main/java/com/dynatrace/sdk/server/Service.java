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

import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class Service {
    private final DynatraceClient client;

    protected Service(DynatraceClient client) {
        this.client = client;
    }

    protected static XPathExpression compileValueExpression() {
        try {
            return XPathFactory.newInstance().newXPath().compile("/result/@value");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
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

    public static StringEntity xmlObjectToEntity(Object object) {
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

    protected CloseableHttpResponse doRequest(HttpRequestBase request) throws ServerConnectionException, ServerResponseException {
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

    protected <T> T parseResponse(CloseableHttpResponse response, Class<T> responseClass) throws ServerResponseException {
        try {
            return xmlInputStreamToObject(response.getEntity().getContent(), responseClass);
        } catch (IOException | JAXBException e) {
            throw new ServerResponseException(response.getStatusLine().getStatusCode(), String.format("Could not unmarshall response into given object: %s", e.getMessage()), e);
        }
    }

    protected CloseableHttpResponse doPostRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);
        return this.doRequest(post);
    }

    protected <T> T doPostRequest(URI uri, HttpEntity entity, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {
        try (CloseableHttpResponse response = this.doPostRequest(uri, entity)) {
            return this.parseResponse(response, responseClass);
        } catch (IOException e) {
            throw new ServerConnectionException(String.format("Could not connect to Dynatrace Server: %s", e.getMessage()), e);
        }
    }

    protected CloseableHttpResponse doPutRequest(URI uri, HttpEntity entity) throws ServerConnectionException, ServerResponseException {
        HttpPut put = new HttpPut(uri);
        put.setEntity(entity);
        return this.doRequest(put);
    }

    protected <T> T doPutRequest(URI uri, HttpEntity entity, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {
        try (CloseableHttpResponse response = this.doPutRequest(uri, entity)) {
            return this.parseResponse(response, responseClass);
        } catch (IOException e) {
            throw new ServerConnectionException(String.format("Could not connect to Dynatrace Server: %s", e.getMessage()), e);
        }
    }

    protected CloseableHttpResponse doGetRequest(URI uri) throws ServerConnectionException, ServerResponseException {
        return this.doRequest(new HttpGet(uri));
    }

    protected <T> T doGetRequest(URI uri, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {
        try (CloseableHttpResponse response = this.doGetRequest(uri)) {
            return this.parseResponse(response, responseClass);
        } catch (IOException e) {
            throw new ServerConnectionException(String.format("Could not connect to Dynatrace Server: %s", e.getMessage()), e);
        }
    }
}
