package com.netmagic.spectrum.dto.contact.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the list of contacts when fetched from NM Servers
 * 
 * @author Pareekshit
 *
 */
public class ListRequest implements Serializable {

    private static final long serialVersionUID = 2921708211252762935L;

    @JsonProperty("mainCustomerId")
    private Long customerId;

    @JsonProperty("associateCustomerId")
    private Long associatedCustomerId;

    @JsonProperty("projectId")
    private String project;

    @JsonProperty("contactType")
    private String contactType;

    public ListRequest() {
        super();
    }

    public ListRequest(Long customerId, Long associatedCustomerId, String project, String contactType) {
        super();
        this.customerId = customerId;
        this.associatedCustomerId = associatedCustomerId;
        this.project = project;
        this.contactType = contactType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAssociatedCustomerId() {
        return associatedCustomerId;
    }

    public void setAssociatedCustomerId(Long associatedCustomerId) {
        this.associatedCustomerId = associatedCustomerId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
}
