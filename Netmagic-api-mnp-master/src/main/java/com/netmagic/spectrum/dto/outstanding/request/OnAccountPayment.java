package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores in account payment details which was used for payment
 * against an invoice
 * 
 * @author webonise
 *
 */
public class OnAccountPayment implements Serializable {

    private static final long serialVersionUID = -7822279228153210658L;

    @JsonProperty("id")
    private String onAccountId;
    @JsonProperty("amountUsed")
    private Double amountUsed;
    @JsonProperty("status")
    private String status;
    @JsonProperty("amountRemaining")
    private Double amountRemaining;

    public String getOnAccountId() {
        return onAccountId;
    }

    public void setOnAccountId(String onAccountId) {
        this.onAccountId = onAccountId;
    }

    public Double getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(Double amountUsed) {
        this.amountUsed = amountUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(Double amountRemaining) {
        this.amountRemaining = amountRemaining;
    }
}
