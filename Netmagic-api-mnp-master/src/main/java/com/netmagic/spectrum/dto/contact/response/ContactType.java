package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the total count of contacts of a Single ContactType
 * 
 * @author Pareekshit
 *
 */
public class ContactType implements Serializable {

    private static final long serialVersionUID = -1174816413937211200L;

    @JsonProperty("contactType")
    private String contactType;
    @JsonProperty("contactTypeId")
    private String contactTypeId;
    @JsonProperty("contactTypeCount")
    private Long contactTypeCount;

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(String contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public Long getContactTypeCount() {
        return contactTypeCount;
    }

    public void setContactTypeCount(Long contactTypeCount) {
        this.contactTypeCount = contactTypeCount;
    }
}
