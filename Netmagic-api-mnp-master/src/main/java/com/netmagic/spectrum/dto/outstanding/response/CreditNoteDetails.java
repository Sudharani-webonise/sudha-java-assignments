package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the credit note details which are received from outstanding
 * List API
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CreditNoteDetails implements Serializable {

    private static final long serialVersionUID = -436628528974600174L;

    @XmlElement(name = "BUSINESS_UNIT", required = true)
    private String businessUnit;
    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;
    @XmlElement(name = "ITEM", required = true)
    private String creditNoteId;
    @XmlElement(name = "SHIP_TO_CUST_ID", required = true)
    private Long supportToCustomerId;
    @XmlElement(name = "BAL_AMT", required = true)
    private Double balanceAmount;
    @XmlElement(name = "ORIG_ITEM_AMT", required = true)
    private Double originalInvoiceAmount;
    @XmlElement(name = "STATUS", required = true)
    private String status;
    @XmlElement(name = "INVOICE_DT", required = true)
    private String invoiceDate;
    @XmlElement(name = "CONTRACT_NUM", required = true)
    private Long contractNumber;
    @XmlElement(name = "PROJECTNAME", required = true)
    private String projectName;
    @XmlElement(name = "ORIGINAL_INVOICE", required = true)
    private String originalInvoice;
    @XmlAttribute(name = "class")
    protected String clazz;

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCreditNoteId() {
        return creditNoteId;
    }

    public void setCreditNoteId(String creditNoteId) {
        this.creditNoteId = creditNoteId;
    }

    public Long getSupportToCustomerId() {
        return supportToCustomerId;
    }

    public void setSupportToCustomerId(Long supportToCustomerId) {
        this.supportToCustomerId = supportToCustomerId;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getOriginalInvoiceAmount() {
        return originalInvoiceAmount;
    }

    public void setOriginalInvoiceAmount(Double originalInvoiceAmount) {
        this.originalInvoiceAmount = originalInvoiceAmount;
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

    public Long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOriginalInvoice() {
        return originalInvoice;
    }

    public void setOriginalInvoice(String originalInvoice) {
        this.originalInvoice = originalInvoice;
    }

}