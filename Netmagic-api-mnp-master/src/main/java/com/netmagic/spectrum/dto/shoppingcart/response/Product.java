package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the product information from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 3643227343417830525L;

    @JsonProperty("pid")
    private String productId;
    @JsonProperty("pname")
    private String productName;
    @JsonProperty("isOnlineProduct")
    private String isOnlineProduct;
    @JsonProperty("ptype")
    private String productType;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("pslctg")
    private String pslctg;
    @JsonProperty("isCustomizedProduct")
    private String isCustomizedProduct;
    @JsonProperty("salesEndDate")
    private String salesEndDate;
    @JsonProperty("locationDetails")
    private List<Location> locationDetails;
    @JsonProperty("subCategory")
    private List<SubCategory> subCategory;
    @JsonProperty("subSubCategory")
    private List<SubSubCategory> subSubCategory;
    @JsonProperty("mrc")
    private Double monthlyRecurringCharge;
    @JsonProperty("otc")
    private Double oneTimeCharge;
    @JsonProperty("var")
    private Double variableCharge;
    @JsonProperty("psorg")
    private String psorg;
    @JsonProperty("type")
    private String type;
    @JsonProperty("qty")
    private Long quantity;
    @JsonProperty("pdesc")
    private String productDescription;
    @JsonProperty("VariableProductUOM")
    private String variableProductUOM;
    @JsonProperty("isVariableProduct")
    private String isVariableProduct;
    @JsonProperty("isCappedProduct")
    private String isCappedProduct;
    @JsonProperty("sprod")
    private Map<String, Object> sprod = new HashMap<String, Object>();

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIsOnlineProduct() {
        return isOnlineProduct;
    }

    public void setIsOnlineProduct(String isOnlineProduct) {
        this.isOnlineProduct = isOnlineProduct;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPslctg() {
        return pslctg;
    }

    public void setPslctg(String pslctg) {
        this.pslctg = pslctg;
    }

    public String getIsCustomizedProduct() {
        return isCustomizedProduct;
    }

    public void setIsCustomizedProduct(String isCustomizedProduct) {
        this.isCustomizedProduct = isCustomizedProduct;
    }

    public String getSalesEndDate() {
        return salesEndDate;
    }

    public void setSalesEndDate(String salesEndDate) {
        this.salesEndDate = salesEndDate;
    }

    public List<Location> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(List<Location> locationDetails) {
        this.locationDetails = locationDetails;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public List<SubSubCategory> getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(List<SubSubCategory> subSubCategory) {
        this.subSubCategory = subSubCategory;
    }

    public Double getMonthlyRecurringCharge() {
        return monthlyRecurringCharge;
    }

    public void setMonthlyRecurringCharge(Double monthlyRecurringCharge) {
        this.monthlyRecurringCharge = monthlyRecurringCharge;
    }

    public Double getOneTimeCharge() {
        return oneTimeCharge;
    }

    public void setOneTimeCharge(Double oneTimeCharge) {
        this.oneTimeCharge = oneTimeCharge;
    }

    public Double getVariableCharge() {
        return variableCharge;
    }

    public void setVariableCharge(Double variableCharge) {
        this.variableCharge = variableCharge;
    }

    public String getPsorg() {
        return psorg;
    }

    public void setPsorg(String psorg) {
        this.psorg = psorg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getVariableProductUOM() {
        return variableProductUOM;
    }

    public void setVariableProductUOM(String variableProductUOM) {
        this.variableProductUOM = variableProductUOM;
    }

    public String getIsVariableProduct() {
        return isVariableProduct;
    }

    public void setIsVariableProduct(String isVariableProduct) {
        this.isVariableProduct = isVariableProduct;
    }

    public String getIsCappedProduct() {
        return isCappedProduct;
    }

    public void setIsCappedProduct(String isCappedProduct) {
        this.isCappedProduct = isCappedProduct;
    }

    public Map<String, Object> getSprod() {
        return sprod;
    }

    public void setSprod(Map<String, Object> sprod) {
        this.sprod = sprod;
    }

}
