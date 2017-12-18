package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author webonsie
 *
 */
public class ShopCartPayResponse implements Serializable {

    private static final long serialVersionUID = -5887708123921715805L;

    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("paymentStatus")
    private String paymentStatus;
    @JsonProperty("StatusDescr")
    private String statusDescr;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatusDescr() {
        return statusDescr;
    }

    public void setStatusDescr(String statusDescr) {
        this.statusDescr = statusDescr;
    }

}
