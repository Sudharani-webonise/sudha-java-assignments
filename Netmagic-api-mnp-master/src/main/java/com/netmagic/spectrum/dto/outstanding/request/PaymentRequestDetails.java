package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the payment details which are sent to people-soft after
 * payment is made
 * 
 * @author webonise
 *
 */
public class PaymentRequestDetails implements Serializable {

    private static final long serialVersionUID = -6524396422435679852L;

    @JsonProperty("billToCustomerId")
    private Long billToCustomerId;
    @JsonProperty("supportToCustomerId")
    private Long supportToCustomerId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("paymentId")
    private String paymentId;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("invoices")
    private List<Invoice> invoiceList;

    public Long getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(Long billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public Long getSupportToCustomerId() {
        return supportToCustomerId;
    }

    public void setSupportToCustomerId(Long supportToCustomerId) {
        this.supportToCustomerId = supportToCustomerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}
