package com.dynatrace.server.sdk.sessions.models;

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
}
