package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class stores data to be send to NM server in request Body
 * 
 * @author webonise
 * 
 */
public class TicketWidgetRequest implements Serializable {

    private static final long serialVersionUID = 4697232501266251957L;

    @JsonProperty("mainCustomerId")
    private Long mainCustomerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("ticketTypes")
    private List<String> ticketTypes;
    @JsonProperty("ticketSeverityTypes")
    private List<String> ticketSeverityTypes;
    @JsonProperty("ticketTimeDelayUnits")
    private List<String> ticketTimeDelayUnits;
    @JsonProperty("ticketStates")
    private List<String> ticketStates;

    public Long getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(Long mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<String> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<String> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<String> getTicketSeverityTypes() {
        return ticketSeverityTypes;
    }

    public void setTicketSeverityTypes(List<String> ticketSeverityTypes) {
        this.ticketSeverityTypes = ticketSeverityTypes;
    }

    public List<String> getTicketTimeDelayUnits() {
        return ticketTimeDelayUnits;
    }

    public void setTicketTimeDelayUnits(List<String> ticketTimeDelayUnits) {
        this.ticketTimeDelayUnits = ticketTimeDelayUnits;
    }

    public List<String> getTicketStates() {
        return ticketStates;
    }

    public void setTicketStates(List<String> ticketStates) {
        this.ticketStates = ticketStates;
    }

}
