package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum TestCategory {
    @XmlEnumValue("unit")
    UNIT("unit"),
    @XmlEnumValue("uidriven")
    UI_DRIVEN("uidriven"),
    @XmlEnumValue("performance")
    PERFORMANCE("performance"),
    @XmlEnumValue("webapi")
    WEB_API("webapi"),
    @XmlEnumValue("external")
    EXTERNAL("external");

    private String internalName;

    TestCategory(String internalName) {
        this.internalName = internalName;
    }

    public String toString() {
        return this.internalName;
    }
}
