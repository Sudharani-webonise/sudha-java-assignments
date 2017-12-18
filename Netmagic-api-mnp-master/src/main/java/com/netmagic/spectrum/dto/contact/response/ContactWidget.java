package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the Contact Widget Data with total contact count and count
 * based on ContactTypes
 * 
 * @author Pareekshit
 *
 */
public class ContactWidget implements Serializable {

    private static final long serialVersionUID = -4918218677184217881L;

    @JsonProperty("totalContactsCount")
    private Long totalContactsCount;
    @JsonProperty("listOfContactsByTypeBean")
    private List<ContactType> contactTypes;

    public Long getTotalContactsCount() {
        return totalContactsCount;
    }

    public void setTotalContactsCount(Long totalContactsCount) {
        this.totalContactsCount = totalContactsCount;
    }

    public List<ContactType> getContactTypes() {
        return contactTypes;
    }

    public void setContactTypes(List<ContactType> contactTypes) {
        this.contactTypes = contactTypes;
    }
}
