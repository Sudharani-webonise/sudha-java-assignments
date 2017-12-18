package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains the types of tickets which will be used to populate the
 * types for filtering in Ticket list display
 * 
 * @author webonise
 *
 */
public class FilteredTicketConfig implements Serializable {

    private static final long serialVersionUID = 7971337355188296814L;

    private List<String> ticketTypes;

    private List<String> severityTypes;

    private List<String> stateTypes;

    public List<String> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<String> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<String> getSeverityTypes() {
        return severityTypes;
    }

    public void setSeverityTypes(List<String> severityTypes) {
        this.severityTypes = severityTypes;
    }

    public List<String> getStateTypes() {
        return stateTypes;
    }

    public void setStateTypes(List<String> stateTypes) {
        this.stateTypes = stateTypes;
    }
}
