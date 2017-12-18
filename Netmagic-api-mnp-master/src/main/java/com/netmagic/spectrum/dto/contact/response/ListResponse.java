package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the list of contacts
 * 
 * @author Pareekshit
 *
 */
public class ListResponse implements Serializable {

    private static final long serialVersionUID = -6979221022026349467L;

    @JsonProperty("listOfMynmContactHeaderBeans")
    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
