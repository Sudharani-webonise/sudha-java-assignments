package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the values of a single Contact associated with a particular
 * customer and associated customer
 * 
 * @author webonise
 *
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = -7758516583545561964L;

    @JsonProperty("contactId")
    private String contactId;
    @JsonProperty("custContFname")
    private String firstName;
    @JsonProperty("custContLname")
    private String lastName;
    @JsonProperty("custName")
    private String customerName;
    @JsonProperty("custCrmId")
    private String customerId;
    @JsonProperty("contactName")
    private String contactName;
    @JsonProperty("contactEmailId")
    private String contactEmailId;
    @JsonProperty("contactMobileNo")
    private String contactMobileNo;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmailId() {
        return contactEmailId;
    }

    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public String getContactMobileNo() {
        return contactMobileNo;
    }

    public void setContactMobileNo(String contactMobileNo) {
        this.contactMobileNo = contactMobileNo;
    }

}
