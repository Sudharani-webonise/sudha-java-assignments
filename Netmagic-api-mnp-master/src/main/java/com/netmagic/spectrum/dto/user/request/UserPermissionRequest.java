package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the information of the request for fetching user
 * permissions
 * 
 * @author webonise
 *
 */
public class UserPermissionRequest implements Serializable {

    private static final long serialVersionUID = -6561155849420704302L;

    @JsonProperty("loginUserId")
    private Long userId;
    @JsonProperty("mainCustomerId")
    private Long mainCustomerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private Long projectId;

    public UserPermissionRequest() {
        super();
    }

    public UserPermissionRequest(Long userId, Long mainCustomerId, Long associateCustomerId, Long projectId) {
        super();
        this.userId = userId;
        this.mainCustomerId = mainCustomerId;
        this.associateCustomerId = associateCustomerId;
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
