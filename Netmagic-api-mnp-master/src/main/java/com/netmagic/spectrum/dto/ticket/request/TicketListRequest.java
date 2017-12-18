package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the ticket list api call to helios
 * 
 * @author webonise
 * @version Spectrum 1.0
 * @since 2016-04-07
 */
public class TicketListRequest implements Serializable {

    private static final long serialVersionUID = -6637156083797946629L;

    @JsonProperty("mainCustomerId")
    private Long mainCustomerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("ticketSeverity")
    private List<String> ticketSeverity;
    @JsonProperty("ticketType")
    private List<String> ticketType;
    @JsonProperty("ticketStatus")
    private List<String> ticketStatus;
    @JsonProperty("pageNumber")
    private Long pageNumber;
    @JsonProperty("perPageRecords")
    private Long perPageRecords;
    @JsonProperty("sortByFieldName")
    private String sortByFieldName;

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

    public List<String> getTicketSeverity() {
        return ticketSeverity;
    }

    public void setTicketSeverity(List<String> ticketSeverity) {
        this.ticketSeverity = ticketSeverity;
    }

    public List<String> getTicketType() {
        return ticketType;
    }

    public void setTicketType(List<String> ticketType) {
        this.ticketType = ticketType;
    }

    public List<String> getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(List<String> ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getPerPageRecords() {
        return perPageRecords;
    }

    public void setPerPageRecords(Long perPageRecords) {
        this.perPageRecords = perPageRecords;
    }

    public String getSortByFieldName() {
        return sortByFieldName;
    }

    public void setSortByFieldName(String sortByFieldName) {
        this.sortByFieldName = sortByFieldName;
    }

}
