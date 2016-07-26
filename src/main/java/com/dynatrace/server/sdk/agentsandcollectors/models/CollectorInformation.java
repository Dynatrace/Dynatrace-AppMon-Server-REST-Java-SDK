package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = CollectorInformation.ROOT_ELEMENT_NAME)
public class CollectorInformation {
    public static final String ROOT_ELEMENT_NAME = "collectorinformation";

    @XmlAttribute
    private String href;

    private String name;
    private String host;
    private String version;

    private Boolean connected;
    private Boolean embedded;
    private Boolean local;

    /* getters */
    public String getHref() { return this.href; }

    public Boolean isEmbedded() {
        return this.embedded;
    }

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public Boolean isConnected() {
        return this.connected;
    }

    public Boolean isLocal() {
        return this.local;
    }

    public String getVersion() {
        return this.version;
    }


    @Override
    public String toString() {
        return "CollectorInformation{" +
                "href='" + href + '\'' +
                ", embedded=" + embedded +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", connected=" + connected +
                ", local=" + local +
                ", version='" + version + '\'' +
                '}';
    }
}