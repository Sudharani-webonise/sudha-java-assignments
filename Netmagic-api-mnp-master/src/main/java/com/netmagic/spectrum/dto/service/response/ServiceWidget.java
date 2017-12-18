package com.netmagic.spectrum.dto.service.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class models the widget data for service ledger lodule on dashboard
 * 
 * @author webonise
 *
 */
public class ServiceWidget implements Serializable {

    private static final long serialVersionUID = 6427299028774855566L;

    @JsonProperty("totalService")
    private Double totalServiceCount;
    @JsonProperty("servicesToBeRenewed")
    private Double toBeRenewedCount;
    @JsonProperty("servicesToBeDelivered")
    private Double toBeDeliveredCount;
    @JsonProperty("servicesActive")
    private Double activeCount;
    @JsonProperty("servicesUnderCancellation")
    private Double underCancellationCount;
    @JsonProperty("servicesUnderDeactivation")
    private Double underDeactivationCount;

    public Double getTotalServiceCount() {
        return totalServiceCount;
    }

    public void setTotalServiceCount(Double totalServiceCount) {
        this.totalServiceCount = totalServiceCount;
    }

    public Double getToBeRenewedCount() {
        return toBeRenewedCount;
    }

    public void setToBeRenewedCount(Double toBeRenewedCount) {
        this.toBeRenewedCount = toBeRenewedCount;
    }

    public Double getToBeDeliveredCount() {
        return toBeDeliveredCount;
    }

    public void setToBeDeliveredCount(Double toBeDeliveredCount) {
        this.toBeDeliveredCount = toBeDeliveredCount;
    }

    public Double getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Double activeCount) {
        this.activeCount = activeCount;
    }

    public Double getUnderCancellationCount() {
        return underCancellationCount;
    }

    public void setUnderCancellationCount(Double underCancellationCount) {
        this.underCancellationCount = underCancellationCount;
    }

    public Double getUnderDeactivationCount() {
        return underDeactivationCount;
    }

    public void setUnderDeactivationCount(Double underDeactivationCount) {
        this.underDeactivationCount = underDeactivationCount;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
