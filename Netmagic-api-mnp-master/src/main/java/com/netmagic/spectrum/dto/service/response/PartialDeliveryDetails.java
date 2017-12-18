package com.netmagic.spectrum.dto.service.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the partial delivery details data of a SOF line item
 * 
 * @author webonise
 */
public class PartialDeliveryDetails implements Serializable {

    private static final long serialVersionUID = 1053828910950874209L;

    @JsonProperty("ActivatedQty")
    private Double activatedQuantity;

    @JsonProperty("Pending")
    private Double pending;

    @JsonProperty("UnderDeactivation")
    private Double underDeactivation;

    @JsonProperty("UnderCancellation")
    private Double underCancellation;

    @JsonProperty("Date")
    private String date;

    public Double getActivatedQuantity() {
        return activatedQuantity;
    }

    public void setActivatedQuantity(Double activatedQuantity) {
        this.activatedQuantity = activatedQuantity;
    }

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public Double getUnderDeactivation() {
        return underDeactivation;
    }

    public void setUnderDeactivation(Double underDeactivation) {
        this.underDeactivation = underDeactivation;
    }

    public Double getUnderCancellation() {
        return underCancellation;
    }

    public void setUnderCancellation(Double underCancellation) {
        this.underCancellation = underCancellation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
