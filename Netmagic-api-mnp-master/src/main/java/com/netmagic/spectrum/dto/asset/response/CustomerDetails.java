package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of a customer
 * 
 * @author webonise
 *
 */
public class CustomerDetails implements Serializable {

    private static final long serialVersionUID = -1889305316000018137L;

    @JsonProperty("cvName")
    private String customerName;
    @JsonProperty("cvCode")
    private String customerCode;
    @JsonProperty("cvCrmId")
    private Long customerId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("cvNameAndCrmID")
    private String cvNameAndCrmID;
    @JsonProperty("cvGenericEmail")
    private String email;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCvNameAndCrmID() {
        return cvNameAndCrmID;
    }

    public void setCvNameAndCrmID(String cvNameAndCrmID) {
        this.cvNameAndCrmID = cvNameAndCrmID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
