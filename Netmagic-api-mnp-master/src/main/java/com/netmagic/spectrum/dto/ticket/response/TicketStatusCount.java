package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the count of Tickets based on Ticket Status Type
 * 
 * @author webonise
 *
 */

public class TicketStatusCount implements Serializable {

    private static final long serialVersionUID = 1980241085535001271L;
    @JsonProperty("ticketStatus")
    private String ticketStatus;
    @JsonProperty("ticketCount")
    private Long ticketCount;

    public TicketStatusCount() {
        super();
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
