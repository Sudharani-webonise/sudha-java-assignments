package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the details of ContactSubType of a particular ContactType
 * 
 * @author Pareekshit
 *
 */
public class ContactSubType implements Serializable {

    private static final long serialVersionUID = 3799842453267791969L;

    @JsonProperty("subTypeId")
    private Long id;
    @JsonProperty("subTypeName")
    private String name;
    @JsonProperty("isActive")
    private String isActive;

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
