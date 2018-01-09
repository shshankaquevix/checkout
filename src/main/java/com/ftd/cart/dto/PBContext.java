package com.ftd.cart.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
    "ExternalPageUrl",
    "OriginalRootPid",
    "RefCode",
    "OriginatingCobrand",
    "PartnerCode",
    "Ssid",
    "IsPortablePDP",
    "ApplicationUsingPortablePDP",
    "PortablePDPDiscountType",
    "PortablePDPDiscountValue",
    "CurrentRootPid"
})
public class PBContext {

    @JsonProperty("ExternalPageUrl")
    private String externalPageUrl;
    
    @JsonProperty("OriginalRootPid")
    private Integer originalRootPid;
    
    @JsonProperty("RefCode")
    private String refCode;
    
    @JsonProperty("OriginatingCobrand")
    private String originatingCobrand;
    
    @JsonProperty("PartnerCode")
    private String partnerCode;
    
    @JsonProperty("Ssid")
    private String ssid;
    
    @JsonProperty("IsPortablePDP")
    private Boolean isPortablePDP;
    
    @JsonProperty("ApplicationUsingPortablePDP")
    private String applicationUsingPortablePDP;
    
    @JsonProperty("PortablePDPDiscountType")
    private Integer portablePDPDiscountType;
    
    @JsonProperty("PortablePDPDiscountValue")
    private Integer portablePDPDiscountValue;
    
    @JsonProperty("CurrentRootPid")
    @NotNull
    private Integer currentRootPid;
    
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ExternalPageUrl")
    public String getExternalPageUrl() {
        return externalPageUrl;
    }

    @JsonProperty("ExternalPageUrl")
    public void setExternalPageUrl(String externalPageUrl) {
        this.externalPageUrl = externalPageUrl;
    }

    public PBContext withExternalPageUrl(String externalPageUrl) {
        this.externalPageUrl = externalPageUrl;
        return this;
    }

    @JsonProperty("OriginalRootPid")
    public Integer getOriginalRootPid() {
        return originalRootPid;
    }

    @JsonProperty("OriginalRootPid")
    public void setOriginalRootPid(Integer originalRootPid) {
        this.originalRootPid = originalRootPid;
    }

    public PBContext withOriginalRootPid(Integer originalRootPid) {
        this.originalRootPid = originalRootPid;
        return this;
    }

    @JsonProperty("RefCode")
    public Object getRefCode() {
        return refCode;
    }

    @JsonProperty("RefCode")
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public PBContext withRefCode(String refCode) {
        this.refCode = refCode;
        return this;
    }

    @JsonProperty("OriginatingCobrand")
    public Object getOriginatingCobrand() {
        return originatingCobrand;
    }

    @JsonProperty("OriginatingCobrand")
    public void setOriginatingCobrand(String originatingCobrand) {
        this.originatingCobrand = originatingCobrand;
    }

    public PBContext withOriginatingCobrand(String originatingCobrand) {
        this.originatingCobrand = originatingCobrand;
        return this;
    }

    @JsonProperty("PartnerCode")
    public String getPartnerCode() {
        return partnerCode;
    }

    @JsonProperty("PartnerCode")
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public PBContext withPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    @JsonProperty("Ssid")
    public Object getSsid() {
        return ssid;
    }

    @JsonProperty("Ssid")
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public PBContext withSsid(String ssid) {
        this.ssid = ssid;
        return this;
    }

    @JsonProperty("IsPortablePDP")
    public Boolean getIsPortablePDP() {
        return isPortablePDP;
    }

    @JsonProperty("IsPortablePDP")
    public void setIsPortablePDP(Boolean isPortablePDP) {
        this.isPortablePDP = isPortablePDP;
    }

    public PBContext withIsPortablePDP(Boolean isPortablePDP) {
        this.isPortablePDP = isPortablePDP;
        return this;
    }

    @JsonProperty("ApplicationUsingPortablePDP")
    public Object getApplicationUsingPortablePDP() {
        return applicationUsingPortablePDP;
    }

    @JsonProperty("ApplicationUsingPortablePDP")
    public void setApplicationUsingPortablePDP(String applicationUsingPortablePDP) {
        this.applicationUsingPortablePDP = applicationUsingPortablePDP;
    }

    public PBContext withApplicationUsingPortablePDP(String applicationUsingPortablePDP) {
        this.applicationUsingPortablePDP = applicationUsingPortablePDP;
        return this;
    }

    @JsonProperty("PortablePDPDiscountType")
    public Integer getPortablePDPDiscountType() {
        return portablePDPDiscountType;
    }

    @JsonProperty("PortablePDPDiscountType")
    public void setPortablePDPDiscountType(Integer portablePDPDiscountType) {
        this.portablePDPDiscountType = portablePDPDiscountType;
    }

    public PBContext withPortablePDPDiscountType(Integer portablePDPDiscountType) {
        this.portablePDPDiscountType = portablePDPDiscountType;
        return this;
    }

    @JsonProperty("PortablePDPDiscountValue")
    public Integer getPortablePDPDiscountValue() {
        return portablePDPDiscountValue;
    }

    @JsonProperty("PortablePDPDiscountValue")
    public void setPortablePDPDiscountValue(Integer portablePDPDiscountValue) {
        this.portablePDPDiscountValue = portablePDPDiscountValue;
    }

    public PBContext withPortablePDPDiscountValue(Integer portablePDPDiscountValue) {
        this.portablePDPDiscountValue = portablePDPDiscountValue;
        return this;
    }

    @JsonProperty("CurrentRootPid")
    public Integer getCurrentRootPid() {
        return currentRootPid;
    }

    @JsonProperty("CurrentRootPid")
    public void setCurrentRootPid(Integer currentRootPid) {
        this.currentRootPid = currentRootPid;
    }

    public PBContext withCurrentRootPid(Integer currentRootPid) {
        this.currentRootPid = currentRootPid;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PBContext withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
