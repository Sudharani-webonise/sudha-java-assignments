package com.netmagic.spectrum.dto.mobile.ticket.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netmagic.spectrum.dto.ticket.response.TicketWorklog;

/**
 * This class contains the details of a single work log related to a ticket
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
@JsonIgnoreProperties({ "timeSpendOnPreviousAction", "worklogStatus" })
public class MobileTicketWorklog extends TicketWorklog {

    private static final long serialVersionUID = -7064703256641926925L;

}
