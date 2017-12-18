package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the count of Tickets based on Time Delay
 * 
 * @author webonise
 *
 */

public class TicketTimeDelayCount implements Serializable {

    private static final long serialVersionUID = -6669547001758284585L;

    @JsonProperty("timeDelayUnit")
    private String timeDelayUnit;
    @JsonProperty("ticketCount")
    private Long ticketCount;

    public TicketTimeDelayCount() {
        super();
    }

    public String getTimeDelayUnit() {
        return timeDelayUnit;
    }

    public void setTimeDelayUnit(String timeDelayUnit) {
        this.timeDelayUnit = timeDelayUnit;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
