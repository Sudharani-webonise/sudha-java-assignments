package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains information of a single ticket along with work logs
 * attached to it
 * 
 * @author webonise
 *
 */

public class TicketDetail implements Serializable {

    private static final long serialVersionUID = 3421899439833785552L;

    @JsonProperty("ticketInformationBean")
    private Ticket ticket;
    @JsonProperty("ticketWorklogUpdatesBeans")
    private List<TicketWorklog> ticketWorklogs;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<TicketWorklog> getTicketWorklogs() {
        return ticketWorklogs;
    }

    public void setTicketWorklogs(List<TicketWorklog> ticketWorklogs) {
        this.ticketWorklogs = ticketWorklogs;
    }
}
