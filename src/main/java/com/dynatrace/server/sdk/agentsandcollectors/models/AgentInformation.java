package com.dynatrace.server.sdk.agentsandcollectors.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = AgentInformation.ROOT_ELEMENT_NAME)
public class AgentInformation {
    public static final String ROOT_ELEMENT_NAME = "agentinformation";

    private String name;
    private long startupDateUTC;
    private int agentId;
    private int processId;
    private String technologyType;
    private byte techTypeConstant;
    private String host;
    private long eventCount;
    private int classLoadCount;
    private int totalClassLoadCount;
    private boolean isConnected;
    private boolean isHotUpdateable;
    private String licenseInformation;
    private boolean isCapture;
    private long counterSkipEvents;
    private long counterSkipExecPaths;
    private double totalExecutionTime;
    private double totalCpuTime;
    private long totalPurePathCount;
    private String systemProfileName;
    private String profileConfigurationName;
    private String agentGroupLabel;
    private boolean isLicenseOk;
    private CollectorInformation collectorInfo;
    private List<DeployedSensorInformation> deployedSensorsInformation;
    private String agentVersion;
    private Integer processorCount;
    private boolean isvLicenseSupported;
    private String vmVersionString;
    private String vmVendor;
    private String configurationId;
    private String agentMappingId;
    private String agentGroupId;
    private String sourceGroupId;
    private String licenseFlagsToString;
    private String collectorName;
    private boolean isAgentConfigured;
    private String agentInstanceName;
    private String instanceName;
    private double syncThreshold;
    private boolean fromCmdb;
    private boolean required;
    private boolean hotUpdateCritical;
    private boolean hotUpdateable;
    private long timestamp;
    private long virtualTime;
    private boolean captureCPUTimes;
    private AgentPropertiesInformation agentProperties;

    /* getters */
    public String getName() {
        return this.name;
    }

    public long getStartupDateUTC() {
        return this.startupDateUTC;
    }

    public int getAgentId() {
        return this.agentId;
    }

    public int getProcessId() {
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

    public long getEventCount() {
        return this.eventCount;
    }

    public int getClassLoadCount() {
        return this.classLoadCount;
    }

    public int getTotalClassLoadCount() {
        return this.totalClassLoadCount;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public boolean isHotUpdateable() {
        return this.isHotUpdateable;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getVirtualTime() {
        return this.virtualTime;
    }

    public boolean isCaptureCPUTimes() {
        return this.captureCPUTimes;
    }

    public AgentPropertiesInformation getAgentProperties() {
        return this.agentProperties;
    }

    public String getLicenseInformation() {
        return this.licenseInformation;
    }

    public boolean isCapture() {
        return this.isCapture;
    }

    public long getCounterSkipEvents() {
        return this.counterSkipEvents;
    }

    public long getCounterSkipExecPaths() {
        return this.counterSkipExecPaths;
    }

    public double getTotalExecutionTime() {
        return this.totalExecutionTime;
    }

    public double getTotalCpuTime() {
        return this.totalCpuTime;
    }

    public long getTotalPurePathCount() {
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

    public boolean isLicenseOk() {
        return this.isLicenseOk;
    }

    public CollectorInformation getCollectorInfo() {
        return this.collectorInfo;
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

    public boolean isvLicenseSupported() {
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

    public boolean isAgentConfigured() {
        return this.isAgentConfigured;
    }

    public String getAgentInstanceName() {
        return this.agentInstanceName;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public double getSyncThreshold() {
        return this.syncThreshold;
    }

    public boolean isFromCmdb() {
        return this.fromCmdb;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isHotUpdateCritical() {
        return this.hotUpdateCritical;
    }

    @Override
    public String toString() {
        return "AgentInformation{" +
                "name='" + name + '\'' +
                ", startupDateUTC=" + startupDateUTC +
                ", agentId=" + agentId +
                ", processId=" + processId +
                ", technologyType='" + technologyType + '\'' +
                ", techTypeConstant=" + techTypeConstant +
                ", host='" + host + '\'' +
                ", eventCount=" + eventCount +
                ", classLoadCount=" + classLoadCount +
                ", totalClassLoadCount=" + totalClassLoadCount +
                ", isConnected=" + isConnected +
                ", isHotUpdateable=" + isHotUpdateable +
                ", licenseInformation='" + licenseInformation + '\'' +
                ", isCapture=" + isCapture +
                ", counterSkipEvents=" + counterSkipEvents +
                ", counterSkipExecPaths=" + counterSkipExecPaths +
                ", totalExecutionTime=" + totalExecutionTime +
                ", totalCpuTime=" + totalCpuTime +
                ", totalPurePathCount=" + totalPurePathCount +
                ", systemProfileName='" + systemProfileName + '\'' +
                ", profileConfigurationName='" + profileConfigurationName + '\'' +
                ", agentGroupLabel='" + agentGroupLabel + '\'' +
                ", isLicenseOk=" + isLicenseOk +
                ", collectorInfo=" + collectorInfo +
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
                ", isAgentConfigured=" + isAgentConfigured +
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
