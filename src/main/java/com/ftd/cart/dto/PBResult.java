package com.ftd.cart.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ftd.cart.custom.jsonserializer.DateFormatSerializer;

@JsonPropertyOrder({
    "TotalQuantity",
    "PidTree",
    "DeliveryDate",
    "FlexDelivery",
    "ZipCode",
    "IsDeliveryBy",
    "DeliveryByStartDate",
    "DeliveryByEndDate"
})
public class PBResult {

    @JsonProperty("TotalQuantity")
    private Integer totalQuantity;
    
    @JsonProperty("PidTree")
    @Valid
    private PidTree pidTree;
    
    @JsonProperty("DeliveryDate")
    private String deliveryDate;
    
    @JsonProperty("FlexDelivery")
    private Boolean flexDelivery;
    
    @JsonProperty("ZipCode")
    private String zipCode;
    
    @JsonProperty("IsDeliveryBy")
    private Boolean isDeliveryBy;
    
    @JsonProperty("DeliveryByStartDate")
    private String deliveryByStartDate;
    
    @JsonProperty("DeliveryByEndDate")
    private String deliveryByEndDate;
    
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("TotalQuantity")
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    @JsonProperty("TotalQuantity")
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public PBResult withTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    @JsonProperty("PidTree")
    public PidTree getPidTree() {
        return pidTree;
    }

    @JsonProperty("PidTree")
    public void setPidTree(PidTree pidTree) {
        this.pidTree = pidTree;
    }

    public PBResult withPidTree(PidTree pidTree) {
        this.pidTree = pidTree;
        return this;
    }

    @JsonProperty("DeliveryDate")
    @JsonSerialize(using = DateFormatSerializer.class)
    public String getDeliveryDate() {
        return deliveryDate;
    }

    @JsonProperty("DeliveryDate")
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PBResult withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    @JsonProperty("FlexDelivery")
    public Boolean getFlexDelivery() {
        return flexDelivery;
    }

    @JsonProperty("FlexDelivery")
    public void setFlexDelivery(Boolean flexDelivery) {
        this.flexDelivery = flexDelivery;
    }

    public PBResult withFlexDelivery(Boolean flexDelivery) {
        this.flexDelivery = flexDelivery;
        return this;
    }

    @JsonProperty("ZipCode")
    public String getZipCode() {
        return zipCode;
    }

    @JsonProperty("ZipCode")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public PBResult withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @JsonProperty("IsDeliveryBy")
    public Boolean getIsDeliveryBy() {
        return isDeliveryBy;
    }

    @JsonProperty("IsDeliveryBy")
    public void setIsDeliveryBy(Boolean isDeliveryBy) {
        this.isDeliveryBy = isDeliveryBy;
    }

    public PBResult withIsDeliveryBy(Boolean isDeliveryBy) {
        this.isDeliveryBy = isDeliveryBy;
        return this;
    }

    @JsonProperty("DeliveryByStartDate")
    public Object getDeliveryByStartDate() {
        return deliveryByStartDate;
    }

    @JsonProperty("DeliveryByStartDate")
    public void setDeliveryByStartDate(String deliveryByStartDate) {
        this.deliveryByStartDate = deliveryByStartDate;
    }

    public PBResult withDeliveryByStartDate(String deliveryByStartDate) {
        this.deliveryByStartDate = deliveryByStartDate;
        return this;
    }

    @JsonProperty("DeliveryByEndDate")
    public Object getDeliveryByEndDate() {
        return deliveryByEndDate;
    }

    @JsonProperty("DeliveryByEndDate")
    public void setDeliveryByEndDate(String deliveryByEndDate) {
        this.deliveryByEndDate = deliveryByEndDate;
    }

    public PBResult withDeliveryByEndDate(String deliveryByEndDate) {
        this.deliveryByEndDate = deliveryByEndDate;
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

    public PBResult withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
