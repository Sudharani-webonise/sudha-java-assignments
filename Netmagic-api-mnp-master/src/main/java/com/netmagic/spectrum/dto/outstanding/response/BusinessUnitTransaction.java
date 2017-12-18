package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * THis class stores the business unit data for outstanding widget
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class BusinessUnitTransaction implements Serializable {

    private static final long serialVersionUID = 6245926673833085512L;

    @XmlElement(name = "ZZ_BU_LIST", required = true)
    private BusinessUnit businessUnit;

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

}
