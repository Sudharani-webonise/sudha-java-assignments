package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the Case Sub types of tickets based on CaseTypes
 * 
 * @author webonise
 *
 */
public class TicketCaseSubType implements Serializable {

    private static final long serialVersionUID = -9105301633700041323L;

    @JsonProperty("caseSubTypeId")
    private Long caseSubTypeId;
    @JsonProperty("caseSubTypeName")
    private String caseSubType;
    @JsonProperty("severity")
    private Integer severity;

    public Long getCaseSubTypeId() {
        return caseSubTypeId;
    }

    public void setCaseSubTypeId(Long caseSubTypeId) {
        this.caseSubTypeId = caseSubTypeId;
    }

    public String getCaseSubType() {
        return caseSubType;
    }

    public void setCaseSubType(String caseSubType) {
        this.caseSubType = caseSubType;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }
}
