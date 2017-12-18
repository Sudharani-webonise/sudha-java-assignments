package com.netmagic.spectrum.dto.outstanding.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores information of the invoice for which payment was made
 * 
 * @author webonise
 *
 */
public class Invoice {
    @JsonProperty("invoiceId")
    private String invoiceNumber;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("tdsAmount")
    private Double tdsAmount;
    @JsonProperty("amountOutstanding")
    private Double amountOutstanding;
    @JsonProperty("creditNoteList")
    private List<CreditNote> creditNoteList;
    @JsonProperty("onAccountPaymentList")
    private List<OnAccountPayment> onAccountPaymentList;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(Double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public Double getAmountOutstanding() {
        return amountOutstanding;
    }

    public void setAmountOutstanding(Double amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    public List<CreditNote> getCreditNoteList() {
        return creditNoteList;
    }

    public void setCreditNoteList(List<CreditNote> creditNoteList) {
        this.creditNoteList = creditNoteList;
    }

    public List<OnAccountPayment> getOnAccountPaymentList() {
        return onAccountPaymentList;
    }

    public void setOnAccountPaymentList(List<OnAccountPayment> onAccountPaymentList) {
        this.onAccountPaymentList = onAccountPaymentList;
    }
}
