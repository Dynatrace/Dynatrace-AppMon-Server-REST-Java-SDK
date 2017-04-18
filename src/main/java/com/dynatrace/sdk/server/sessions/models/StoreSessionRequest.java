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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.dynatrace.sdk.server.sessions.Sessions;

/**
 * Store Session Request class required for {@link Sessions#store} method
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class StoreSessionRequest {

	/** System profile is used as url param, not included in request body. */
	@XmlTransient
    private String systemProfile;

    @XmlAttribute(name="recordingoption")
    private RecordingOption recordingOption;
    @XmlAttribute(name="locksession")
    private Boolean isSessionLocked;
    @XmlAttribute(name="appendtimestamp")
    private Boolean appendTimestamp;
    @XmlAttribute(name="timeframestart")
    private Date timeframeStart;
    @XmlAttribute(name="timeframeend")
    private Date timeframeEnd;
    @XmlAttribute(name="sessionname")
    private String storedSessionName;
    @XmlElement(name="labels")
    private List<String> labels = new ArrayList<>();
    @XmlElement
    private String description;


    public StoreSessionRequest() {
	}

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

    public Date getTimeframeStart() {
		return timeframeStart;
	}

	public void setTimeframeStart(Date timeframeStart) {
		this.timeframeStart = timeframeStart;
	}

	public Date getTimeframeEnd() {
		return timeframeEnd;
	}

	public void setTimeframeEnd(Date timeframeEnd) {
		this.timeframeEnd = timeframeEnd;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


}
