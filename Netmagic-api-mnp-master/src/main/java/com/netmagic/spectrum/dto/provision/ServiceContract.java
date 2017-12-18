package com.netmagic.spectrum.dto.provision;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains all the Information about Service Contract
 * 
 * @author webonise
 */
public class ServiceContract implements Serializable {

    private static final long serialVersionUID = -6507872136959051839L;

    @JsonProperty("isBillingGroup")
    private String isBillingGroup;
    @JsonProperty("isExcessUsage")
    private String isExcessUsage;
    @JsonProperty("productSubSubCategory")
    private String productSubSubCategory;
    @JsonProperty("serviceDetailsAttributeBeans")
    private List<Object> serviceDetailsAttribute;
    @JsonProperty("serviceContractedQty")
    private String serviceContractedQty;
    @JsonProperty("serviceProductCode")
    private String serviceProductCode;
    @JsonProperty("serviceDesc")
    private String serviceDesc;
    @JsonProperty("serviceCategory")
    private String serviceCategory;

    public String getIsBillingGroup() {
        return isBillingGroup;
    }

    public void setIsBillingGroup(String isBillingGroup) {
        this.isBillingGroup = isBillingGroup;
    }

    public String getIsExcessUsage() {
        return isExcessUsage;
    }

    public void setIsExcessUsage(String isExcessUsage) {
        this.isExcessUsage = isExcessUsage;
    }

    public String getProductSubSubCategory() {
        return productSubSubCategory;
    }

    public void setProductSubSubCategory(String productSubSubCategory) {
        this.productSubSubCategory = productSubSubCategory;
    }

    public String getServiceContractedQty() {
        return serviceContractedQty;
    }

    public void setServiceContractedQty(String serviceContractedQty) {
        this.serviceContractedQty = serviceContractedQty;
    }

    public List<Object> getServiceDetailsAttribute() {
        return serviceDetailsAttribute;
    }

    public void setServiceDetailsAttribute(List<Object> serviceDetailsAttribute) {
        this.serviceDetailsAttribute = serviceDetailsAttribute;
    }

    public String getServiceProductCode() {
        return serviceProductCode;
    }

    public void setServiceProductCode(String serviceProductCode) {
        this.serviceProductCode = serviceProductCode;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
}
