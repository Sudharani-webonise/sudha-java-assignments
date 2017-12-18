package com.netmagic.spectrum.dto.contact.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the Contact Widget data
 * 
 * @author Pareekshit
 *
 */
public class WidgetRequest implements Serializable {

    private static final long serialVersionUID = -4930237635041112199L;

    @JsonProperty("mainCustomerId")
    private Long customerId;

    @JsonProperty("associateCustomerId")
    private Long associatedCustomerId;

    @JsonProperty("projectId")
    private String project;

    public WidgetRequest() {
        super();
    }

    public WidgetRequest(Long customerId, Long associatedCustomerId, String project) {
        super();
        this.customerId = customerId;
        this.associatedCustomerId = associatedCustomerId;
        this.project = project;
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

}
