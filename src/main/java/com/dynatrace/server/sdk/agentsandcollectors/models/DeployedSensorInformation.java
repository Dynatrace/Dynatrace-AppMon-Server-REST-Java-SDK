package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "deployedSensor")
public class DeployedSensorInformation {
    private String methodName;
    private String className;
    private List<String> argument;
    private String returns;
    private String sensor;
    private String flags;
    private String sourceFile;
    private String lineNumber;
}
