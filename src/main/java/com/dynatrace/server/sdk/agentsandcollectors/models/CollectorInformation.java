package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "collectorinformation")
public class CollectorInformation {
    @XmlAttribute
    private String href; //TODO is it necessary?
    private boolean embedded; //TODO is it necessary?

    private String name;
    private String host;
    private boolean isConnected;
    private boolean isLocal;
    private String version;

    /* getters */
    public String getHref() {
        return this.href;
    }

    public boolean isEmbedded() {
        return this.embedded;
    }

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public boolean isLocal() {
        return this.isLocal;
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
                ", isConnected=" + isConnected +
                ", isLocal=" + isLocal +
                ", version='" + version + '\'' +
                '}';
    }
}