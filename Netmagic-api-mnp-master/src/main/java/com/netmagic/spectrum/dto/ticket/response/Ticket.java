package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains single ticket information required to be displayed while
 * listing tickets
 * 
 * @author webonise
 *
 */
public class Ticket implements Serializable {

    private static final long serialVersionUID = 7399460715429364083L;
    @JsonProperty("ticketNumber")
    private String number;
    @JsonProperty("assignedToGroup")
    private String assignedToGroup;
    @JsonProperty("ticketOwner")
    private String owner;
    @JsonProperty("ticketProject")
    private String project;
    @JsonProperty("ticketSeverity")
    private String severity;
    @JsonProperty("caseType")
    private String caseType;
    @JsonProperty("caseSubType")
    private String caseSubType;
    @JsonProperty("ticketStatus")
    private String status;
    @JsonProperty("ticketSummary")
    private String summary;
    @JsonProperty("ticketCustomerDesc")
    private String customerDescription;
    @JsonProperty("ticketOpenSince")
    private String openSince;
    @JsonProperty("ticketRaisedBy")
    private String raisedBy;
    @JsonProperty("ticketCreatedDate")
    private String createdAt;
    @JsonProperty("ticketLastUpdatedDate")
    private String lastUpdatedAt;
    @JsonProperty("associatedCustomer")
    private String associatedCustomerId;
    @JsonProperty("projectName")
    private String projectName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAssignedToGroup() {
        return assignedToGroup;
    }

    public void setAssignedToGroup(String assignedToGroup) {
        this.assignedToGroup = assignedToGroup;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseSubType() {
        return caseSubType;
    }

    public void setCaseSubType(String caseSubType) {
        this.caseSubType = caseSubType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public String getOpenSince() {
        return openSince;
    }

    public void setOpenSince(String openSince) {
        this.openSince = openSince;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getAssociatedCustomerId() {
        return associatedCustomerId;
    }

    public void setAssociatedCustomerId(String associatedCustomerId) {
        this.associatedCustomerId = associatedCustomerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
