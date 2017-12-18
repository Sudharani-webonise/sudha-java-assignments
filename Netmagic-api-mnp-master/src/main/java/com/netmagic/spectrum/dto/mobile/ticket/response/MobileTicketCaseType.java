package com.netmagic.spectrum.dto.mobile.ticket.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseType;

/**
 * wrapper to get the MobileTicketCaseType
 * 
 * @author webonise
 *
 */
public class MobileTicketCaseType implements Serializable {

    private static final long serialVersionUID = 3579336205153412002L;

    @JsonProperty(value = "ticketCaseType")
    private List<TicketCaseType> mobileTicketCaseType;

    public List<TicketCaseType> getMobileTicketCaseType() {
        return mobileTicketCaseType;
    }

    public void setMobileTicketCaseType(List<TicketCaseType> mobileTicketCaseType) {
        this.mobileTicketCaseType = mobileTicketCaseType;
    }

}
