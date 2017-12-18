package com.netmagic.spectrum.dto.ticket.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.cache.Cacheable;

/**
 * Class stores the different classification of Tickets Available
 * 
 * @author webonise
 *
 */
public class TicketConfig implements Cacheable {

    private static final long serialVersionUID = -8535546006415986119L;

    private static final String CACHE_KEY = "CONFIG";

    private static final String OBJECT_KEY = "TICKET";

    @JsonProperty("ticketSeverityBeans")
    private List<TicketSeverity> ticketSeverities;
    @JsonProperty("ticketCaseTypeBeans")
    private List<TicketCaseType> ticketCaseTypes;
    @JsonProperty("ticketStateBeans")
    private List<TicketState> ticketStates;

    public TicketConfig() {
        super();
    }

    public List<TicketSeverity> getTicketSeverities() {
        return ticketSeverities;
    }

    public void setTicketSeverities(List<TicketSeverity> ticketSeverities) {
        this.ticketSeverities = ticketSeverities;
    }

    public List<TicketCaseType> getTicketCaseTypes() {
        return ticketCaseTypes;
    }

    public void setTicketCaseTypes(List<TicketCaseType> ticketCaseTypes) {
        this.ticketCaseTypes = ticketCaseTypes;
    }

    public List<TicketState> getTicketStates() {
        return ticketStates;
    }

    public void setTicketStates(List<TicketState> ticketStates) {
        this.ticketStates = ticketStates;
    }

    @Override
    public String getCacheKey() {
        return CACHE_KEY;
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }
}
