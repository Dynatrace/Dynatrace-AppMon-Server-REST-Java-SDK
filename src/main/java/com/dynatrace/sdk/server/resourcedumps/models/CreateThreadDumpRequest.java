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

package com.dynatrace.sdk.server.resourcedumps.models;

public class CreateThreadDumpRequest {
    private String systemProfile;
    private String agentName;
    private String hostName;
    private Integer processId;
    private Boolean isSessionLocked;

    public CreateThreadDumpRequest(String systemProfile, String agentName, String hostName, Integer processId) {
        this.systemProfile = systemProfile;
        this.agentName = agentName;
        this.hostName = hostName;
        this.processId = processId;
    }

    public String getSystemProfile() {
        return systemProfile;
    }

    public void setSystemProfile(String systemProfile) {
        this.systemProfile = systemProfile;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Boolean isSessionLocked() {
        return isSessionLocked;
    }

    /**
     * @param sessionLocked - true to lock the session that is recorded, otherwise false.
     */
    public void setSessionLocked(Boolean sessionLocked) {
        isSessionLocked = sessionLocked;
    }
}
