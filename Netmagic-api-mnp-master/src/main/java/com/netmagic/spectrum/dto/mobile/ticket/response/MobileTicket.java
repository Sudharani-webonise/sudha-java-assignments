package com.netmagic.spectrum.dto.mobile.ticket.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netmagic.spectrum.dto.ticket.response.Ticket;

/**
 * This class contains single ticket information required to be displayed while
 * mobile listing tickets
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
@JsonIgnoreProperties({ "ticketOwner", "ticketProject" })
public class MobileTicket extends Ticket {

    private static final long serialVersionUID = -3824646659649478570L;

}
