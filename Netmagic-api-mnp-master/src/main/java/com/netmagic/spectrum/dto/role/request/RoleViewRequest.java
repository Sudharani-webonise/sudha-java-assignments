package com.netmagic.spectrum.dto.role.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the role view details request
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
public class RoleViewRequest implements Serializable {

    private static final long serialVersionUID = -7065850103946796129L;

    @JsonProperty("mainCustomerId")
    private Long mainCustomerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private String projectId;

    public Long getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(Long mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
