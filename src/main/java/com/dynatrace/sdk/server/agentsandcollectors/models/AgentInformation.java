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

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = AgentInformation.ROOT_ELEMENT_NAME)
public class AgentInformation {
    public static final String ROOT_ELEMENT_NAME = "agentinformation";

    private CollectorInformation collectorinformation;
    private AgentPropertiesInformation agentProperties;

    @XmlElement(name = "agentGroup")
    private String agentGroupLabel;

    @XmlElement(name = "configuration")
    private String configurationName;

    private Boolean connected;
    private Boolean capture;
    private Boolean licenseOk;
    private Boolean agentConfigured;
    private Long startupTimeUTC;

    private String name;
    private Integer agentId;
    private Integer processId;
    private String technologyType;
    private Byte technologyTypeId;
    private String host;
    private Long eventCount;
    private Integer classLoadCount;
    private Integer totalClassLoadCount;
    private Boolean isHotUpdateable;
    private String licenseInformation;
    private Long skippedEvents;
    private Long skippedPurePaths;
    private Double totalExecutionTime;
    private Double totalCpuTime;
    private Long totalPurePathCount;
    private String systemProfile;
    private String systemProfileName;
    private String agentVersion;
    private Integer processorCount;
    private Boolean isvLicenseSupported;
    private String vmVersionString;
    private String vmVendor;
    private String configurationId;
    private String agentMappingId;
    private String agentGroupId;
    private String sourceGroupId;
    private String licenseFlagsToString;
    private String collectorName;
    private String agentInstanceName;
    private String instanceName;
    private Double syncThreshold;
    private Boolean fromCmdb;
    private Boolean required;
    private Boolean hotUpdateCritical;
    private Boolean hotUpdateable;
    private Long timestamp;
    private Long virtualTimeUTC;
    private Boolean captureCPUTimes;
    private Boolean supportsHotSensorPlacement;

    /* getters */
    public String getName() {
        return this.name;
    }

    public Long getStartupTimeUTC() {
        return this.startupTimeUTC;
    }

    public Integer getAgentId() {
        return this.agentId;
    }

    public Integer getProcessId() {
        return this.processId;
    }

    public String getTechnologyType() {
        return this.technologyType;
    }

    public byte getTechnologyTypeId() {
        return this.technologyTypeId;
    }

    public String getHost() {
        return this.host;
    }

    public Long getEventCount() {
        return this.eventCount;
    }

    public Integer getClassLoadCount() {
        return this.classLoadCount;
    }

    public Integer getTotalClassLoadCount() {
        return this.totalClassLoadCount;
    }

    public Boolean isConnected() {
        return this.connected;
    }

    public Boolean isHotUpdateable() {
        return this.hotUpdateable;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Long getVirtualTimeUTC() {
        return this.virtualTimeUTC;
    }

    public Boolean isCaptureCPUTimes() {
        return this.captureCPUTimes;
    }

    public AgentPropertiesInformation getAgentProperties() {
        return this.agentProperties;
    }

    public String getLicenseInformation() {
        return this.licenseInformation;
    }

    public Boolean isCapture() {
        return this.capture;
    }

    public Long getSkippedEvents() {
        return this.skippedEvents;
    }

    public Long getSkippedPurePaths() {
        return this.skippedPurePaths;
    }

    public Double getTotalExecutionTime() {
        return this.totalExecutionTime;
    }

    public Double getTotalCpuTime() {
        return this.totalCpuTime;
    }

    public Long getTotalPurePathCount() {
        return this.totalPurePathCount;
    }

    public String getSystemProfileName() {
        return this.systemProfileName;
    }

    public String getConfigurationName() {
        return this.configurationName;
    }

    public String getAgentGroupLabel() {
        return this.agentGroupLabel;
    }

    public Boolean isLicenseOk() {
        return this.licenseOk;
    }

    public CollectorInformation getCollectorinformation() {
        return this.collectorinformation;
    }

    public String getAgentVersion() {
        return this.agentVersion;
    }

    public Integer getProcessorCount() {
        return this.processorCount;
    }

    public Boolean isvLicenseSupported() {
        return this.isvLicenseSupported;
    }

    public String getVmVersionString() {
        return this.vmVersionString;
    }

    public String getVmVendor() {
        return this.vmVendor;
    }

    public String getConfigurationId() {
        return this.configurationId;
    }

    public String getAgentMappingId() {
        return this.agentMappingId;
    }

    public String getAgentGroupId() {
        return this.agentGroupId;
    }

    public String getSourceGroupId() {
        return this.sourceGroupId;
    }

    public String getLicenseFlagsToString() {
        return this.licenseFlagsToString;
    }

    public String getCollectorName() {
        return this.collectorName;
    }

    public Boolean isAgentConfigured() {
        return this.agentConfigured;
    }

    public String getAgentInstanceName() {
        return this.agentInstanceName;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public Double getSyncThreshold() {
        return this.syncThreshold;
    }

    public Boolean isFromCmdb() {
        return this.fromCmdb;
    }

    public Boolean isRequired() {
        return this.required;
    }

    public Boolean isHotUpdateCritical() {
        return this.hotUpdateCritical;
    }

    public Boolean isHotSensorPlacementSupported() {
        return this.supportsHotSensorPlacement;
    }

    public String getSystemProfile() {
        return this.systemProfile;
    }

    @Override
    public String toString() {
        return "AgentInformation{" +
                "name='" + this.name + '\'' +
                ", startupTimeUTC=" + this.startupTimeUTC +
                ", agentId=" + this.agentId +
                ", processId=" + this.processId +
                ", technologyType='" + this.technologyType + '\'' +
                ", technologyTypeId=" + this.technologyTypeId +
                ", host='" + this.host + '\'' +
                ", eventCount=" + this.eventCount +
                ", classLoadCount=" + this.classLoadCount +
                ", totalClassLoadCount=" + this.totalClassLoadCount +
                ", connected=" + this.connected +
                ", isHotUpdateable=" + this.isHotUpdateable +
                ", licenseInformation='" + this.licenseInformation + '\'' +
                ", capture=" + this.capture +
                ", skippedEvents=" + this.skippedEvents +
                ", skippedPurePaths=" + this.skippedPurePaths +
                ", totalExecutionTime=" + this.totalExecutionTime +
                ", totalCpuTime=" + this.totalCpuTime +
                ", totalPurePathCount=" + this.totalPurePathCount +
                ", systemProfileName='" + this.systemProfileName + '\'' +
                ", configurationName='" + this.configurationName + '\'' +
                ", agentGroupLabel='" + this.agentGroupLabel + '\'' +
                ", licenseOk=" + this.licenseOk +
                ", collectorinformation=" + this.collectorinformation +
                ", agentVersion='" + this.agentVersion + '\'' +
                ", processorCount=" + this.processorCount +
                ", isvLicenseSupported=" + this.isvLicenseSupported +
                ", vmVersionString='" + this.vmVersionString + '\'' +
                ", vmVendor='" + this.vmVendor + '\'' +
                ", configurationId='" + this.configurationId + '\'' +
                ", agentMappingId='" + this.agentMappingId + '\'' +
                ", agentGroupId='" + this.agentGroupId + '\'' +
                ", sourceGroupId='" + this.sourceGroupId + '\'' +
                ", licenseFlagsToString='" + this.licenseFlagsToString + '\'' +
                ", collectorName='" + this.collectorName + '\'' +
                ", agentConfigured=" + this.agentConfigured +
                ", agentInstanceName='" + this.agentInstanceName + '\'' +
                ", instanceName='" + this.instanceName + '\'' +
                ", syncThreshold=" + this.syncThreshold +
                ", fromCmdb=" + this.fromCmdb +
                ", required=" + this.required +
                ", hotUpdateCritical=" + this.hotUpdateCritical +
                ", hotUpdateable=" + this.hotUpdateable +
                ", timestamp=" + this.timestamp +
                ", virtualTimeUTC=" + this.virtualTimeUTC +
                ", captureCPUTimes=" + this.captureCPUTimes +
                ", agentProperties=" + this.agentProperties +
                '}';
    }
}
