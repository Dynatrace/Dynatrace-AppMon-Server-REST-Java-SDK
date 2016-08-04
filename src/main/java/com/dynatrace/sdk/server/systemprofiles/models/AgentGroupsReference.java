package com.dynatrace.sdk.server.systemprofiles.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "agentgroupsreference")
public class AgentGroupsReference {
    @XmlAttribute
    private String href;

    public AgentGroupsReference(String href) {
        this.href = href;
    }

    public AgentGroupsReference() {
    }

    public String getHref() {
        return this.href;
    }

    @Override
    public String toString() {
        return "AgentGroupsReference{" +
                "href='" + this.href + '\'' +
                '}';
    }
}
