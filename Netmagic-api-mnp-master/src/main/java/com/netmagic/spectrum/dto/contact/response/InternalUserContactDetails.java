package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of a internal customer contact details
 * 
 * @author Webonise
 *
 */

public class InternalUserContactDetails implements Serializable {

    private static final long serialVersionUID = -2665399282441354165L;

    @JsonProperty("contactFirtName")
    private String contactFirtName;
    @JsonProperty("contactLastName")
    private String contactLastName;
    @JsonProperty("contactEmailId")
    private String contactEmailId;
    @JsonProperty("contactType")
    private String contactType;
    @JsonProperty("contactMobileNO")
    private String contactMobileNO;

    public String getContactFirtName() {
        return contactFirtName;
    }

    public void setContactFirtName(String contactFirtName) {
        this.contactFirtName = contactFirtName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactEmailId() {
        return contactEmailId;
    }

    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactMobileNO() {
        return contactMobileNO;
    }

    public void setContactMobileNO(String contactMobileNO) {
        this.contactMobileNO = contactMobileNO;
    }

}
