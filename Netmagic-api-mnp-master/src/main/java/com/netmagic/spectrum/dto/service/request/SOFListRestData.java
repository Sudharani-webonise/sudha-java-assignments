package com.netmagic.spectrum.dto.service.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the rest data required for SOF listing API in service
 * ledger
 * 
 * @author webonise
 *
 */
public class SOFListRestData implements Serializable {

    private static final long serialVersionUID = 4145222393202203056L;

    @JsonProperty("token")
    private String token;

    @JsonProperty("customerid")
    private String customerId;

    @JsonProperty("supportid")
    private String supportId;

    @JsonProperty("projectid")
    private String projectId;

    @JsonProperty("statuscode")
    private String statusCode;

    @JsonProperty("page")
    private Long page;

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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }
}
