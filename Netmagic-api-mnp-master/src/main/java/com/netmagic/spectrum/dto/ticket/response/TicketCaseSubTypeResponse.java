package com.netmagic.spectrum.dto.ticket.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.cache.Cacheable;

/**
 * This class stores the ticket case sub type list of a particular ticket case
 * type into the cache
 * 
 * @author webonise
 *
 */
public class TicketCaseSubTypeResponse implements Cacheable {

    private static final long serialVersionUID = 6386263872002874675L;

    private static final String OBJECT_KEY = "TICKET";

    @JsonIgnore
    private String caseTypeId;

    @JsonProperty(value = "ticketCaseSubTypes")
    private List<TicketCaseSubType> caseSubTypes;

    public TicketCaseSubTypeResponse() {
    }

    public TicketCaseSubTypeResponse(String caseTypeId) {
        super();
        this.caseTypeId = caseTypeId;
    }

    public String getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(String caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public List<TicketCaseSubType> getCaseSubTypes() {
        return caseSubTypes;
    }

    public void setCaseSubTypes(List<TicketCaseSubType> caseSubTypes) {
        this.caseSubTypes = caseSubTypes;
    }

    @JsonIgnore
    @Override
    public String getCacheKey() {
        return getCaseTypeId();
    }

    @JsonIgnore
    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }

}
