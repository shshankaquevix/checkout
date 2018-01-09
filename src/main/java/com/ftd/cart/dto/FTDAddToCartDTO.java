package com.ftd.cart.dto;

/**
 * Immutable class provides AddToCartRequest fields formatted for POST request on FTD site
 * All methods of this class must return string value, (All int, long, float, double and other types
 * should be converted to String before returning)
 * @author vsai12
 *
 */
public final class FTDAddToCartDTO {

	private String productType;
	
	private String productId;

	private String subCode;

	private String markCode;

	private String productPrice;

	private String westProduct;

	private String master;

	private String aid;

	private String action;

	private String useNewPurchasePath;

	private String selectedProduct;

	private String pasOnlyShippingMethod;

	private String zip;

	private String ppDelLoc;

	private String vases;

	private String shippingMethodDeldateProduct;

	private String productCountry;

	private String countryId;

	private String sameday;

	private String productSubType;

	private String websiteID;

	private String gbb;

	private String masterProdSkuCount;

	private String productPriceWithdiscount;

	private String item1;

	private String catalog;

	private String vh;

	private String subcodeList;

	private String subcodePrices;

	private String deliveryOption;

	private String entryFrom;

	private String productName;
	
	private String productURL;
	
	private String deliveryDate;
	
	private String displayDelivDateProduct;
		
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the subCode
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * @param subCode the subCode to set
	 */
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	/**
	 * @return the markCode
	 */
	public String getMarkCode() {
		return markCode;
	}

	/**
	 * @param markCode the markCode to set
	 */
	public void setMarkCode(String markCode) {
		this.markCode = markCode;
	}

	/**
	 * @return the productPrice
	 */
	public String getProductPrice() {
		return productPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return the westProduct
	 */
	public String getWestProduct() {
		return westProduct;
	}

	/**
	 * @param westProduct the westProduct to set
	 */
	public void setWestProduct(String westProduct) {
		this.westProduct = westProduct;
	}

	/**
	 * @return the master
	 */
	public String getMaster() {
		return master;
	}

	/**
	 * @param master the master to set
	 */
	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * @param aid the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the useNewPurchasePath
	 */
	public String getUseNewPurchasePath() {
		return useNewPurchasePath;
	}

	/**
	 * @param useNewPurchasePath the useNewPurchasePath to set
	 */
	public void setUseNewPurchasePath(String useNewPurchasePath) {
		this.useNewPurchasePath = useNewPurchasePath;
	}

	/**
	 * @return the selectedProduct
	 */
	public String getSelectedProduct() {
		return selectedProduct;
	}

	/**
	 * @param selectedProduct the selectedProduct to set
	 */
	public void setSelectedProduct(String selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	/**
	 * @return the pasOnlyShippingMethod
	 */
	public String getPasOnlyShippingMethod() {
		return pasOnlyShippingMethod;
	}

	/**
	 * @param pasOnlyShippingMethod the pasOnlyShippingMethod to set
	 */
	public void setPasOnlyShippingMethod(String pasOnlyShippingMethod) {
		this.pasOnlyShippingMethod = pasOnlyShippingMethod;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the deliveryDate
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the ppDelLoc
	 */
	public String getPpDelLoc() {
		return ppDelLoc;
	}

	/**
	 * @return the displayDelivDateProduct
	 */
	public String getDisplayDelivDateProduct() {
		return displayDelivDateProduct;
	}

	/**
	 * @param displayDelivDateProduct the displayDelivDateProduct to set
	 */
	public void setDisplayDelivDateProduct(String displayDelivDateProduct) {
		this.displayDelivDateProduct = displayDelivDateProduct;
	}

	/**
	 * @param ppDelLoc the ppDelLoc to set
	 */
	public void setPpDelLoc(String ppDelLoc) {
		this.ppDelLoc = ppDelLoc;
	}

	/**
	 * @return the vases
	 */
	public String getVases() {
		return vases;
	}

	/**
	 * @param vases the vases to set
	 */
	public void setVases(String vases) {
		this.vases = vases;
	}

	/**
	 * @return the shippingMethodDeldateProduct
	 */
	public String getShippingMethodDeldateProduct() {
		return shippingMethodDeldateProduct;
	}

	/**
	 * @param shippingMethodDeldateProduct the shippingMethodDeldateProduct to set
	 */
	public void setShippingMethodDeldateProduct(String shippingMethodDeldateProduct) {
		this.shippingMethodDeldateProduct = shippingMethodDeldateProduct;
	}

	/**
	 * @return the productCountry
	 */
	public String getProductCountry() {
		return productCountry;
	}

	/**
	 * @param productCountry the productCountry to set
	 */
	public void setProductCountry(String productCountry) {
		this.productCountry = productCountry;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the sameday
	 */
	public String getSameday() {
		return sameday;
	}

	/**
	 * @param sameday the sameday to set
	 */
	public void setSameday(String sameday) {
		this.sameday = sameday;
	}

	/**
	 * @return the productSubType
	 */
	public String getProductSubType() {
		return productSubType;
	}

	/**
	 * @param productSubType the productSubType to set
	 */
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	/**
	 * @return the websiteID
	 */
	public String getWebsiteID() {
		return websiteID;
	}

	/**
	 * @param websiteID the websiteID to set
	 */
	public void setWebsiteID(String websiteID) {
		this.websiteID = websiteID;
	}

	/**
	 * @return the gbb
	 */
	public String getGbb() {
		return gbb;
	}

	/**
	 * @param gbb the gbb to set
	 */
	public void setGbb(String gbb) {
		this.gbb = gbb;
	}

	/**
	 * @return the masterProdSkuCount
	 */
	public String getMasterProdSkuCount() {
		return masterProdSkuCount;
	}

	/**
	 * @param masterProdSkuCount the masterProdSkuCount to set
	 */
	public void setMasterProdSkuCount(String masterProdSkuCount) {
		this.masterProdSkuCount = masterProdSkuCount;
	}

	/**
	 * @return the productPriceWithdiscount
	 */
	public String getProductPriceWithdiscount() {
		return productPriceWithdiscount;
	}

	/**
	 * @param productPriceWithdiscount the productPriceWithdiscount to set
	 */
	public void setProductPriceWithdiscount(String productPriceWithdiscount) {
		this.productPriceWithdiscount = productPriceWithdiscount;
	}

	/**
	 * @return the item1
	 */
	public String getItem1() {
		return item1;
	}

	/**
	 * @param item1 the item1 to set
	 */
	public void setItem1(String item1) {
		this.item1 = item1;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * @return the vh
	 */
	public String getVh() {
		return vh;
	}

	/**
	 * @param vh the vh to set
	 */
	public void setVh(String vh) {
		this.vh = vh;
	}

	/**
	 * @return the subcodeList
	 */
	public String getSubcodeList() {
		return subcodeList;
	}

	/**
	 * @param subcodeList the subcodeList to set
	 */
	public void setSubcodeList(String subcodeList) {
		this.subcodeList = subcodeList;
	}

	/**
	 * @return the subcodePrices
	 */
	public String getSubcodePrices() {
		return subcodePrices;
	}

	/**
	 * @param subcodePrices the subcodePrices to set
	 */
	public void setSubcodePrices(String subcodePrices) {
		this.subcodePrices = subcodePrices;
	}

	/**
	 * @return the deliveryOption
	 */
	public String getDeliveryOption() {
		return deliveryOption;
	}

	/**
	 * @param deliveryOption the deliveryOption to set
	 */
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	/**
	 * @return the entryFrom
	 */
	public String getEntryFrom() {
		return entryFrom;
	}

	/**
	 * @param entryFrom the entryFrom to set
	 */
	public void setEntryFrom(String entryFrom) {
		this.entryFrom = entryFrom;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productURL
	 */
	public String getProductURL() {
		return productURL;
	}

	/**
	 * @param productURL the productURL to set
	 */
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FTDAddToCartDTO [productType=" + productType + ", productId=" + productId + ", subCode=" + subCode
				+ ", markCode=" + markCode + ", productPrice=" + productPrice + ", westProduct=" + westProduct
				+ ", master=" + master + ", aid=" + aid + ", action=" + action + ", useNewPurchasePath="
				+ useNewPurchasePath + ", selectedProduct=" + selectedProduct + ", pasOnlyShippingMethod="
				+ pasOnlyShippingMethod + ", zip=" + zip + ", ppDelLoc=" + ppDelLoc + ", vases=" + vases
				+ ", shippingMethodDeldateProduct=" + shippingMethodDeldateProduct + ", productCountry="
				+ productCountry + ", countryId=" + countryId + ", sameday=" + sameday + ", productSubType="
				+ productSubType + ", websiteID=" + websiteID + ", gbb=" + gbb + ", masterProdSkuCount="
				+ masterProdSkuCount + ", productPriceWithdiscount=" + productPriceWithdiscount + ", item1=" + item1
				+ ", catalog=" + catalog + ", vh=" + vh + ", subcodeList=" + subcodeList + ", subcodePrices="
				+ subcodePrices + ", deliveryOption=" + deliveryOption + ", entryFrom=" + entryFrom + ", productName="
				+ productName + ", productURL=" + productURL + ", deliveryDate=" + deliveryDate
				+ ", displayDelivDateProduct=" + displayDelivDateProduct + "]";
	}

	
}
