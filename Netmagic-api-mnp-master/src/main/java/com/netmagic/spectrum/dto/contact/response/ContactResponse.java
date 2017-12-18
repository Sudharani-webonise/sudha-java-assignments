package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class records the response after a contact is added or updated
 * 
 * @author Pareekshit
 *
 */
public class ContactResponse implements Serializable {

    private static final long serialVersionUID = 6470867307926692529L;

    @JsonProperty("contactId")
    private String contactId;
    @JsonProperty("responseStatusBean")
    private ContactResponseStatus responseStatus;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public ContactResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ContactResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
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
