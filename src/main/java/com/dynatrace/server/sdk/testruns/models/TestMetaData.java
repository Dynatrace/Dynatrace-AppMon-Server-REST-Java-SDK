package com.dynatrace.server.sdk.testruns.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
public class TestMetaData {
    @XmlAnyAttribute
    private Map<QName, Object> entries = new HashMap<>();

    public void setValue(String name, String value) {
        this.entries.put(new QName(name), value);
    }

    public Map<QName, Object> getValues() {
        return this.entries;
    }
}
