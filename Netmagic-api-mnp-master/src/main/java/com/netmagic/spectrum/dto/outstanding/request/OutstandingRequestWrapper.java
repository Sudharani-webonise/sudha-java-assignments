package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to store the outstanding API request body value
 * 
 * @author webonise
 *
 */
public class OutstandingRequestWrapper implements Serializable {

    private static final long serialVersionUID = -4025454817447904798L;

    @JsonProperty("billToCustomer")
    private Long billToCustomer;
    @JsonProperty("businessUnit")
    private String businessUnit;
    @JsonProperty("suppportToCustomers")
    private SupportToCustomer supportToCustomer;

    public Long getBillToCustomer() {
        return billToCustomer;
    }

    public void setBillToCustomer(Long billToCustomer) {
        this.billToCustomer = billToCustomer;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public SupportToCustomer getSupportToCustomer() {
        return supportToCustomer;
    }

    public void setSupportToCustomer(SupportToCustomer supportToCustomer) {
        this.supportToCustomer = supportToCustomer;
    }

}
