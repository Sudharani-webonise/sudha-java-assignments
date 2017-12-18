package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the on account payment details for a on account payment
 * made towards a business unit
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class OnAccountPaymentDetails implements Serializable {

    private static final long serialVersionUID = 3703238170083492392L;
    @XmlElement(name = "BUSINESS_UNIT", required = true)
    private String businessunit;
    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;
    @XmlElement(name = "ITEM", required = true)
    private String onAccountPaymentId;
    @XmlElement(name = "BAL_AMT", required = true)
    private double balanceAmount;
    @XmlElement(name = "ORIG_ITEM_AMT", required = true)
    private Long originalAmount;
    @XmlElement(name = "STATUS", required = true)
    private String status;
    @XmlElement(name = "INVOICE_DT", required = true)
    private String invoiceDate;
    @XmlAttribute(name = "DESCR100", required = true)
    private String description;
    @XmlAttribute(name = "class")
    protected String clazz;

    public String getBusinessunit() {
        return businessunit;
    }

    public void setBusinessunit(String businessunit) {
        this.businessunit = businessunit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOnAccountPaymentId() {
        return onAccountPaymentId;
    }

    public void setOnAccountPaymentId(String onAccountPaymentId) {
        this.onAccountPaymentId = onAccountPaymentId;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Long originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
