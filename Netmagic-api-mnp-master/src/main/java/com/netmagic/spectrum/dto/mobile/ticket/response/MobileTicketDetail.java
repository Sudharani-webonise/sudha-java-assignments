package com.netmagic.spectrum.dto.mobile.ticket.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains information of a single ticket along with work logs
 * attached to it
 * 
 * @author webonise
 * @version Spectrum 1.0
 */

public class MobileTicketDetail implements Serializable {

    private static final long serialVersionUID = -3421899439833785552L;

    @JsonProperty("ticketInformationBean")
    private MobileTicket ticket;
    @JsonProperty("ticketWorklogUpdatesBeans")
    private List<MobileTicketWorklog> ticketWorklogs;

    public MobileTicket getTicket() {
        return ticket;
    }

    public void setTicket(MobileTicket ticket) {
        this.ticket = ticket;
    }

    public List<MobileTicketWorklog> getTicketWorklogs() {
        return ticketWorklogs;
    }

    public void setTicketWorklogs(List<MobileTicketWorklog> ticketWorklogs) {
        this.ticketWorklogs = ticketWorklogs;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
