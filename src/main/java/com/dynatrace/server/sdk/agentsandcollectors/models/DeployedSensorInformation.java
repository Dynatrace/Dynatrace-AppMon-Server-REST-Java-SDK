package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = DeployedSensorInformation.ROOT_ELEMENT_NAME)
public class DeployedSensorInformation {
    public static final String ROOT_ELEMENT_NAME = "deployedSensor";

    private String methodName;
    private String className;
    private List<String> argument;
    private String returns;
    private String sensor;
    private String flags;
    private String sourceFile;
    private String lineNumber;


    @Override
    public String toString() {
        return "DeployedSensorInformation{" +
                "methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                ", argument=" + argument +
                ", returns='" + returns + '\'' +
                ", sensor='" + sensor + '\'' +
                ", flags='" + flags + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                '}';
    }
}
