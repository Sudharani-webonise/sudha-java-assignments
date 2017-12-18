package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the Data for Ticket Widget
 * 
 * @author webonise
 *
 */
public class TicketWidget implements Serializable {

    private static final long serialVersionUID = 4282862343073367985L;

    @JsonProperty("openTicketsCount")
    private Long openTicketsCount;
    @JsonProperty("ticketsCountByType")
    private List<TicketTypeCount> ticketsCountByType;
    @JsonProperty("ticketsCountBySeverity")
    private List<TicketSeverityCount> ticketsCountBySeverity;
    @JsonProperty("ticketsCountByTimeDelay")
    private List<TicketTimeDelayCount> ticketsCountByTimeDelay;
    @JsonProperty("ticketsCountByStatus")
    private List<TicketStatusCount> ticketsCountByStatus;

    public TicketWidget() {
        super();
    }

    public Long getOpenTicketsCount() {
        return openTicketsCount;
    }

    public void setOpenTicketsCount(Long openTicketsCount) {
        this.openTicketsCount = openTicketsCount;
    }

    public List<TicketTypeCount> getTicketsCountByType() {
        return ticketsCountByType;
    }

    public void setTicketsCountByType(List<TicketTypeCount> ticketsCountByType) {
        this.ticketsCountByType = ticketsCountByType;
    }

    public List<TicketSeverityCount> getTicketsCountBySeverity() {
        return ticketsCountBySeverity;
    }

    public void setTicketsCountBySeverity(List<TicketSeverityCount> ticketsCountBySeverity) {
        this.ticketsCountBySeverity = ticketsCountBySeverity;
    }

    public List<TicketTimeDelayCount> getTicketsCountByTimeDelay() {
        return ticketsCountByTimeDelay;
    }

    public void setTicketsCountByTimeDelay(List<TicketTimeDelayCount> ticketsCountByTimeDelay) {
        this.ticketsCountByTimeDelay = ticketsCountByTimeDelay;
    }

    public List<TicketStatusCount> getTicketsCountByStatus() {
        return ticketsCountByStatus;
    }

    public void setTicketsCountByStatus(List<TicketStatusCount> ticketsCountByStatus) {
        this.ticketsCountByStatus = ticketsCountByStatus;
    }
}
