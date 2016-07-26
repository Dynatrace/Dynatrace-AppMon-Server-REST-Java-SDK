package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum(String.class)
@XmlType
public enum TestStatus {
    @XmlEnumValue("failed")
    FAILED,
    @XmlEnumValue("degraded")
    DEGRADED,
    @XmlEnumValue("volatile")
    VOLATILE,
    @XmlEnumValue("improved")
    IMPROVED,
    @XmlEnumValue("passed")
    PASSED,
    @XmlEnumValue("invalidated")
    INVALIDATED
}