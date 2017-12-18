package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class act as a wrapper to store the payment details
 * 
 * @author webonise
 *
 */
public class PaymentResponse implements Serializable {

    private static final long serialVersionUID = -2390928500131997364L;

    @JsonProperty("PaymentResponse")
    private PaymentDetails paymentDetails;

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
