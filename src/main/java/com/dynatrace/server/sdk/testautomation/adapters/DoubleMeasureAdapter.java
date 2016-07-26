package com.dynatrace.server.sdk.testautomation.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleMeasureAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String v) throws Exception {
        // Dynatrace server returns Infinities in a -INF/INF format instead of INFINITY/-INFINITY
        // causing unmarshalling error
        if (v.equals("-INF")) {
            return Double.NEGATIVE_INFINITY;
        }
        if (v.equals("INF")) {
            return Double.POSITIVE_INFINITY;
        }
        return Double.valueOf(v);
    }

    @Override
    public String marshal(Double v) throws Exception {
        if (v.equals(Double.NEGATIVE_INFINITY)) {
            return "-INF";
        }
        if (v.equals(Double.POSITIVE_INFINITY)) {
            return "INF";
        }
        return String.valueOf(v);
    }
}
