package com.netmagic.spectrum.dto.service.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the data of a service line item of a particular SOF
 * 
 * @author webonise
 */
public class ServiceLineItem implements Serializable {

    private static final long serialVersionUID = 7340921399582148306L;

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("LineNumber")
    private String lineNumber;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("ServiceAttribute")
    private String serviceAttribute;

    @JsonProperty("subCatName")
    private String subCategoryName;

    @JsonProperty("subsubCatName")
    private String subSubCategoryName;

    @JsonProperty("ServiceAttributeDetail")
    private String serviceAttributeDetails;

    @JsonProperty("OrderedQty")
    private Double orderedQuantity;

    @JsonProperty("PartialDeliveryDetails")
    private List<PartialDeliveryDetails> partialDeliveryDetails;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServiceAttribute() {
        return serviceAttribute;
    }

    public void setServiceAttribute(String serviceAttribute) {
        this.serviceAttribute = serviceAttribute;
    }

    public Double getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Double orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public List<PartialDeliveryDetails> getPartialDeliveryDetails() {
        return partialDeliveryDetails;
    }

    public void setPartialDeliveryDetails(List<PartialDeliveryDetails> partialDeliveryDetails) {
        this.partialDeliveryDetails = partialDeliveryDetails;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubSubCategoryName() {
        return subSubCategoryName;
    }

    public void setSubSubCategoryName(String subSubCategoryName) {
        this.subSubCategoryName = subSubCategoryName;
    }

    public String getServiceAttributeDetails() {
        return serviceAttributeDetails;
    }

    public void setServiceAttributeDetails(String serviceAttributeDetails) {
        this.serviceAttributeDetails = serviceAttributeDetails;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
