package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the information of the file which is being uploaded or
 * downloaded from Netmagic Servers
 * 
 * @author webonise
 *
 */
public class AttachedFileInformation implements Serializable {

    private static final long serialVersionUID = 496621068108275683L;

    @JsonProperty("ticketAttachmentId")
    private Long ticketAttachmentId;
    @JsonProperty("ticketAttachmentDatetime")
    private String ticketAttachmentDatetime;
    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("attachmentFilename")
    private String filename;
    @JsonProperty("receivedfile")
    private String receivedfile;
    @JsonProperty("attachmentFullpath")
    private String attachmentFullpath;
    @JsonProperty("tick_atta_filesize")
    private Long fileSize;
    @JsonProperty("fileExtension")
    private String fileExtension;
    @JsonProperty("fileInBytes")
    private String fileInBytes;

    public Long getTicketAttachmentId() {
        return ticketAttachmentId;
    }

    public void setTicketAttachmentId(Long ticketAttachmentId) {
        this.ticketAttachmentId = ticketAttachmentId;
    }

    public String getTicketAttachmentDatetime() {
        return ticketAttachmentDatetime;
    }

    public void setTicketAttachmentDatetime(String ticketAttachmentDatetime) {
        this.ticketAttachmentDatetime = ticketAttachmentDatetime;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getReceivedfile() {
        return receivedfile;
    }

    public void setReceivedfile(String receivedfile) {
        this.receivedfile = receivedfile;
    }

    public String getAttachmentFullpath() {
        return attachmentFullpath;
    }

    public void setAttachmentFullpath(String attachmentFullpath) {
        this.attachmentFullpath = attachmentFullpath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileInBytes() {
        return fileInBytes;
    }

    public void setFileInBytes(String fileInBytes) {
        this.fileInBytes = fileInBytes;
    }
}
