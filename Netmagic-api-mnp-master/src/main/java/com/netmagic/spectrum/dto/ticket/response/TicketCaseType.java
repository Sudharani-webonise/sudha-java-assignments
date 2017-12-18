package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the type of tickets based on Cases
 * 
 * @author webonise
 *
 */
public class TicketCaseType implements Serializable {

    private static final long serialVersionUID = -4589139783166561816L;

    @JsonProperty("caseTypeId")
    private Long caseTypeId;
    @JsonProperty("caseTypeName")
    private String caseType;

    public Long getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(Long caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
}
