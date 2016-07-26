package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = Collectors.ROOT_ELEMENT_NAME)
public class Collectors {
    public static final String ROOT_ELEMENT_NAME = "collectors";

    @XmlAttribute
    private String href;

    @XmlElement(name = CollectorInformation.ROOT_ELEMENT_NAME)
    private final List<CollectorInformation> collectors = new ArrayList<>();

    public List<CollectorInformation> getCollectors() {
        return this.collectors;
    }
}
