package com.dynatrace.server.sdk;

import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
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
    private static final XPathExpression ERROR_EXPRESSION;
    public static final String XML_CONTENT_TYPE = "application/xml";

    static {
        try {
            ERROR_EXPRESSION = XPathFactory.newInstance().newXPath().compile("/error/@reason");
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
            return new StringEntity(writer.getBuffer().toString());
        } catch (JAXBException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Provided request couldn't be serialized.", e);
        }
    }

    private final DynatraceClient client;

    protected Service(DynatraceClient client) {
        this.client = client;
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
                    error = ERROR_EXPRESSION.evaluate(new InputSource(is));
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
            throw new ServerResponseException(response.getStatusLine().getStatusCode(), "Could not unmarshall response into given object", e);
        }
    }

    protected CloseableHttpResponse doPostRequest(URI uri, HttpEntity entity, String contentType) throws ServerConnectionException, ServerResponseException {
        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);
        post.setHeader("Content-Type", contentType);
        return this.doRequest(post);
    }

    protected <T> T doPostRequest(URI uri, HttpEntity entity, String contentType, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {
        try (CloseableHttpResponse response = this.doPostRequest(uri, entity, contentType)) {
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
