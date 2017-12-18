package com.netmagic.spectrum.dto.outstanding.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class OnlineCreditMsgdata {

    @XmlElement(name = "Transaction", required = true)
    private CreditLimitTrasaction creditLimitTrasaction;

    public CreditLimitTrasaction getCreditLimitTrasaction() {
        return creditLimitTrasaction;
    }

    public void setCreditLimitTrasaction(CreditLimitTrasaction creditLimitTrasaction) {
        this.creditLimitTrasaction = creditLimitTrasaction;
    }

}
