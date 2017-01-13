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

package com.dynatrace.sdk.server.agentsandcollectors;

import java.util.List;

import com.dynatrace.sdk.server.DynatraceClient;
import com.dynatrace.sdk.server.Service;
import com.dynatrace.sdk.server.agentsandcollectors.models.AgentInformation;
import com.dynatrace.sdk.server.agentsandcollectors.models.Agents;
import com.dynatrace.sdk.server.agentsandcollectors.models.CollectorInformation;
import com.dynatrace.sdk.server.agentsandcollectors.models.Collectors;
import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.response.models.ResultResponse;

/**
 * Wraps Dynatrace Server Agents and Collectors REST API providing an easy to use set of methods.
 */
public class AgentsAndCollectors extends Service {
    public static final String AGENTS_EP = "/rest/management/agents";
    public static final String COLLECTORS_EP = "/rest/management/collectors/%s";
    public static final String HOT_SENSOR_PLACEMENT_EP = "/rest/management/agents/%d/hotsensorplacement";
    public static final String COLLECTOR_RESTART_EP = "/rest/management/collector/%s/restart";
    public static final String COLLECTOR_SHUTDOWN_EP = "/rest/management/collector/%s/shutdown";

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
    	return this.doGetRequest(AGENTS_EP, getBodyResponseResolver(Agents.class));
    }

    /**
     * Fetches list of all {@link CollectorInformation}
     *
     * @return {@link Collectors} instance containing a {@link List} of {@link CollectorInformation}
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public Collectors fetchCollectors() throws ServerConnectionException, ServerResponseException {
    	return this.doGetRequest(String.format(COLLECTORS_EP, ""), getBodyResponseResolver(Collectors.class));
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
        return this.doGetRequest(String.format(COLLECTORS_EP, collectorAndHostName), getBodyResponseResolver(CollectorInformation.class));

    }

    /**
     * Performs a Hot Sensor Placement on a Dynatrace Agent
     *
     * @param agentId - Dynatrace Agent ID
     * @return boolean describing if the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean placeHotSensor(int agentId) throws ServerConnectionException, ServerResponseException {
    	return this.doGetRequest(String.format(HOT_SENSOR_PLACEMENT_EP, agentId), getBodyResponseResolver(ResultResponse.class)).getValueAsBoolean();
    }

    /**
     * Performs restart of the Collector
     *
     * @param collectorName - name of the Collector
     * @return boolean describing if the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean restartCollector(String collectorName) throws ServerConnectionException, ServerResponseException {
    	return this.doPostRequest(String.format(COLLECTOR_RESTART_EP, collectorName), null).getValueAsBoolean();
    }

    /**
     * Performs shutdown of the Collector
     *
     * @param collectorName - name of the Collector
     * @return boolean describing if the request was executed successfully
     * @throws ServerConnectionException whenever connecting to the Dynatrace server fails
     * @throws ServerResponseException   whenever parsing a response fails or invalid status code is provided
     */
    public boolean shutdownCollector(String collectorName) throws ServerConnectionException, ServerResponseException {
    	return this.doPostRequest(String.format(COLLECTOR_SHUTDOWN_EP, collectorName), null).getValueAsBoolean();
    }
}
