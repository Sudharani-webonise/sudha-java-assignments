package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the contact types
 * 
 * @author webonise
 *
 */
public class ContactTypes implements Serializable {

    private static final long serialVersionUID = 4624835688655345687L;

    @JsonProperty("typeId")
    private Long typeId;
    @JsonProperty("typeName")
    private String typeName;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
