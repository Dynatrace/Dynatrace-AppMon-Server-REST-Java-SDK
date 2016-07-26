package com.dynatrace.server.sdk.sessions.models;

public enum RecordingOption {
    // All PurePaths including time series
    ALL("all"),
    // Only PurePaths marked as violated, including time series.
    VIOLATIONS("violations"),
    // Time series only
    TIME_SERIES("timeseries");

    private final String internal;

    RecordingOption(String internal) {
        this.internal = internal;
    }

    public String getInternal() {
        return this.internal;
    }
}

