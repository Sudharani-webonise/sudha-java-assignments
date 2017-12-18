package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the widget data for outstanding widget
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class WidgetInfo implements Serializable {

    private static final long serialVersionUID = -5915848799558068572L;

    @XmlElement(name = "CUST_ID", required = true)
    private Long customerId;
    @XmlElement(name = "CURRENCY_CD", required = true)
    private String currency;
    @XmlElement(name = "AMOUNT", required = true)
    private double totalOutstandingAmount;
    @XmlElement(name = "CR_LIMIT", required = true)
    private double creditLimit;
    @XmlElement(name = "UNBILLED_AMT", required = true)
    private double creditUnbilled;
    @XmlElement(name = "AMOUNT_1", required = true)
    private double outstandingSinceThirty;
    @XmlElement(name = "AMOUNT_2", required = true)
    private double outstandingSinceSixty;
    @XmlElement(name = "AMOUNT_3", required = true)
    private double outstandingSinceNinty;
    @XmlAttribute(name = "class")
    protected String clazz;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(double totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditUnbilled() {
        return creditUnbilled;
    }

    public void setCreditUnbilled(double creditUnbilled) {
        this.creditUnbilled = creditUnbilled;
    }

    public double getOutstandingSinceThirty() {
        return outstandingSinceThirty;
    }

    public void setOutstandingSinceThirty(double outstandingSinceThirty) {
        this.outstandingSinceThirty = outstandingSinceThirty;
    }

    public double getOutstandingSinceSixty() {
        return outstandingSinceSixty;
    }

    public void setOutstandingSinceSixty(double outstandingSinceSixty) {
        this.outstandingSinceSixty = outstandingSinceSixty;
    }

    public double getOutstandingSinceNinty() {
        return outstandingSinceNinty;
    }

    public void setOutstandingSinceNinty(double outstandingSinceNinty) {
        this.outstandingSinceNinty = outstandingSinceNinty;
    }

}
