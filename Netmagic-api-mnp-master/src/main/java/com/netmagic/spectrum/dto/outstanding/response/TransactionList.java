package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the outstanding list details of outstanding list API
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TransactionList implements Serializable {

    private static final long serialVersionUID = -5325858808350508077L;

    @XmlElement(name = "ZZ_HEADER_DTL", required = true)
    private OutstandingListInfo outstandingListInfo;

    public OutstandingListInfo getOutstandingListInfo() {
        return outstandingListInfo;
    }

    public void setOutstandingListInfo(OutstandingListInfo outstandingListInfo) {
        this.outstandingListInfo = outstandingListInfo;
    }

}