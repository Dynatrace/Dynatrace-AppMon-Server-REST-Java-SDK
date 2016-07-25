package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "collectors")
public class Collectors {

    @XmlAttribute
    private String href; //TODO is it necessary?

    private final List<CollectorInformation> collectorinformation = new ArrayList<>();

    public List<CollectorInformation> getCollectors() {
        return this.collectorinformation;
    }
}
