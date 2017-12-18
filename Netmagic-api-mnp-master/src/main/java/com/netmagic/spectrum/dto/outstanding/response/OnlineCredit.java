package com.netmagic.spectrum.dto.outstanding.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class OnlineCredit {

    @XmlElement(name = "BILL_TO_CUST_ID", required = true)
    private Long billtoCustomer;

    @XmlElement(name = "CURRENCY_CD", required = true)
    private String currency;

    @XmlElement(name = "CREDIT_AMOUNT", required = true)
    private Double onlineCreditLimit;

    public Long getBilltoCustomer() {
        return billtoCustomer;
    }

    public void setBilltoCustomer(Long billtoCustomer) {
        this.billtoCustomer = billtoCustomer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getOnlineCreditLimit() {
        return onlineCreditLimit;
    }

    public void setOnlineCreditLimit(Double onlineCreditLimit) {
        this.onlineCreditLimit = onlineCreditLimit;
    }
}
