package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This Request is used to make customer to super user
 * 
 * @author webonsie
 *
 */
public class SuperAdminRequest implements Serializable {

    private static final long serialVersionUID = 3713463198428856014L;

    @JsonProperty("mainCustomerId")
    private String mainCustomerId;
    @JsonProperty("associateCustomerId")
    private String associatedCustomerId;
    @JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("contactSugarrId")
    private String contactSugarrId;
    @JsonProperty("password")
    private String password;

    public String getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(String mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public String getAssociatedCustomerId() {
        return associatedCustomerId;
    }

    public void setAssociatedCustomerId(String associatedCustomerId) {
        this.associatedCustomerId = associatedCustomerId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getContactSugarrId() {
        return contactSugarrId;
    }

    public void setContactSugarrId(String contactSugarrId) {
        this.contactSugarrId = contactSugarrId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
