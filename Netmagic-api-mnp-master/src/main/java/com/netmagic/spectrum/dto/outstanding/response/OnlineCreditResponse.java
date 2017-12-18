package com.netmagic.spectrum.dto.outstanding.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ONLINECREDITLIMITRESPONSE1")
public class OnlineCreditResponse {

    @XmlElement(name = "MsgData", required = true)
    private OnlineCreditMsgdata onlineCreditMsgdata;

    public OnlineCreditMsgdata getOnlineCreditMsgdata() {
        return onlineCreditMsgdata;
    }

    public void setOnlineCreditMsgdata(OnlineCreditMsgdata onlineCreditMsgdata) {
        this.onlineCreditMsgdata = onlineCreditMsgdata;
    }
}
