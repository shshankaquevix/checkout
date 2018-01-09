package com.ftd.cart.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "Pid",
    "Children",
    "ProductSetSelections",
    "PersonalizationElements",
    "OriginalSalePrice"
})
public class PidTree {

    @JsonProperty("Pid")
    @NotNull
    private Integer pid;
    
    @JsonProperty("Children")
    @Valid
    private List<PidTree> children = new ArrayList<PidTree>();
    
    @JsonProperty("ProductSetSelections")
    @Valid
    private ProductSetSelections productSetSelections = new ProductSetSelections();
    
    @JsonProperty("PersonalizationElements")
    @Valid
    private List<PersonalizationElement> personalizationElements = new ArrayList<PersonalizationElement>();
    
    @JsonProperty("OriginalSalePrice")
    @NotNull
    private Double originalSalePrice;
    
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Pid")
    public Integer getPid() {
        return pid;
    }

    @JsonProperty("Pid")
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public PidTree withPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    @JsonProperty("Children")
    public List<PidTree> getChildren() {
        return children;
    }

    @JsonProperty("Children")
    public void setChildren(List<PidTree> children) {
        this.children = children;
    }

    public PidTree withChildren(List<PidTree> children) {
        this.children = children;
        return this;
    }

    @JsonProperty("ProductSetSelections")
    public ProductSetSelections getProductSetSelections() {
        return productSetSelections;
    }

    @JsonProperty("ProductSetSelections")
    public void setProductSetSelections(ProductSetSelections productSetSelections) {
        this.productSetSelections = productSetSelections;
    }

    public PidTree withProductSetSelections(ProductSetSelections productSetSelections) {
        this.productSetSelections = productSetSelections;
        return this;
    }

    @JsonProperty("PersonalizationElements")
    public List<PersonalizationElement> getPersonalizationElements() {
        return personalizationElements;
    }

    @JsonProperty("PersonalizationElements")
    public void setPersonalizationElements(List<PersonalizationElement> personalizationElements) {
        this.personalizationElements = personalizationElements;
    }

    public PidTree withPersonalizationElements(List<PersonalizationElement> personalizationElements) {
        this.personalizationElements = personalizationElements;
        return this;
    }

    @JsonProperty("OriginalSalePrice")
    public Double getOriginalSalePrice() {
        return originalSalePrice;
    }

    @JsonProperty("OriginalSalePrice")
    public void setOriginalSalePrice(Double originalSalePrice) {
        this.originalSalePrice = originalSalePrice;
    }

    public PidTree withOriginalSalePrice(Double originalSalePrice) {
        this.originalSalePrice = originalSalePrice;
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

    public PidTree withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
