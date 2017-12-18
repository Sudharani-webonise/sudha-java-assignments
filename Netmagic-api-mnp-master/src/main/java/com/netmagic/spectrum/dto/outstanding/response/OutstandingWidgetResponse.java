package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the outstanding widget response
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "OUTSTANDINGRESPONSE1")
public class OutstandingWidgetResponse implements Serializable {

    private static final long serialVersionUID = -9160061379936456030L;

    @XmlElement(name = "MsgData", required = true)
    private Data outstandingWidgetData;

    public Data getOutstandingWidgetData() {
        return outstandingWidgetData;
    }

    public void setOutstandingWidgetData(Data outstandingWidgetData) {
        this.outstandingWidgetData = outstandingWidgetData;
    }

}