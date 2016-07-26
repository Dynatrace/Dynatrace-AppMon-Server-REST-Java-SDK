package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = AgentInformation.ROOT_ELEMENT_NAME)
public class AgentInformation {
    public static final String ROOT_ELEMENT_NAME = "agentinformation";

    private CollectorInformation collectorinformation;
    private AgentPropertiesInformation agentProperties;
    private List<DeployedSensorInformation> deployedSensorsInformation;

    private Boolean connected;
    private Boolean capture;
    private Boolean licenseOk;
    private Boolean agentConfigured;
    private Long startupTimeUTC;

    private String name;
    private Integer agentId;
    private Integer processId;
    private String technologyType;
    private Byte techTypeConstant;
    private String host;
    private Long eventCount;
    private Integer classLoadCount;
    private Integer totalClassLoadCount;
    private Boolean isHotUpdateable;
    private String licenseInformation;
    private Long counterSkipEvents;
    private Long counterSkipExecPaths;
    private Double totalExecutionTime;
    private Double totalCpuTime;
    private Long totalPurePathCount;
    private String systemProfileName;
    private String profileConfigurationName;
    private String agentGroupLabel;
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
    private Long virtualTime;
    private Boolean captureCPUTimes;

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

    public byte getTechTypeConstant() {
        return this.techTypeConstant;
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
        return this.isHotUpdateable;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Long getVirtualTime() {
        return this.virtualTime;
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

    public Long getCounterSkipEvents() {
        return this.counterSkipEvents;
    }

    public Long getCounterSkipExecPaths() {
        return this.counterSkipExecPaths;
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

    public String getProfileConfigurationName() {
        return this.profileConfigurationName;
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

    public List<DeployedSensorInformation> getDeployedSensorsInformation() {
        return this.deployedSensorsInformation;
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

    @Override
    public String toString() {
        return "AgentInformation{" +
                "name='" + name + '\'' +
                ", startupTimeUTC=" + startupTimeUTC +
                ", agentId=" + agentId +
                ", processId=" + processId +
                ", technologyType='" + technologyType + '\'' +
                ", techTypeConstant=" + techTypeConstant +
                ", host='" + host + '\'' +
                ", eventCount=" + eventCount +
                ", classLoadCount=" + classLoadCount +
                ", totalClassLoadCount=" + totalClassLoadCount +
                ", connected=" + connected +
                ", isHotUpdateable=" + isHotUpdateable +
                ", licenseInformation='" + licenseInformation + '\'' +
                ", capture=" + capture +
                ", counterSkipEvents=" + counterSkipEvents +
                ", counterSkipExecPaths=" + counterSkipExecPaths +
                ", totalExecutionTime=" + totalExecutionTime +
                ", totalCpuTime=" + totalCpuTime +
                ", totalPurePathCount=" + totalPurePathCount +
                ", systemProfileName='" + systemProfileName + '\'' +
                ", profileConfigurationName='" + profileConfigurationName + '\'' +
                ", agentGroupLabel='" + agentGroupLabel + '\'' +
                ", licenseOk=" + licenseOk +
                ", collectorinformation=" + collectorinformation +
                ", deployedSensorsInformation=" + deployedSensorsInformation +
                ", agentVersion='" + agentVersion + '\'' +
                ", processorCount=" + processorCount +
                ", isvLicenseSupported=" + isvLicenseSupported +
                ", vmVersionString='" + vmVersionString + '\'' +
                ", vmVendor='" + vmVendor + '\'' +
                ", configurationId='" + configurationId + '\'' +
                ", agentMappingId='" + agentMappingId + '\'' +
                ", agentGroupId='" + agentGroupId + '\'' +
                ", sourceGroupId='" + sourceGroupId + '\'' +
                ", licenseFlagsToString='" + licenseFlagsToString + '\'' +
                ", collectorName='" + collectorName + '\'' +
                ", agentConfigured=" + agentConfigured +
                ", agentInstanceName='" + agentInstanceName + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", syncThreshold=" + syncThreshold +
                ", fromCmdb=" + fromCmdb +
                ", required=" + required +
                ", hotUpdateCritical=" + hotUpdateCritical +
                ", hotUpdateable=" + hotUpdateable +
                ", timestamp=" + timestamp +
                ", virtualTime=" + virtualTime +
                ", captureCPUTimes=" + captureCPUTimes +
                ", agentProperties=" + agentProperties +
                '}';
    }
}
