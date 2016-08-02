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

package com.dynatrace.sdk.server.memorydumps.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "memorydump")
@XmlAccessorType(XmlAccessType.FIELD)
public class MemoryDump {
    @XmlAttribute(name="resourceid")
    private String resourceId;
    @XmlAttribute(name="sessionid")
    private String sessionId;
    private String name;
    private JobState state;
    private String description;
    @XmlElement(name = "ispostprocessed")
    private Boolean isPostProcessed;
    @XmlElement(name = "issessionlocked")
    private Boolean isSessionLocked;
    @XmlElement(name = "iscaptureprimitives")
    private Boolean isCapturePrimitives;
    @XmlElement(name = "iscapturestrings")
    private Boolean isCaptureStrings;
    @XmlElement(name = "isdogc")
    private Boolean isDogc;
    @XmlElement(name = "storedsessiontype")
    private StoredSessionType storedSessionType;
    @XmlElement(name="agentpattern")
    private AgentPattern agentPattern;
    @XmlElement(name = "usedmemory")
    private Long usedMemory;
    @XmlElement(name = "processmemory")
    private Long processMemory;
    private Integer classes;
    private Integer objects;

    public String getResourceId() {
        return this.resourceId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getName() {
        return this.name;
    }

    public JobState getState() {
        return this.state;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean isPostProcessed() {
        return this.isPostProcessed;
    }

    public Boolean isSessionLocked() {
        return this.isSessionLocked;
    }

    public Boolean isCapturePrimitives() {
        return this.isCapturePrimitives;
    }

    public Boolean isCaptureStrings() {
        return this.isCaptureStrings;
    }

    public Boolean isDogc() {
        return this.isDogc;
    }

    public StoredSessionType getStoredSessionType() {
        return this.storedSessionType;
    }

    public AgentPattern getAgentPattern() {
        return this.agentPattern;
    }

    public Long getUsedMemory() {
        return this.usedMemory;
    }

    public Long getProcessMemory() {
        return this.processMemory;
    }

    public Integer getClasses() {
        return this.classes;
    }

    public Integer getObjects() {
        return this.objects;
    }

    @Override
    public String toString() {
        return "MemoryDump{" +
                "resourceId='" + this.resourceId + '\'' +
                ", sessionId='" + this.sessionId + '\'' +
                ", name='" + this.name + '\'' +
                ", state=" + this.state +
                ", description='" + this.description + '\'' +
                ", isPostProcessed=" + this.isPostProcessed +
                ", isSessionLocked=" + this.isSessionLocked +
                ", isCapturePrimitives=" + this.isCapturePrimitives +
                ", isCaptureStrings=" + this.isCaptureStrings +
                ", isDogc=" + this.isDogc +
                ", storedSessionType=" + this.storedSessionType +
                ", agentPattern=" + this.agentPattern +
                ", usedMemory=" + this.usedMemory +
                ", processMemory=" + this.processMemory +
                ", classes=" + this.classes +
                ", objects=" + this.objects +
                '}';
    }
}
