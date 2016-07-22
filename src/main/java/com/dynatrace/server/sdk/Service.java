package com.dynatrace.server.sdk;

import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Service {
    private static final XPathExpression ERROR_EXPRESSION;

    static {
        try {
            ERROR_EXPRESSION = XPathFactory.newInstance().newXPath().compile("/error/@reason");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T inputStreamToObject(InputStream xml, Class<T> clazz) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        try {
            return (T) unmarshaller.unmarshal(xml);
        } finally {
            xml.close();
        }
    }

    private static String objectToString(Object object) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(object, writer);
        return writer.getBuffer().toString();
    }

    protected final ServerConfiguration configuration;
    protected final HttpClient client;

    protected Service(ServerConfiguration configuration) {
        this.configuration = configuration;
        this.client = Utils.buildClient(configuration);
    }

    protected URI buildURI(String endpoint, String query, String... arguments) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(configuration.isSSL() ? "https" : "http");
        uriBuilder.setHost(configuration.getHost());
        uriBuilder.setPort(configuration.getPort());
        uriBuilder.setPath(String.format(endpoint, arguments));
        uriBuilder.setCustomQuery(query);
        return uriBuilder.build();
    }

    protected <T> T doPostRequest(URI uri, Object entity, Class<T> responseClass) throws ServerConnectionException, ServerResponseException {
        HttpPost post = new HttpPost(uri);
        try {
            post.setEntity(new StringEntity(objectToString(entity)));
        } catch (JAXBException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Provided request couldn't be serialized.", e);
        }
        post.setHeader("Content-Type", "application/xml");
        post.setHeader("Accept", "application/xml");
        try {
            HttpResponse response = this.client.execute(post);
            if (response.getStatusLine().getStatusCode() >= 300 || response.getStatusLine().getStatusCode() < 200) {
                throw new ServerResponseException(response.getStatusLine().getReasonPhrase());
            }
            try (InputStream is = response.getEntity().getContent()) {
//                String content = EntityUtils.toString(response.getEntity());
//                System.out.println(content);
//                InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
                T obj = inputStreamToObject(response.getEntity().getContent(), responseClass);
                return obj;
            } catch (JAXBException e) {
                throw new ServerResponseException("Could not unmarshall response into given object", e);
            }
        } catch (IOException e) {
            throw new ServerConnectionException("Could not connect to Dynatrace Server", e);
        }
    }
}
