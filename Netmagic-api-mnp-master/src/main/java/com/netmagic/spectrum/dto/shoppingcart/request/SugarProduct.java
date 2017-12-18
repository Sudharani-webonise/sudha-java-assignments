package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to hold the product information
 * 
 * @author webonise
 *
 */
public class SugarProduct implements Serializable {

    private static final long serialVersionUID = 7793337685553633936L;

    @JsonProperty("pid")
    private String productId;
    @JsonProperty("pname")
    private String productName;
    @JsonProperty("isOnlineProduct")
    private String isOnlineProduct;
    @JsonProperty("VariableProductUOM")
    private String variableProductUOM;
    @JsonProperty("isCappedProduct")
    private String isCappedProduct;
    @JsonProperty("isVariableProduct")
    private String isVariableProduct;
    @JsonProperty("ptype")
    private String productType;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("mrc")
    private Double monthlyRecurringCharge;
    @JsonProperty("otc")
    private Double oneTimeCharge;
    @JsonProperty("var")
    private Double variableCharge;
    @JsonProperty("pslctg")
    private String pslctg;

    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("isCustomizedProduct")
    private String isCustomizedProduct;
    @JsonProperty("salesEndDate")
    private String salesEndDate;

    // newly assumed data
    @JsonProperty("reasonFor")
    private String reasonFor;
    @JsonProperty("flag")
    private String flag;
    @JsonProperty("reasonFlag")
    private String reasonFlag;
    @JsonProperty("oldSofNumber")
    private String oldSofNumber;
    @JsonProperty("lineItemNumber")
    private String lineItemNumber;
    @JsonProperty("oldLineItemNumber")
    private String oldLineItemNumber;
    @JsonProperty("InstanceId")
    private String instanceId;
    @JsonProperty("ServiceType")
    private String serviceType;
    @JsonProperty("oldSofReasonFlag")
    private String oldSofReasonFlag;
    @JsonProperty("status")
    private String status;
    @JsonProperty("dependentTokenId")
    private String dependentTokenId;
    @JsonProperty("partialDelivery")
    private String partialDelivery;
    @JsonProperty("lineItemToken")
    private String lineItemToken;
    @JsonProperty("varProdAddedOfType")
    private String varProdAddedOfType;

    @JsonProperty("psorg")
    private String psorg;
    @JsonProperty("type")
    private String type;
    @JsonProperty("qty")
    private Long quantity;
    @JsonProperty("pdesc")
    private String productDescription;
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

    public String getVariableProductUOM() {
        return variableProductUOM;
    }

    public void setVariableProductUOM(String variableProductUOM) {
        this.variableProductUOM = variableProductUOM;
    }

    public String getIsCappedProduct() {
        return isCappedProduct;
    }

    public void setIsCappedProduct(String isCappedProduct) {
        this.isCappedProduct = isCappedProduct;
    }

    public String getIsVariableProduct() {
        return isVariableProduct;
    }

    public void setIsVariableProduct(String isVariableProduct) {
        this.isVariableProduct = isVariableProduct;
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

    public String getPslctg() {
        return pslctg;
    }

    public void setPslctg(String pslctg) {
        this.pslctg = pslctg;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getReasonFor() {
        return reasonFor;
    }

    public void setReasonFor(String reasonFor) {
        this.reasonFor = reasonFor;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getReasonFlag() {
        return reasonFlag;
    }

    public void setReasonFlag(String reasonFlag) {
        this.reasonFlag = reasonFlag;
    }

    public String getOldSofNumber() {
        return oldSofNumber;
    }

    public void setOldSofNumber(String oldSofNumber) {
        this.oldSofNumber = oldSofNumber;
    }

    public String getLineItemNumber() {
        return lineItemNumber;
    }

    public void setLineItemNumber(String lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }

    public String getOldLineItemNumber() {
        return oldLineItemNumber;
    }

    public void setOldLineItemNumber(String oldLineItemNumber) {
        this.oldLineItemNumber = oldLineItemNumber;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOldSofReasonFlag() {
        return oldSofReasonFlag;
    }

    public void setOldSofReasonFlag(String oldSofReasonFlag) {
        this.oldSofReasonFlag = oldSofReasonFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDependentTokenId() {
        return dependentTokenId;
    }

    public void setDependentTokenId(String dependentTokenId) {
        this.dependentTokenId = dependentTokenId;
    }

    public String getPartialDelivery() {
        return partialDelivery;
    }

    public void setPartialDelivery(String partialDelivery) {
        this.partialDelivery = partialDelivery;
    }

    public String getLineItemToken() {
        return lineItemToken;
    }

    public void setLineItemToken(String lineItemToken) {
        this.lineItemToken = lineItemToken;
    }

    public String getVarProdAddedOfType() {
        return varProdAddedOfType;
    }

    public void setVarProdAddedOfType(String varProdAddedOfType) {
        this.varProdAddedOfType = varProdAddedOfType;
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

    public Map<String, Object> getSprod() {
        return sprod;
    }

    public void setSprod(Map<String, Object> sprod) {
        this.sprod = sprod;
    }

}
