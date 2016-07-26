package com.dynatrace.server.sdk.testautomation.adapters;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DoubleMeasureAdapterTest {
    private DoubleMeasureAdapter dma = new DoubleMeasureAdapter();

    @Test
    public void marshall() throws Exception {
        assertThat(this.dma.unmarshal("-INF"), is(Double.NEGATIVE_INFINITY));
        assertThat(this.dma.unmarshal("INF"), is(Double.POSITIVE_INFINITY));
        assertThat(this.dma.unmarshal("15.5"), is(15.5));
    }

    @Test
    public void unmarshall() throws Exception {
        assertThat(this.dma.marshal(Double.POSITIVE_INFINITY), is("INF"));
        assertThat(this.dma.marshal(Double.NEGATIVE_INFINITY), is("-INF"));
        assertThat(this.dma.marshal(-15.2), is("-15.2"));
    }
}
