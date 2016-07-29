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

package com.dynatrace.sdk.server.agentsandcollectors.models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = AgentPropertiesInformation.ROOT_ELEMENT_NAME)
public class AgentPropertiesInformation {
    public static final String ROOT_ELEMENT_NAME = "agentProperties";

    private String agentHost;
    private String agentId;
    private String agentVersion;
    private String agentBootstrapVersion;
    private String agentHostAddress;
    private String agentPlatform;
    private String bufferCount;
    private String bufferSaturationThreshold;
    private String bufferSize;
    private String clockFrequency;
    private String cloud;
    private String hiResClock;
    private String hotSensorPlaceable;
    private String hypervisor;
    private String osHyperVFriendly;
    private String instrumentationState;
    private String logFileLocation;
    private String maximumMemory;
    private String operatingSystem;
    private String osArchitecture;
    private String osEdition;
    private String osProductId;
    private String osVersion;
    private String processors;
    private String recoveryEnabled;
    private String startDate;
    private String startUp;
    private String timer;

    //dotNet only
    private String applicationServerVersionDetected;
    private String applicationServerDetected;
    private String cellNameDetected;
    private String clrVendor;
    private String clrVersion;
    private String runtimeVersion;
    private String commandLine;
    private String commandLineMayBeTruncated;
    private String hasCommandLine;
    private String workingDirectory;
    private String hasWorkingDirectory;

    // Mainframe only
    private String smfId;
    private String snaId;
    private String subsystem;
    private String jobname;
    private String queueType;
    private String ccsid;
    private String zosType;
    private String zosRelease;
    private String asid;
    private String regionId;

    /* getters */
    public String getAgentHost() {
        return this.agentHost;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public String getAgentVersion() {
        return this.agentVersion;
    }

    public String getAgentBootstrapVersion() {
        return this.agentBootstrapVersion;
    }

    public String getAgentHostAddress() {
        return this.agentHostAddress;
    }

    public String getAgentPlatform() {
        return this.agentPlatform;
    }

    public String getBufferCount() {
        return this.bufferCount;
    }

    public String getBufferSaturationThreshold() {
        return this.bufferSaturationThreshold;
    }

    public String getBufferSize() {
        return this.bufferSize;
    }

    public String getClockFrequency() {
        return this.clockFrequency;
    }

    public String getCloud() {
        return this.cloud;
    }

    public String getHiResClock() {
        return this.hiResClock;
    }

    public String getHotSensorPlaceable() {
        return this.hotSensorPlaceable;
    }

    public String getHypervisor() {
        return this.hypervisor;
    }

    public String getOsHyperVFriendly() {
        return this.osHyperVFriendly;
    }

    public String getInstrumentationState() {
        return this.instrumentationState;
    }

    public String getLogFileLocation() {
        return this.logFileLocation;
    }

    public String getMaximumMemory() {
        return this.maximumMemory;
    }

    public String getOperatingSystem() {
        return this.operatingSystem;
    }

    public String getOsArchitecture() {
        return this.osArchitecture;
    }

    public String getOsEdition() {
        return this.osEdition;
    }

    public String getOsProductId() {
        return this.osProductId;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getProcessors() {
        return this.processors;
    }

    public String getRecoveryEnabled() {
        return this.recoveryEnabled;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getStartUp() {
        return this.startUp;
    }

    public String getTimer() {
        return this.timer;
    }

    public String getApplicationServerVersionDetected() {
        return this.applicationServerVersionDetected;
    }

    public String getApplicationServerDetected() {
        return this.applicationServerDetected;
    }

    public String getCellNameDetected() {
        return this.cellNameDetected;
    }

    public String getClrVendor() {
        return this.clrVendor;
    }

    public String getClrVersion() {
        return this.clrVersion;
    }

    public String getRuntimeVersion() {
        return this.runtimeVersion;
    }

    public String getCommandLine() {
        return this.commandLine;
    }

    public String getCommandLineMayBeTruncated() {
        return this.commandLineMayBeTruncated;
    }

    public String getHasCommandLine() {
        return this.hasCommandLine;
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public String getHasWorkingDirectory() {
        return this.hasWorkingDirectory;
    }

    public String getSmfId() {
        return this.smfId;
    }

    public String getSnaId() {
        return this.snaId;
    }

    public String getSubsystem() {
        return this.subsystem;
    }

    public String getJobname() {
        return this.jobname;
    }

    public String getQueueType() {
        return this.queueType;
    }

    public String getCcsid() {
        return this.ccsid;
    }

    public String getZosType() {
        return this.zosType;
    }

    public String getZosRelease() {
        return this.zosRelease;
    }

    public String getAsid() {
        return this.asid;
    }

    public String getRegionId() {
        return this.regionId;
    }


    @Override
    public String toString() {
        return "AgentPropertiesInformation{" +
                "agentHost='" + this.agentHost + '\'' +
                ", agentId='" + this.agentId + '\'' +
                ", agentVersion='" + this.agentVersion + '\'' +
                ", agentBootstrapVersion='" + this.agentBootstrapVersion + '\'' +
                ", agentHostAddress='" + this.agentHostAddress + '\'' +
                ", agentPlatform='" + this.agentPlatform + '\'' +
                ", bufferCount='" + this.bufferCount + '\'' +
                ", bufferSaturationThreshold='" + this.bufferSaturationThreshold + '\'' +
                ", bufferSize='" + this.bufferSize + '\'' +
                ", clockFrequency='" + this.clockFrequency + '\'' +
                ", cloud='" + this.cloud + '\'' +
                ", hiResClock='" + this.hiResClock + '\'' +
                ", hotSensorPlaceable='" + this.hotSensorPlaceable + '\'' +
                ", hypervisor='" + this.hypervisor + '\'' +
                ", osHyperVFriendly='" + this.osHyperVFriendly + '\'' +
                ", instrumentationState='" + this.instrumentationState + '\'' +
                ", logFileLocation='" + this.logFileLocation + '\'' +
                ", maximumMemory='" + this.maximumMemory + '\'' +
                ", operatingSystem='" + this.operatingSystem + '\'' +
                ", osArchitecture='" + this.osArchitecture + '\'' +
                ", osEdition='" + this.osEdition + '\'' +
                ", osProductId='" + this.osProductId + '\'' +
                ", osVersion='" + this.osVersion + '\'' +
                ", processors='" + this.processors + '\'' +
                ", recoveryEnabled='" + this.recoveryEnabled + '\'' +
                ", startDate='" + this.startDate + '\'' +
                ", startUp='" + this.startUp + '\'' +
                ", timer='" + this.timer + '\'' +
                ", applicationServerVersionDetected='" + this.applicationServerVersionDetected + '\'' +
                ", applicationServerDetected='" + this.applicationServerDetected + '\'' +
                ", cellNameDetected='" + this.cellNameDetected + '\'' +
                ", clrVendor='" + this.clrVendor + '\'' +
                ", clrVersion='" + this.clrVersion + '\'' +
                ", runtimeVersion='" + this.runtimeVersion + '\'' +
                ", commandLine='" + this.commandLine + '\'' +
                ", commandLineMayBeTruncated='" + this.commandLineMayBeTruncated + '\'' +
                ", hasCommandLine='" + this.hasCommandLine + '\'' +
                ", workingDirectory='" + this.workingDirectory + '\'' +
                ", hasWorkingDirectory='" + this.hasWorkingDirectory + '\'' +
                ", smfId='" + this.smfId + '\'' +
                ", snaId='" + this.snaId + '\'' +
                ", subsystem='" + this.subsystem + '\'' +
                ", jobname='" + this.jobname + '\'' +
                ", queueType='" + this.queueType + '\'' +
                ", ccsid='" + this.ccsid + '\'' +
                ", zosType='" + this.zosType + '\'' +
                ", zosRelease='" + this.zosRelease + '\'' +
                ", asid='" + this.asid + '\'' +
                ", regionId='" + this.regionId + '\'' +
                '}';
    }
}