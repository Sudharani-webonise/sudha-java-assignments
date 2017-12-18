package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the Customer contact details
 * 
 * @author webonise
 *
 */
public class CustomerContact implements Serializable {

    private static final long serialVersionUID = -7015142758762758919L;

    @JsonProperty("contactid")
    private String contactid;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("emailId")
    private Object emailId;
    @JsonProperty("mobileCountryCode")
    private Object mobileCountryCode;
    @JsonProperty("mobilephone")
    private String mobileNumber;

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
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

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public Object getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(Object mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
