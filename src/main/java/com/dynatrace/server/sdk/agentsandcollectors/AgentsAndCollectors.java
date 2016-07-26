package com.dynatrace.server.sdk.agentsandcollectors;

import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.agentsandcollectors.models.AgentInformation;
import com.dynatrace.server.sdk.agentsandcollectors.models.Agents;
import com.dynatrace.server.sdk.agentsandcollectors.models.CollectorInformation;
import com.dynatrace.server.sdk.agentsandcollectors.models.Collectors;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Wraps Dynatrace Server Agents and Collectors REST API providing an easy to use set of methods.
 */
public class AgentsAndCollectors extends Service {
    public static final String AGENTS_EP = "/rest/management/agents";
    public static final String COLLECTORS_EP = "/rest/management/collectors/%s";
    public static final String HOT_SENSOR_PLACEMENT_EP = "/rest/management/agents/%d/hotsensorplacement";
    public static final String COLLECTOR_RESTART_EP = "/rest/management/collector/%s/restart";
    public static final String COLLECTOR_SHUTDOWN_EP = "/rest/management/collector/%s/shutdown";

    private static final XPathExpression SIMPLE_RESULT_EXPRESSION;

    static {
        try {
            SIMPLE_RESULT_EXPRESSION = XPathFactory.newInstance().newXPath().compile("/result/@value");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public AgentsAndCollectors(DynatraceClient client) {
        super(client);
    }

    /**
     * Fetches list of all {@link AgentInformation}
     *
     * @return {@link Agents} instance containing a {@link List} of {@link AgentInformation}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Agents fetchAgents() throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(AGENTS_EP));
            return this.doGetRequest(uri, Agents.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid uri format", e);
        }
    }

    /**
     * Fetches list of all {@link CollectorInformation}
     *
     * @return {@link Collectors} instance containing a {@link List} of {@link CollectorInformation}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Collectors fetchCollectors() throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(COLLECTORS_EP, ""));
            return this.doGetRequest(uri, Collectors.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid uri format", e);
        }
    }

    /**
     * Fetches single {@link CollectorInformation}
     *
     * @param collectorAndHostName - name and host of the Collector, delimited by @ (at) symbol, e.g.: dynatraceCollector@host.dynatrace.org
     * @return {@link CollectorInformation} instance containing a single Collector
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public CollectorInformation fetchCollector(String collectorAndHostName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(COLLECTORS_EP, collectorAndHostName));
            return this.doGetRequest(uri, CollectorInformation.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid collectorAndHostName format", e);
        }
    }

    /**
     * Performs a Hot Sensor Placement on a Dynatrace Agent
     *
     * @param agentId - Dynatrace Agent ID
     * @return {@link Boolean} instance describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Boolean hotSensorPlacement(Integer agentId) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(HOT_SENSOR_PLACEMENT_EP, agentId));
            CloseableHttpResponse response = this.doGetRequest(uri);

            String result = null;

            try (InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid agentId", e);
        }
    }

    /**
     * Performs restart of the Collector
     *
     * @param collectorName - name of the Collector
     * @return {@link Boolean} that describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException whenever parsing a response fails or invalid status code is provided
     */
    public Boolean restartCollector(String collectorName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(COLLECTOR_RESTART_EP, collectorName));
            String result = null;

            try (CloseableHttpResponse response = this.doPostRequest(uri, null, Service.XML_CONTENT_TYPE);
                 InputStream is = response.getEntity().getContent()) {
                    // xpath is reasonable for parsing such a small entity
                    result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid collectorName", e);
        }
    }

    /**
     * Performs shutdown of the Collector
     *
     * @param collectorName - name of the Collector
     * @return {@link Boolean} instance describes that the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Boolean shutdownCollector(String collectorName) throws ServerConnectionException, ServerResponseException {
        try {
            URI uri = this.buildURI(String.format(COLLECTOR_SHUTDOWN_EP, collectorName));
            String result = null;

            try (CloseableHttpResponse response = this.doPostRequest(uri, null, Service.XML_CONTENT_TYPE);
                 InputStream is = response.getEntity().getContent()) {
                // xpath is reasonable for parsing such a small entity
                result = SIMPLE_RESULT_EXPRESSION.evaluate(new InputSource(is));
            } catch (XPathExpressionException e) {
                // if it occurs, it means result == false
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return (result != null) && (result.equals("true"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid collectorName", e);
        }
    }






}
