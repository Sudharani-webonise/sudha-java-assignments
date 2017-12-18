package com.netmagic.spectrum.dto.provision;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a reposonse data for provision SOF api
 * 
 * @author webonise
 */
public class SofDetails implements Serializable {

    private static final long serialVersionUID = 4579893802005655134L;

    @JsonProperty("sofNumber")
    private String sofNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("source")
    private String source;
    @JsonProperty("contractedPeriod")
    private Long contractedPeriod;
    @JsonProperty("lineItemNumber")
    private String lineItemNumber;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("location")
    private String location;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("pendingQty")
    private String pendingQuantity;
    @JsonProperty("productDesc")
    private String productDesc;
    @JsonProperty("serviceCategory")
    private String serviceCategory;
    @JsonProperty("serviceContractDetailsBean")
    private List<ServiceContract> serviceContractDetails;

    public String getSofNumber() {
        return sofNumber;
    }

    public void setSofNumber(String sofNumber) {
        this.sofNumber = sofNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLineItemNumber() {
        return lineItemNumber;
    }

    public Long getContractedPeriod() {
        return contractedPeriod;
    }

    public void setContractedPeriod(Long contractedPeriod) {
        this.contractedPeriod = contractedPeriod;
    }

    public void setLineItemNumber(String lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPendingQuantity() {
        return pendingQuantity;
    }

    public void setPendingQuantity(String pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public List<ServiceContract> getServiceContractDetails() {
        return serviceContractDetails;
    }

    public void setServiceContractDetails(List<ServiceContract> serviceContractDetails) {
        this.serviceContractDetails = serviceContractDetails;
    }
}
