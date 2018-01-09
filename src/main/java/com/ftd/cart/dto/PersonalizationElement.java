package com.ftd.cart.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "PersonalizationType",
    "PersonalizationName",
    "PersonalizationValue",
    "FulfillmentSortOrder"
})
public class PersonalizationElement {

    @JsonProperty("PersonalizationType")
    private String personalizationType;
    
    @JsonProperty("PersonalizationName")
    private String personalizationName;
    
    @JsonProperty("PersonalizationValue")
    private String personalizationValue;
    
    @JsonProperty("FulfillmentSortOrder")
    private String fulfillmentSortOrder;
    
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("PersonalizationType")
    public String getPersonalizationType() {
        return personalizationType;
    }

    @JsonProperty("PersonalizationType")
    public void setPersonalizationType(String personalizationType) {
        this.personalizationType = personalizationType;
    }

    public PersonalizationElement withPersonalizationType(String personalizationType) {
        this.personalizationType = personalizationType;
        return this;
    }

    @JsonProperty("PersonalizationName")
    public String getPersonalizationName() {
        return personalizationName;
    }

    @JsonProperty("PersonalizationName")
    public void setPersonalizationName(String personalizationName) {
        this.personalizationName = personalizationName;
    }

    public PersonalizationElement withPersonalizationName(String personalizationName) {
        this.personalizationName = personalizationName;
        return this;
    }

    @JsonProperty("PersonalizationValue")
    public String getPersonalizationValue() {
        return personalizationValue;
    }

    @JsonProperty("PersonalizationValue")
    public void setPersonalizationValue(String personalizationValue) {
        this.personalizationValue = personalizationValue;
    }

    public PersonalizationElement withPersonalizationValue(String personalizationValue) {
        this.personalizationValue = personalizationValue;
        return this;
    }

    @JsonProperty("FulfillmentSortOrder")
    public String getFulfillmentSortOrder() {
        return fulfillmentSortOrder;
    }

    @JsonProperty("FulfillmentSortOrder")
    public void setFulfillmentSortOrder(String fulfillmentSortOrder) {
        this.fulfillmentSortOrder = fulfillmentSortOrder;
    }

    public PersonalizationElement withFulfillmentSortOrder(String fulfillmentSortOrder) {
        this.fulfillmentSortOrder = fulfillmentSortOrder;
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

    public PersonalizationElement withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
