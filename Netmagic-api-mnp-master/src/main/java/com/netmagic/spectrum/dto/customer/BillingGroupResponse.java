package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/****
 * This class contains the details of billing to customer details based on
 * customer id, associated customer id and the project id
 * 
 * @author webonise
 *
 */
public class BillingGroupResponse implements Serializable {

    private static final long serialVersionUID = -3272936180182114573L;

    @JsonProperty("billingGroupNumber")
    private String billingGroupNumber;
    @JsonProperty("billingGroupTypes")
    private String billingGroupTypes;
    @JsonProperty("unitOfMeasurment")
    private String unitOfMeasurment;
    @JsonProperty("totalContractedValue")
    private Double totalContractedValue;
    @JsonProperty("variableCharges")
    private Double variableCharges;
    @JsonProperty("variableChargesProduct")
    private String variableChargesProduct;
    @JsonProperty("rateBasedType")
    private String rateBasedType;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBillingGroupNumber() {
        return billingGroupNumber;
    }

    public void setBillingGroupNumber(String billingGroupNumber) {
        this.billingGroupNumber = billingGroupNumber;
    }

    public String getBillingGroupTypes() {
        return billingGroupTypes;
    }

    public void setBillingGroupTypes(String billingGroupTypes) {
        this.billingGroupTypes = billingGroupTypes;
    }

    public String getUnitOfMeasurment() {
        return unitOfMeasurment;
    }

    public void setUnitOfMeasurment(String unitOfMeasurment) {
        this.unitOfMeasurment = unitOfMeasurment;
    }

    public Double getTotalContractedValue() {
        return totalContractedValue;
    }

    public void setTotalContractedValue(Double totalContractedValue) {
        this.totalContractedValue = totalContractedValue;
    }

    public Double getVariableCharges() {
        return variableCharges;
    }

    public void setVariableCharges(Double variableCharges) {
        this.variableCharges = variableCharges;
    }

    public String getVariableChargesProduct() {
        return variableChargesProduct;
    }

    public void setVariableChargesProduct(String variableChargesProduct) {
        this.variableChargesProduct = variableChargesProduct;
    }

    public String getRateBasedType() {
        return rateBasedType;
    }

    public void setRateBasedType(String rateBasedType) {
        this.rateBasedType = rateBasedType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String fieldName, Object value) {
        this.additionalProperties.put(fieldName, value);
    }

}
