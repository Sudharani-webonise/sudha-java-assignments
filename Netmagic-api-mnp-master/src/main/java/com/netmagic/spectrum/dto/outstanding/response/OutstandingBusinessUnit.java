package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds the response of the business ubits available in the system
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "BUSINESS_UNIT_LIST")
public class OutstandingBusinessUnit implements Serializable {

    private static final long serialVersionUID = -5100326206110196377L;

    @XmlElement(name = "MsgData", required = true)
    private BusinessUnitData data;

    public BusinessUnitData getData() {
        return data;
    }

    public void setData(BusinessUnitData data) {
        this.data = data;
    }

}
