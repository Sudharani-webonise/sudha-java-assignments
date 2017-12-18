package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the payment details after the payment is done
 * 
 * @author webonise
 *
 */
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = -6795746158299704909L;

    @JsonProperty("billToCustomerId")
    private Long billToCustomerId;
    @JsonProperty("paymentId")
    private String paymentId;
    @JsonProperty("paymentStatus")
    private String paymentStatus;
    @JsonProperty("accountType")
    private String accountType;

    public Long getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(Long billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
