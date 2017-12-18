package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the payment Request
 * 
 * @author webonise
 *
 */
public class PaymentRequest implements Serializable {

    private static final long serialVersionUID = 7930384866558831438L;

    @JsonProperty("PaymentRequest")
    private PaymentRequestDetails paymentRequestDetails;

    public PaymentRequestDetails getPaymentRequestDetails() {
        return paymentRequestDetails;
    }

    public void setPaymentRequestDetails(PaymentRequestDetails paymentRequestDetails) {
        this.paymentRequestDetails = paymentRequestDetails;
    }

}
