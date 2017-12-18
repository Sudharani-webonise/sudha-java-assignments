package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the details of a single ContactType
 * 
 * @author Pareekshit
 *
 */
public class ContactTypeDetails implements Serializable {

    private static final long serialVersionUID = -7961136274448479150L;

    @JsonProperty("typeId")
    private Long id;
    @JsonProperty("typeName")
    private String name;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("contactSubTypeBeans")
    private List<ContactSubType> contactSubTypes;

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

    public List<ContactSubType> getContactSubTypes() {
        return contactSubTypes;
    }

    public void setContactSubTypes(List<ContactSubType> contactSubTypes) {
        this.contactSubTypes = contactSubTypes;
    }
}
