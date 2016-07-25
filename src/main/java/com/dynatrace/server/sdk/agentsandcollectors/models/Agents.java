package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "agents")
public class Agents {
    @XmlElement(name = AgentInformation.ROOT_ELEMENT_NAME)
    private final List<AgentInformation> agents = new ArrayList<>();

    public List<AgentInformation> getAgents() {
        return this.agents;
    }
}
