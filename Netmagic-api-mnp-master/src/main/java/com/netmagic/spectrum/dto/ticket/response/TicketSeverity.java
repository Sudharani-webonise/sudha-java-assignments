package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the data of a Ticket Severity Type
 * 
 * @author webonise
 *
 */
public class TicketSeverity implements Serializable {

    private static final long serialVersionUID = -8086212511716601511L;

    @JsonProperty("severityId")
    private Long severityId;
    @JsonProperty("severityName")
    private String severityName;
    @JsonProperty("severityDateTime")
    private Timestamp severityDateTime;

    public TicketSeverity() {
        super();
    }

    public Long getSeverityId() {
        return severityId;
    }

    public void setSeverityId(Long severityId) {
        this.severityId = severityId;
    }

    public String getSeverityName() {
        return severityName;
    }

    public void setSeverityName(String severityName) {
        this.severityName = severityName;
    }

    public Timestamp getSeverityDateTime() {
        return severityDateTime;
    }

    public void setSeverityDateTime(Timestamp severityDateTime) {
        this.severityDateTime = severityDateTime;
    }
}
