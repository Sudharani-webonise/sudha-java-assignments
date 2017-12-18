package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the ticket list api call to helios
 * 
 * @author ketan
 * @version Spectrum 1.0
 * @since 2016-04-11
 */

public class UpdateTicketRequest implements Serializable {

    private static final long serialVersionUID = -8379960309666257346L;

    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("updateComment")
    private String updateComment;
    @JsonProperty("updatedByUser")
    private String updatedByUser;
    @JsonProperty("changeStatus")
    private String newStatus;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUpdateComment() {
        return updateComment;
    }

    public void setUpdateComment(String updateComment) {
        this.updateComment = updateComment;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
