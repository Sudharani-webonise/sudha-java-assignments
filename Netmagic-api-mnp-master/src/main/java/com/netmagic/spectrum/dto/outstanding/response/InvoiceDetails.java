package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the invoice details for a invoice
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 2421607363227828074L;
    @XmlElement(name = "BUSINESS_UNIT", required = true)
    private String businessUnit;
    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;
    @XmlElement(name = "SHIP_TO_CUST_ID", required = true)
    private Long supportToCustomerId;
    @XmlElement(name = "ITEM", required = true)
    private String invoiceId;
    @XmlElement(name = "INVOICE_DT", required = true)
    private String invoiceDate;
    @XmlElement(name = "CONTRACT_NUM", required = true)
    private String contractNumber;
    @XmlElement(name = "PROJECTNAME", required = true)
    private String ProjectName;
    @XmlElement(name = "CHARGE_FROM_DT", required = true)
    private String chargeFromDate;
    @XmlElement(name = "CHARGE_TO_DT", required = true)
    private String chargeToDate;
    @XmlElement(name = "PO_REF", required = true)
    private String poRef;
    @XmlElement(name = "DAYS", required = true)
    private Long days;
    @XmlElement(name = "BAL_AMT", required = true)
    private double balanceAmount;
    @XmlElement(name = "ORIG_ITEM_AMT", required = true)
    private double originalAmount;
    @XmlElement(name = "DISPUTE_AMOUNT", required = true)
    private double disputeAmount;
    @XmlElement(name = "PAID_AMT", required = true)
    private double paidAmount;
    @XmlElement(name = "ENTRY_TYPE", required = true)
    private String entryType;
    @XmlElement(name = "STATUS", required = true)
    private String status;
    @XmlElement(name = "ZZ_LOW_TAX_FLG", required = true)
    private String lowTaxFlag;
    @XmlElement(name = "PERCENTAGE", required = true)
    private String tdsPercentage;
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

    public Long getSupportToCustomerId() {
        return supportToCustomerId;
    }

    public void setSupportToCustomerId(Long supportToCustomerId) {
        this.supportToCustomerId = supportToCustomerId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getChargeFromDate() {
        return chargeFromDate;
    }

    public void setChargeFromDate(String chargeFromDate) {
        this.chargeFromDate = chargeFromDate;
    }

    public String getChargeToDate() {
        return chargeToDate;
    }

    public void setChargeToDate(String chargeToDate) {
        this.chargeToDate = chargeToDate;
    }

    public String getPoRef() {
        return poRef;
    }

    public void setPoRef(String poRef) {
        this.poRef = poRef;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getDisputeAmount() {
        return disputeAmount;
    }

    public void setDisputeAmount(double disputeAmount) {
        this.disputeAmount = disputeAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLowTaxFlag() {
        return lowTaxFlag;
    }

    public void setLowTaxFlag(String lowTaxFlag) {
        this.lowTaxFlag = lowTaxFlag;
    }

    public String getTdsPercentage() {
        return tdsPercentage;
    }

    public void setTdsPercentage(String tdsPercentage) {
        this.tdsPercentage = tdsPercentage;
    }

}