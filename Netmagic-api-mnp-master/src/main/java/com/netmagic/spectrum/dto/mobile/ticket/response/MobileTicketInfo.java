package com.netmagic.spectrum.dto.mobile.ticket.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.Ticket;

/**
 * This class contains single ticket information required to be displayed while
 * listing tickets for mobile
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
@JsonIgnoreProperties({ "assignedToGroup", "ticketOwner", "ticketProject", "caseSubType", "ticketCustomerDesc",
        "ticketRaisedBy", "ticketCreatedDate", "projectName" })
public class MobileTicketInfo extends Ticket {

    private static final long serialVersionUID = -4064770814162757803L;

    @JsonProperty("ticketWorklogUpdatesBean")
    private MobileTicketWorklog ticketWorklogUpdatesBean;
}
