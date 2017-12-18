package com.netmagic.spectrum.dto.outstanding.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CreditLimitTrasaction {

    @XmlElement(name = "ZZ_ONLCRED_LMT", required = true)
    private OnlineCredit onlineCredit;

    public OnlineCredit getOnlineCredit() {
        return onlineCredit;
    }

    public void setOnlineCredit(OnlineCredit onlineCredit) {
        this.onlineCredit = onlineCredit;
    }
}
