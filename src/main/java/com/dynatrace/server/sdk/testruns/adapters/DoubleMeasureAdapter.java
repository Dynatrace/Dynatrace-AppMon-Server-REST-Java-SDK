package com.dynatrace.server.sdk.testruns.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleMeasureAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String v) throws Exception {
        // Dynatrace server returns Infinities in a -INF/INF format instead of INFINITY/-INFINITY
        // causing unmarshalling error
        if(v.equals("-INF")) {
            return Double.NEGATIVE_INFINITY;
        }
        if(v.equals("INF")) {
            return Double.POSITIVE_INFINITY;
        }
        return Double.valueOf(v);
    }

    @Override
    public String marshal(Double v) throws Exception {
        return String.valueOf(v);
    }
}
