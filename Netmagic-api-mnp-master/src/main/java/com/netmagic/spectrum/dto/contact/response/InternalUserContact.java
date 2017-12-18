package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This wrapper class fetch the information of a internal customer contact
 * details
 * 
 * @author Webonise
 *
 */
public class InternalUserContact implements Serializable {

    private static final long serialVersionUID = -5850063668735781097L;

    @JsonProperty("listOfNetmagicContactBean")
    private List<InternalUserContactDetails> internalCustomerContacts;

    public List<InternalUserContactDetails> getInternalCustomerContacts() {
        return internalCustomerContacts;
    }

    public void setInternalCustomerContacts(List<InternalUserContactDetails> internalCustomerContacts) {
        this.internalCustomerContacts = internalCustomerContacts;
    }

}
