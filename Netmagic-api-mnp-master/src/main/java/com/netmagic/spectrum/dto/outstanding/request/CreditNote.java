package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the credit note information whch was used for payment
 * against a invoice
 * 
 * @author webonise
 *
 */
public class CreditNote implements Serializable {

    private static final long serialVersionUID = -1769206083436534614L;

    @JsonProperty("id")
    private String creditNoteId;
    @JsonProperty("amountUsed")
    private Double amountUsed;
    @JsonProperty("status")
    private String status;
    @JsonProperty("amountRemaining")
    private Double amountRemaining;

    public String getCreditNoteId() {
        return creditNoteId;
    }

    public void setCreditNoteId(String creditNoteId) {
        this.creditNoteId = creditNoteId;
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
