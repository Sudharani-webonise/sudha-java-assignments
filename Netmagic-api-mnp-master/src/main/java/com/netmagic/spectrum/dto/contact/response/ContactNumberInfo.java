package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the details for a single contact number
 * 
 * @author Pareekshit
 *
 */
public class ContactNumberInfo implements Serializable {

    private static final long serialVersionUID = 2220659000459758955L;

    @JsonProperty("contactNumberCategory")
    private String contactNumberCategory;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("contactNumber")
    private String contactNumber;

    public String getContactNumberCategory() {
        return contactNumberCategory;
    }

    public void setContactNumberCategory(String contactNumberCategory) {
        this.contactNumberCategory = contactNumberCategory;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
