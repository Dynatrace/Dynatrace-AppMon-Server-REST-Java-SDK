package com.dynatrace.sdk.server.systemprofiles.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="systemprofile")
public class SystemProfileMetadata extends SystemProfile {
    @XmlAttribute
    private Boolean enabled;
    @XmlAttribute
    private Boolean isInteractiveLicensed;

    @XmlElement(name = "agentgroupsreference")
    private AgentGroupsReference agentGroupsReference;
    private String description;

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean isInteractiveLicensed() {
        return this.isInteractiveLicensed;
    }

    public AgentGroupsReference getAgentGroupsReference() {
        return this.agentGroupsReference;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "SystemProfileMetadata{" +
                "enabled=" + this.enabled +
                ", isInteractiveLicensed=" + this.isInteractiveLicensed +
                ", agentGroupReference=" + this.agentGroupsReference +
                ", description='" + this.description + '\'' +
                '}';
    }
}
