package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.ticket.response.AttachedFileInformation;

/**
 * This class stores the information of a work log which is uploaded against a
 * ticket
 * 
 * @author webonise
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateWorklogRequest implements Serializable {

    private static final long serialVersionUID = 688703251394273658L;

    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("worklogComment")
    private String comment;
    @JsonProperty("worklogUserName")
    private String username;
    @JsonProperty("uploadFilesList")
    private List<AttachedFileInformation> attachedFilesInformation;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<AttachedFileInformation> getAttachedFilesInformation() {
        return attachedFilesInformation;
    }

    public void setAttachedFilesInformation(List<AttachedFileInformation> attachedFilesInformation) {
        this.attachedFilesInformation = attachedFilesInformation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
