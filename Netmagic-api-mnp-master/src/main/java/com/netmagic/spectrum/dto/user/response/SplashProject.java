package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.TicketSeverityCount;

/**
 * This class maps the request object for the user authentication Project
 * Response
 * 
 * @author webonise
 * @version Spectrum 1.0
 */

public class SplashProject implements Serializable {

    private static final long serialVersionUID = -4663832155107824722L;

    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("projectLastUpdatedOn")
    private String projectLastUpdatedOn;
    @JsonProperty("allTicketsCount")
    private Long allTicketsCount;
    @JsonProperty("combinationId")
    private String combinationId;
    @JsonProperty("countBySeverityBeans")
    private List<TicketSeverityCount> countBySeverityBeans;
    @JsonProperty("customerAssetsCount")
    private Long customerAssetsCount;
    @JsonProperty("customerAlertsCount")
    private Long customerAlertsCount;
    @JsonProperty("sugarProjectId")
    private String sugarProjectId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(String combinationId) {
        this.combinationId = combinationId;
    }

    public String getProjectLastUpdatedOn() {
        return projectLastUpdatedOn;
    }

    public void setProjectLastUpdatedOn(String projectLastUpdatedOn) {
        this.projectLastUpdatedOn = projectLastUpdatedOn;
    }

    public Long getAllTicketsCount() {
        return allTicketsCount;
    }

    public void setAllTicketsCount(Long allTicketsCount) {
        this.allTicketsCount = allTicketsCount;
    }

    public List<TicketSeverityCount> getCountBySeverityBeans() {
        return countBySeverityBeans;
    }

    public void setCountBySeverityBeans(List<TicketSeverityCount> countBySeverityBeans) {
        this.countBySeverityBeans = countBySeverityBeans;
    }

    public Long getCustomerAssetsCount() {
        return customerAssetsCount;
    }

    public void setCustomerAssetsCount(Long customerAssetsCount) {
        this.customerAssetsCount = customerAssetsCount;
    }

    public Long getCustomerAlertsCount() {
        return customerAlertsCount;
    }

    public void setCustomerAlertsCount(Long customerAlertsCount) {
        this.customerAlertsCount = customerAlertsCount;
    }

    public String getSugarProjectId() {
        return sugarProjectId;
    }

    public void setSugarProjectId(String sugarProjectId) {
        this.sugarProjectId = sugarProjectId;
    }

}
