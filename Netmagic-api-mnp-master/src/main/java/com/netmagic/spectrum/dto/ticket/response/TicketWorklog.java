package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the details of a single work log related to a ticket
 * 
 * @author webonise
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketWorklog implements Serializable {

    private static final long serialVersionUID = -4820126957446560954L;

    @JsonProperty("worklogUpdatedByUser")
    private String updatedBy;
    @JsonProperty("worklogComment")
    private String comment;
    @JsonProperty("worklogCustomerComment")
    private String worklogCustomerComment;
    @JsonProperty("workLogUpdatetime")
    private String updatedAt;
    @JsonProperty("timeSpendOnPreviousAction")
    private String lastActionTimeDuration;
    @JsonProperty("worklogStatus")
    private String status;
    @JsonProperty("worklogSource")
    private String source;
    @JsonProperty("listOfTicketAttachmentBeans")
    private List<AttachedFileInformation> attachedFilesInformation;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWorklogCustomerComment() {
        return worklogCustomerComment;
    }

    public void setWorklogCustomerComment(String worklogCustomerComment) {
        this.worklogCustomerComment = worklogCustomerComment;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastActionTimeDuration() {
        return lastActionTimeDuration;
    }

    public void setLastActionTimeDuration(String lastActionTimeDuration) {
        this.lastActionTimeDuration = lastActionTimeDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<AttachedFileInformation> getAttachedFilesInformation() {
        return attachedFilesInformation;
    }

    public void setAttachedFilesInformation(List<AttachedFileInformation> attachedFilesInformation) {
        this.attachedFilesInformation = attachedFilesInformation;
    }
}
