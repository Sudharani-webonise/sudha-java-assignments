package com.netmagic.spectrum.dto.ticket.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the list of tickets
 * 
 * @author webonise
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketListResponse {

    @JsonProperty("ticketDetailsBeans")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
