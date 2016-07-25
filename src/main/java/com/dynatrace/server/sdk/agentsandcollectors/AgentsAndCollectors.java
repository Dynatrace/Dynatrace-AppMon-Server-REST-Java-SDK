package com.dynatrace.server.sdk.agentsandcollectors;

import com.dynatrace.server.sdk.DynatraceClient;
import com.dynatrace.server.sdk.Service;
import com.dynatrace.server.sdk.agentsandcollectors.models.AgentInformation;
import com.dynatrace.server.sdk.agentsandcollectors.models.Agents;
import com.dynatrace.server.sdk.agentsandcollectors.models.CollectorInformation;
import com.dynatrace.server.sdk.agentsandcollectors.models.Collectors;
import com.dynatrace.server.sdk.exceptions.ServerConnectionException;
import com.dynatrace.server.sdk.exceptions.ServerResponseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Wraps Dynatrace Server Agents and Collectors REST API providing an easy to use set of methods.
 */
public class AgentsAndCollectors extends Service {
    public static final String AGENTS_EP = "/rest/management/agents";
    public static final String COLLECTORS_EP = "/rest/management/collectors";

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
            URI uri = this.buildURI(String.format(COLLECTORS_EP));
            return this.doGetRequest(uri, Collectors.class);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid uri format", e);
        }
    }
}
