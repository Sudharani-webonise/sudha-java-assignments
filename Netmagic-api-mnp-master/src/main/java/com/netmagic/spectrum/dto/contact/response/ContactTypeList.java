package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the Contact type List
 * 
 * @author webonise
 *
 */
public class ContactTypeList implements Serializable {

    private static final long serialVersionUID = -5550669722927317940L;

    @JsonProperty("contactTypeBeans")
    private List<ContactTypes> types;

    public List<ContactTypes> getTypes() {
        return types;
    }

    public void setTypes(List<ContactTypes> types) {
        this.types = types;
    }

}
