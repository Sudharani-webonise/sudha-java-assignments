package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the TDS details
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TdsDetail implements Serializable {

    private static final long serialVersionUID = 6666547678762867161L;

    @XmlElement(name = "BUSINESS_UNIT", required = true)
    private String businessUnit;
    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;
    @XmlElement(name = "ITEM", required = true)
    private String tdsId;
    @XmlElement(name = "ENTRY_REASON", required = true)
    private String reasonForEntry;
    @XmlElement(name = "BAL_AMT", required = true)
    private double balanceAmount;
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

    public String getTdsId() {
        return tdsId;
    }

    public void setTdsId(String tdsId) {
        this.tdsId = tdsId;
    }

    public String getReasonForEntry() {
        return reasonForEntry;
    }

    public void setReasonForEntry(String reasonForEntry) {
        this.reasonForEntry = reasonForEntry;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

}
