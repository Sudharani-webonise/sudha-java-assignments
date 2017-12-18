package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the Contact Sub type List
 * 
 * @author webonise
 *
 */
public class ContactSubTypeList implements Serializable {

    private static final long serialVersionUID = 5444821110006357812L;

    @JsonProperty("contactSubTypeBeanList")
    private List<ContactSubType> subTypes;

    public List<ContactSubType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<ContactSubType> subTypes) {
        this.subTypes = subTypes;
    }

}
