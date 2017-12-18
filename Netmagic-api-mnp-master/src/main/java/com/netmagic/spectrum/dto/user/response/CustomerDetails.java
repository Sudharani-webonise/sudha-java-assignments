package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.TicketSeverityCount;

/**
 * This class maps the request object for the user authentication Response
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
public class CustomerDetails implements Serializable {

    private static final long serialVersionUID = -6108485619765021840L;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("customerSugarId")
    private String customerSugarId;

    @JsonProperty("customerLogoPath")
    private String customerLogoPath;

    @JsonProperty("customerLastUpdatedOn")
    private String customerLastUpdatedOn;

    @JsonProperty("allTicketsCount")
    private Long allTicketsCount;

    @JsonProperty("combinationId")
    private String combinationId;

    @JsonProperty("countBySeverityBeans")
    private List<TicketSeverityCount> severityCounts;

    @JsonProperty("customerAssetsCount")
    private Long customerAssetsCount;

    @JsonProperty("customerAlertsCount")
    private Long customerAlertsCount;

    @JsonProperty("isMainCustomer")
    private String isMainCustomer;

    @JsonProperty("projects")
    private List<SplashProject> projects;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerLogoPath() {
        return customerLogoPath;
    }

    public void setCustomerLogoPath(String customerLogoPath) {
        this.customerLogoPath = customerLogoPath;
    }

    public String getCustomerLastUpdatedOn() {
        return customerLastUpdatedOn;
    }

    public void setCustomerLastUpdatedOn(String customerLastUpdatedOn) {
        this.customerLastUpdatedOn = customerLastUpdatedOn;
    }

    public Long getAllTicketsCount() {
        return allTicketsCount;
    }

    public void setAllTicketsCount(Long allTicketsCount) {
        this.allTicketsCount = allTicketsCount;
    }

    public String getCustomerSugarId() {
        return customerSugarId;
    }

    public void setCustomerSugarId(String customerSugarId) {
        this.customerSugarId = customerSugarId;
    }

    public String getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(String combinationId) {
        this.combinationId = combinationId;
    }

    public List<TicketSeverityCount> getSeverityCounts() {
        return severityCounts;
    }

    public void setSeverityCounts(List<TicketSeverityCount> severityCounts) {
        this.severityCounts = severityCounts;
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

    public String getIsMainCustomer() {
        return isMainCustomer;
    }

    public void setIsMainCustomer(String isMainCustomer) {
        this.isMainCustomer = isMainCustomer;
    }

    public List<SplashProject> getProjects() {
        return projects;
    }

    public void setProjects(List<SplashProject> projects) {
        this.projects = projects;
    }

}
