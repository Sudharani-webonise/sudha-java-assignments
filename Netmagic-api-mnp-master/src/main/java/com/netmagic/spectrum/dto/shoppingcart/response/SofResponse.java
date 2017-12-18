package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the response received after sof creation is successful
 * 
 * @author webonise
 *
 */
public class SofResponse implements Serializable {

    private static final long serialVersionUID = -371622031524647394L;

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private List<SofInfo> sofLists;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SofInfo> getSofLists() {
        return sofLists;
    }

    public void setSofLists(List<SofInfo> sofLists) {
        this.sofLists = sofLists;
    }
}
