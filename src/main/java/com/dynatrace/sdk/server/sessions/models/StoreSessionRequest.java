/*
 * Dynatrace Server SDK
 * Copyright (c) 2008-2016, DYNATRACE LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  Neither the name of the dynaTrace software nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.dynatrace.sdk.server.sessions.models;

import com.dynatrace.sdk.server.sessions.Sessions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Store Session Request class required for {@link Sessions#store} method
 * <strong>
 *     The timeframe parameters {@link StoreSessionRequest#timeframeStart} and {@link StoreSessionRequest#timeframeEnd}
 *     must be formatted according to ISO 8601, without a time zone specification, in the form yyyy-MM-dd'T'HH:mm:ss or yyyy-MM-dd'T'HH:mm:ss.S
 * </strong>
 */
public class StoreSessionRequest {
    private String systemProfile;

    private RecordingOption recordingOption;
    private Boolean isSessionLocked;
    private Boolean appendTimestamp;
    private String timeframeStart;
    private String timeframeEnd;
    private String storedSessionName;
    private List<String> labels = new ArrayList<>();

    public StoreSessionRequest(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    public void setSystemProfile(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public RecordingOption getRecordingOption() {
        return this.recordingOption;
    }

    public void setRecordingOption(RecordingOption recordingOption) {
        this.recordingOption = recordingOption;
    }

    public Boolean isSessionLocked() {
        return this.isSessionLocked;
    }

    public void setSessionLocked(boolean sessionLocked) {
        this.isSessionLocked = sessionLocked;
    }

    public Boolean isAppendTimestamp() {
        return this.appendTimestamp;
    }

    public void setAppendTimestamp(boolean appendTimestamp) {
        this.appendTimestamp = appendTimestamp;
    }

    public String getTimeframeStart() {
        return this.timeframeStart;
    }

    public void setTimeframeStart(String timeframeStart) {
        this.timeframeStart = timeframeStart;
    }

    public void setTimeframeStart(Date timeframeStart) {
        this.timeframeStart = this.getTimeframeDateFormat().format(timeframeStart);
    }

    public String getTimeframeEnd() {
        return this.timeframeEnd;
    }

    public void setTimeframeEnd(String timeframeEnd) {
        this.timeframeEnd = timeframeEnd;
    }

    public void setTimeframeEnd(Date timeframeEnd) {
        this.timeframeEnd = this.getTimeframeDateFormat().format(timeframeEnd);
    }

    public String getStoredSessionName() {
        return this.storedSessionName;
    }

    public void setStoredSessionName(String storedSessionName) {
        this.storedSessionName = storedSessionName;
    }
    public List<String> getLabels() {
        return this.labels;
    }

    /**
     * @param label - Descriptive text to mark the stored session
     */
    public void addLabel(String label) {
        this.labels.add(label);
    }

    @Override
    public String toString() {
        return "StoreSessionRequest{" +
                "systemProfile='" + this.systemProfile + '\'' +
                ", recordingOption=" + this.recordingOption +
                ", isSessionLocked=" + this.isSessionLocked +
                ", appendTimestamp=" + this.appendTimestamp +
                ", timeframeStart='" + this.timeframeStart + '\'' +
                ", timeframeEnd='" + this.timeframeEnd + '\'' +
                ", storedSessionName='" + this.storedSessionName + '\'' +
                ", labels=" + this.labels +
                '}';
    }

    private DateFormat getTimeframeDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
    }
}
