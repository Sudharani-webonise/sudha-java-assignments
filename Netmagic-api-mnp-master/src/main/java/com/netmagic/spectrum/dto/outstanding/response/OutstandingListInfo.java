package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the outstanding list data with the list of invoices,credit
 * notes, on account payment and tds
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class OutstandingListInfo implements Serializable {

    private static final long serialVersionUID = -3889779299311559964L;

    private static final Long DEFAULT_TDS = 10L;

    private Long defaultTdsValue = DEFAULT_TDS;

    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;

    @XmlElement(name = "CURRENCY_CD", required = true)
    private String currencyType;

    @XmlElement(name = "TOTAL_INVOICED_AMT", required = true)
    private double openInvoicesAmount;

    @XmlElement(name = "TOTAL_CREDIT_AMT", required = true)
    private double creditNoteTotal;

    @XmlElement(name = "TOTAL_AMT", required = true)
    private double onAccountPaymentTotal;

    @XmlElement(name = "ZZ_CREDIT_DTL")
    private List<CreditNoteDetails> creditNoteList;

    @XmlElement(name = "ZZ_ONACC_DTL")
    private List<OnAccountPaymentDetails> onAccountPaymentList;

    @XmlElement(name = "ZZ_INVOICE_DTL")
    private List<InvoiceDetails> invoiceList;

    @XmlElement(name = "ZZ_TDS_DTL")
    private List<TdsDetail> tdsList;

    @XmlAttribute(name = "class")
    protected String clazz;

    public Long getDefaultTdsValue() {
        return defaultTdsValue;
    }

    public void setDefaultTdsValue(Long defaultTdsValue) {
        this.defaultTdsValue = defaultTdsValue;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public double getOpenInvoicesAmount() {
        return openInvoicesAmount;
    }

    public void setOpenInvoicesAmount(double openInvoicesAmount) {
        this.openInvoicesAmount = openInvoicesAmount;
    }

    public double getCreditNoteTotal() {
        return creditNoteTotal;
    }

    public void setCreditNoteTotal(double creditNoteTotal) {
        this.creditNoteTotal = creditNoteTotal;
    }

    public double getOnAccountPaymentTotal() {
        return onAccountPaymentTotal;
    }

    public void setOnAccountPaymentTotal(double onAccountPaymentTotal) {
        this.onAccountPaymentTotal = onAccountPaymentTotal;
    }

    public List<CreditNoteDetails> getCreditNoteList() {
        return creditNoteList;
    }

    public void setCreditNoteList(List<CreditNoteDetails> creditNoteList) {
        this.creditNoteList = creditNoteList;
    }

    public List<OnAccountPaymentDetails> getOnAccountPaymentList() {
        return onAccountPaymentList;
    }

    public void setOnAccountPaymentList(List<OnAccountPaymentDetails> onAccountPaymentList) {
        this.onAccountPaymentList = onAccountPaymentList;
    }

    public List<InvoiceDetails> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceDetails> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<TdsDetail> getTdsList() {
        return tdsList;
    }

    public void setTdsList(List<TdsDetail> tdsList) {
        this.tdsList = tdsList;
    }

}
