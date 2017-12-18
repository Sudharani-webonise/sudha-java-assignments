package com.netmagic.spectrum.dto.ticket.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the data of a Ticket State Type
 * 
 * @author webonise
 *
 */

public class TicketState implements Serializable {

    private static final long serialVersionUID = -9162776701986894411L;
    @JsonProperty("stateId")
    private Long stateId;
    @JsonProperty("stateName")
    private String stateName;

    public TicketState() {
        super();
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
