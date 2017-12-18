package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the information of support to customer for which data is
 * being requested
 * 
 * @author webonise
 *
 */
public class SupportToCustomer implements Serializable {

    private static final long serialVersionUID = -5363875444230036859L;

    @JsonProperty("suppportToCustomerId")
    private String suppportToCustomerId;
    @JsonProperty("projectList")
    private Project project;

    public String getSuppportToCustomerId() {
        return suppportToCustomerId;
    }

    public void setSuppportToCustomerId(String suppportToCustomerId) {
        this.suppportToCustomerId = suppportToCustomerId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
