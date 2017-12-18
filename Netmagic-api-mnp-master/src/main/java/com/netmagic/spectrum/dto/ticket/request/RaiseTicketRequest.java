package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.AttachedFileInformation;

/**
 * This class contains the data for creating a New Ticket
 * 
 * @author webonise
 *
 */

public class RaiseTicketRequest implements Serializable {

    private static final long serialVersionUID = -8297373966334110901L;

    @JsonProperty("customerIdHidden")
    private String customerIdHidden;
    @JsonProperty("supportToCustomerId")
    private String supportToCustomerId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("ticketType")
    private Integer ticketType;
    @JsonProperty("ticketCategory")
    private Integer ticketCategory;
    @JsonProperty("ticketoriginator")
    private String ticketoriginator;
    @JsonProperty("ticketSummary")
    private String ticketSummary;
    @JsonProperty("ticketDescription")
    private String ticketDescription;
    @JsonProperty("additionalNotificationPersonTableList")
    private List<ContactNotification> contactsNotification;
    @JsonProperty("listOfFilesInByte")
    private List<AttachedFileInformation> attachedFilesInformation;

    public String getCustomerIdHidden() {
        return customerIdHidden;
    }

    public void setCustomerIdHidden(String customerIdHidden) {
        this.customerIdHidden = customerIdHidden;
    }

    public String getSupportToCustomerId() {
        return supportToCustomerId;
    }

    public void setSupportToCustomerId(String supportToCustomerId) {
        this.supportToCustomerId = supportToCustomerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(Integer ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public String getTicketoriginator() {
        return ticketoriginator;
    }

    public void setTicketoriginator(String ticketoriginator) {
        this.ticketoriginator = ticketoriginator;
    }

    public String getTicketSummary() {
        return ticketSummary;
    }

    public void setTicketSummary(String ticketSummary) {
        this.ticketSummary = ticketSummary;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public List<ContactNotification> getContactsNotification() {
        return contactsNotification;
    }

    public void setContactsNotification(List<ContactNotification> contactsNotification) {
        this.contactsNotification = contactsNotification;
    }

    public List<AttachedFileInformation> getAttachedFilesInformation() {
        return attachedFilesInformation;
    }

    public void setAttachedFilesInformation(List<AttachedFileInformation> attachedFilesInformation) {
        this.attachedFilesInformation = attachedFilesInformation;
    }
}
