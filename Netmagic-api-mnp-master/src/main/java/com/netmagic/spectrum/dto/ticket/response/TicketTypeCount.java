package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the count of Tickets based on Ticket Type
 * 
 * @author webonise
 *
 */

public class TicketTypeCount implements Serializable {

    private static final long serialVersionUID = -4900825774724425698L;

    @JsonProperty("ticketType")
    private String ticketType;
    @JsonProperty("ticketCount")
    private Long ticketCount;

    public TicketTypeCount() {
        super();
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
