package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the information after a new ticket is created
 * 
 * @author webonise
 *
 */
public class RaiseTicketResponse implements Serializable {

    private static final long serialVersionUID = 8986806308008055521L;

    @JsonProperty("ticketd")
    private Long ticketd;
    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("ticketSummary")
    private String ticketSummary;
    @JsonProperty("ticketGroup")
    private String ticketGroup;
    @JsonProperty("ticketWorkLogId")
    private Long ticketWorkLogId;
    @JsonProperty("ticketValidate")
    private Boolean ticketValidate;
    @JsonProperty("errorMessage")
    private String errorMessage;

    public Long getTicketd() {
        return ticketd;
    }

    public void setTicketd(Long ticketd) {
        this.ticketd = ticketd;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketSummary() {
        return ticketSummary;
    }

    public void setTicketSummary(String ticketSummary) {
        this.ticketSummary = ticketSummary;
    }

    public String getTicketGroup() {
        return ticketGroup;
    }

    public void setTicketGroup(String ticketGroup) {
        this.ticketGroup = ticketGroup;
    }

    public Long getTicketWorkLogId() {
        return ticketWorkLogId;
    }

    public void setTicketWorkLogId(Long ticketWorkLogId) {
        this.ticketWorkLogId = ticketWorkLogId;
    }

    public Boolean getTicketValidate() {
        return ticketValidate;
    }

    public void setTicketValidate(Boolean ticketValidate) {
        this.ticketValidate = ticketValidate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
