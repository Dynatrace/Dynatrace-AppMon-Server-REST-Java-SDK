package com.dynatrace.server.sdk.testautomation.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum CreationMode {
    @XmlEnumValue("MANUAL")
    MANUAL,
    @XmlEnumValue("AUTO")
    AUTO
}
