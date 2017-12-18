package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the count of Tickets based on Ticket Severity Type
 * 
 * @author webonise
 *
 */
public class TicketSeverityCount implements Serializable {

    private static final long serialVersionUID = -4490636388923513887L;

    @JsonProperty("severityType")
    private String severityType;

    @JsonProperty("ticketCount")
    private Long ticketCount;

    public TicketSeverityCount() {
        super();
    }

    public String getSeverityType() {
        return severityType;
    }

    public void setSeverityType(String severityType) {
        this.severityType = severityType;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
