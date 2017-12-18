package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the details and price for an individual product
 * 
 * @author webonise
 *
 */
public class ProductPrice implements Serializable {

    private static final long serialVersionUID = 327912999620199351L;

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
    @JsonProperty("psorg")
    private String psorg;
    @JsonProperty("isCustomizedProduct")
    private String isCustomizedProduct;
    @JsonProperty("salesEndDate")
    private String salesEndDate;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;

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

    @JsonProperty("type")
    private String type;
    @JsonProperty("qty")
    private Long quantity;
    @JsonProperty("pdesc")
    private String productDescription;
    @JsonProperty("sprod")
    private Map<String, Object> sprod = new HashMap<String, Object>();
    @JsonProperty("rc_applied")
    private String rcApplied;
    @JsonProperty("uom_c")
    private String uomC;
    @JsonProperty("spmrc")
    private Double productMonthlyRecurringCost;
    @JsonProperty("spotc")
    private Double productOneTimeCost;
    @JsonProperty("spvc")
    private Double productVariableChargeCost;
    @JsonProperty("isPCD")
    private Long isPCD;
    @JsonProperty("spmrc_sale")
    private Double productMonthlyRecurringSale;
    @JsonProperty("spotc_sale")
    private Double productOneTimeCostSale;
    @JsonProperty("spvc_sale")
    private Double productVariableChargeCostSale;
    @JsonProperty("spotc_tot")
    private Double productOneTimeCostTotal;
    @JsonProperty("spmrc_tot")
    private Double productMonthlyRecurringCostTotal;
    @JsonProperty("spvc_tot")
    private Double productVariableChargeCostTotal;
    @JsonProperty("spmrc_tot_cntr")
    private Double spmrcTotCntr;
    @JsonProperty("spmrc_tax")
    private Double productMonthlyRecurringCostWithTax;
    @JsonProperty("spotc_tax")
    private Double productOneTimeCostWithTax;
    @JsonProperty("spvc_tax")
    private Double productVariableChargeCostWithTax;

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

    public String getPsorg() {
        return psorg;
    }

    public void setPsorg(String psorg) {
        this.psorg = psorg;
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

    public String getRcApplied() {
        return rcApplied;
    }

    public void setRcApplied(String rcApplied) {
        this.rcApplied = rcApplied;
    }

    public String getUomC() {
        return uomC;
    }

    public void setUomC(String uomC) {
        this.uomC = uomC;
    }

    public Double getProductMonthlyRecurringCost() {
        return productMonthlyRecurringCost;
    }

    public void setProductMonthlyRecurringCost(Double productMonthlyRecurringCost) {
        this.productMonthlyRecurringCost = productMonthlyRecurringCost;
    }

    public Double getProductOneTimeCost() {
        return productOneTimeCost;
    }

    public void setProductOneTimeCost(Double productOneTimeCost) {
        this.productOneTimeCost = productOneTimeCost;
    }

    public Double getProductVariableChargeCost() {
        return productVariableChargeCost;
    }

    public void setProductVariableChargeCost(Double productVariableChargeCost) {
        this.productVariableChargeCost = productVariableChargeCost;
    }

    public Long getIsPCD() {
        return isPCD;
    }

    public void setIsPCD(Long isPCD) {
        this.isPCD = isPCD;
    }

    public Double getProductMonthlyRecurringSale() {
        return productMonthlyRecurringSale;
    }

    public void setProductMonthlyRecurringSale(Double productMonthlyRecurringSale) {
        this.productMonthlyRecurringSale = productMonthlyRecurringSale;
    }

    public Double getProductOneTimeCostSale() {
        return productOneTimeCostSale;
    }

    public void setProductOneTimeCostSale(Double productOneTimeCostSale) {
        this.productOneTimeCostSale = productOneTimeCostSale;
    }

    public Double getProductVariableChargeCostSale() {
        return productVariableChargeCostSale;
    }

    public void setProductVariableChargeCostSale(Double productVariableChargeCostSale) {
        this.productVariableChargeCostSale = productVariableChargeCostSale;
    }

    public Double getProductOneTimeCostTotal() {
        return productOneTimeCostTotal;
    }

    public void setProductOneTimeCostTotal(Double productOneTimeCostTotal) {
        this.productOneTimeCostTotal = productOneTimeCostTotal;
    }

    public Double getProductMonthlyRecurringCostTotal() {
        return productMonthlyRecurringCostTotal;
    }

    public void setProductMonthlyRecurringCostTotal(Double productMonthlyRecurringCostTotal) {
        this.productMonthlyRecurringCostTotal = productMonthlyRecurringCostTotal;
    }

    public Double getProductVariableChargeCostTotal() {
        return productVariableChargeCostTotal;
    }

    public void setProductVariableChargeCostTotal(Double productVariableChargeCostTotal) {
        this.productVariableChargeCostTotal = productVariableChargeCostTotal;
    }

    public Double getSpmrcTotCntr() {
        return spmrcTotCntr;
    }

    public void setSpmrcTotCntr(Double spmrcTotCntr) {
        this.spmrcTotCntr = spmrcTotCntr;
    }

    public Double getProductMonthlyRecurringCostWithTax() {
        return productMonthlyRecurringCostWithTax;
    }

    public void setProductMonthlyRecurringCostWithTax(Double productMonthlyRecurringCostWithTax) {
        this.productMonthlyRecurringCostWithTax = productMonthlyRecurringCostWithTax;
    }

    public Double getProductOneTimeCostWithTax() {
        return productOneTimeCostWithTax;
    }

    public void setProductOneTimeCostWithTax(Double productOneTimeCostWithTax) {
        this.productOneTimeCostWithTax = productOneTimeCostWithTax;
    }

    public Double getProductVariableChargeCostWithTax() {
        return productVariableChargeCostWithTax;
    }

    public void setProductVariableChargeCostWithTax(Double productVariableChargeCostWithTax) {
        this.productVariableChargeCostWithTax = productVariableChargeCostWithTax;
    }

}
