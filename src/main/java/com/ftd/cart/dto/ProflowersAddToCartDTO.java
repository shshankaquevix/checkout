package com.ftd.cart.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ftd.cart.vo.AddToCartRequest;

@JsonPropertyOrder({
    "PBResult",
    "SavePidTreeInfoInTransactionTable",
    "SkuBusinessUnitId",
    "CartUniqueLineNumberForEdit",
    "PBContext"
})
public class ProflowersAddToCartDTO {
	
	@JsonIgnore
	private AddToCartRequest addToCartRequest;
	
	@JsonProperty("PBResult")
    @Valid
    private PBResult pBResult;
	
    @JsonProperty("SavePidTreeInfoInTransactionTable")
    private Boolean savePidTreeInfoInTransactionTable;
    
    @JsonProperty("SkuBusinessUnitId")
    private Integer skuBusinessUnitId;
    
    @JsonProperty("CartUniqueLineNumberForEdit")
    private Integer cartUniqueLineNumberForEdit;
    
    @JsonProperty("PBContext")
    @Valid
    private PBContext pBContext;
    
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ProflowersAddToCartDTO(AddToCartRequest addToCartRequest) {
		this.addToCartRequest = addToCartRequest;
	}
    
    public ProflowersAddToCartDTO(){
    }

	@JsonProperty("PBResult")
    public PBResult getPBResult() {
        return pBResult;
    }

    @JsonProperty("PBResult")
    public void setPBResult(PBResult pBResult) {
        this.pBResult = pBResult;
    }

    public ProflowersAddToCartDTO withPBResult(PBResult pBResult) {
        this.pBResult = pBResult;
        return this;
    }

    @JsonProperty("SavePidTreeInfoInTransactionTable")
    public Boolean getSavePidTreeInfoInTransactionTable() {
        return savePidTreeInfoInTransactionTable;
    }

    @JsonProperty("SavePidTreeInfoInTransactionTable")
    public void setSavePidTreeInfoInTransactionTable(Boolean savePidTreeInfoInTransactionTable) {
        this.savePidTreeInfoInTransactionTable = savePidTreeInfoInTransactionTable;
    }

    @JsonProperty("SkuBusinessUnitId")
    public Integer getSkuBusinessUnitId() {
        return skuBusinessUnitId;
    }

    @JsonProperty("SkuBusinessUnitId")
    public void setSkuBusinessUnitId(Integer skuBusinessUnitId) {
        this.skuBusinessUnitId = skuBusinessUnitId;
    }

    @JsonProperty("CartUniqueLineNumberForEdit")
    public Integer getCartUniqueLineNumberForEdit() {
        return cartUniqueLineNumberForEdit;
    }

    @JsonProperty("CartUniqueLineNumberForEdit")
    public void setCartUniqueLineNumberForEdit(Integer cartUniqueLineNumberForEdit) {
        this.cartUniqueLineNumberForEdit = cartUniqueLineNumberForEdit;
    }

    @JsonProperty("PBContext")
    public PBContext getPBContext() {
        return pBContext;
    }

    @JsonProperty("PBContext")
    public void setPBContext(PBContext pBContext) {
        this.pBContext = pBContext;
    }

    public ProflowersAddToCartDTO withPBContext(PBContext pBContext) {
        this.pBContext = pBContext;
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

    /**
	 * @return the addToCartRequest
	 */
	public AddToCartRequest getAddToCartRequest() {
		return addToCartRequest;
	}

	public ProflowersAddToCartDTO withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
	
}
