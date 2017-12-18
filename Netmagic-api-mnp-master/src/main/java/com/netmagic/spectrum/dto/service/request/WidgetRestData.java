package com.netmagic.spectrum.dto.service.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the widget rest data required API in service ledger
 * 
 * @author webonise
 *
 */
public class WidgetRestData implements Serializable {

    private static final long serialVersionUID = -6311030898889356880L;

    @JsonProperty("token")
    private String token;

    @JsonProperty("customerid")
    private String customerId;

    @JsonProperty("supportid")
    private String supportId;

    @JsonProperty("projectid")
    private String projectId;

    public WidgetRestData() {
        super();
    }

    public WidgetRestData(String customerId, String supportId, String projectId) {
        super();
        this.customerId = customerId;
        this.supportId = supportId;
        this.projectId = projectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSupportId() {
        return supportId;
    }

    public void setSupportId(String supportId) {
        this.supportId = supportId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
