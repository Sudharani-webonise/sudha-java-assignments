package com.netmagic.spectrum.dto.asset.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the data for the asset widget request.
 * 
 * @author webonise
 *
 */
public class WidgetRequest implements Serializable {

    private static final long serialVersionUID = -2028718304750667424L;

    @JsonProperty("mainCustomerId")
    private Long customerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private String projectId;

    public WidgetRequest() {
        super();
    }

    public WidgetRequest(Long customerId, Long associateCustomerId, String projectId) {
        super();
        this.customerId = customerId;
        this.associateCustomerId = associateCustomerId;
        this.projectId = projectId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
