package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the list of calling numbers category for the contact
 * 
 * @author webonise
 *
 */
public class ContactNumberCategory implements Serializable {

    private static final long serialVersionUID = -1578644535350385333L;

    @JsonProperty("contactNumberTypeId")
    private Long id;
    @JsonProperty("contactNumberTypeName")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
