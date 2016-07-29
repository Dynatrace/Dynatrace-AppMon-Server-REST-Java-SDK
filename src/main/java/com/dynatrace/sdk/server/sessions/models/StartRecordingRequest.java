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

import java.util.ArrayList;
import java.util.List;

public class StartRecordingRequest {
    private String systemProfile;
    private String presentableName;
    private String description;
    private Boolean isTimestampAllowed;
    private RecordingOption recordingOption;
    private Boolean isSessionLocked;
    private List<String> labels = new ArrayList<>();

    public StartRecordingRequest(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    public void setSystemProfile(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getPresentableName() {
        return this.presentableName;
    }

    public void setPresentableName(String presentableName) {
        this.presentableName = presentableName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isTimestampAllowed() {
        return this.isTimestampAllowed;
    }

    /**
     * @param timestampAllowed - {@code true} to append timestamp information to the recorded session name, otherwise {@code false}.
     */
    public void setTimestampAllowed(Boolean timestampAllowed) {
        this.isTimestampAllowed = timestampAllowed;
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

    /**
     * @param sessionLocked - true to lock the session that is recorded, otherwise false.
     */
    public void setSessionLocked(Boolean sessionLocked) {
        this.isSessionLocked = sessionLocked;
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
        return "StartRecordingRequest{" +
                "systemProfile='" + this.systemProfile + '\'' +
                ", presentableName='" + this.presentableName + '\'' +
                ", description='" + this.description + '\'' +
                ", isTimestampAllowed=" + this.isTimestampAllowed +
                ", recordingOption=" + this.recordingOption +
                ", isSessionLocked=" + this.isSessionLocked +
                ", labels=" + this.labels +
                '}';
    }
}
