package com.netmagic.spectrum.dto.mobile.ticket.response;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the list of tickets
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileTicketListResponse {

    @JsonProperty("ticketDetailsBeans")
    private List<MobileTicketInfo> tickets;

    public List<MobileTicketInfo> getTickets() {
        return tickets;
    }

    public void setTickets(List<MobileTicketInfo> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
