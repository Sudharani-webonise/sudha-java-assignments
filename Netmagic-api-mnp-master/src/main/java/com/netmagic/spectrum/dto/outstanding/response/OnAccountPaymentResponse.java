package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnAccountPaymentResponse implements Serializable {

    private static final long serialVersionUID = 3061698709841511614L;
    @JsonProperty("TransactionID")
    private String transactionId;
    @JsonProperty("BillToCustID")
    private String mainCustomerId;
    @JsonProperty("accountType")
    private String businessUnit;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("StatusDescr")
    private String statusDescription;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(String mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
