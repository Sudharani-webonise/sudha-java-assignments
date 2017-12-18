package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EBSOnAccountPaymentReq")
public class OnAccountPaymentRequest implements Serializable {

    private static final long serialVersionUID = 885117770200153421L;

    @JsonProperty("paymentID")
    private String transactionNumber;
    @JsonProperty("TransactionID")
    private String merchantReferenceNumber;
    @JsonProperty("CustID")
    private String mainCustomerId;
    @JsonProperty("Amount")
    private Double amount;
    @JsonProperty("BusinessUnit")
    private String businessUnit;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getMerchantReferenceNumber() {
        return merchantReferenceNumber;
    }

    public void setMerchantReferenceNumber(String merchantReferenceNumber) {
        this.merchantReferenceNumber = merchantReferenceNumber;
    }

    public String getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(String mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

}
