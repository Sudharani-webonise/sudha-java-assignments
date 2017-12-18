
package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the outstanding response
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "OUTSTANDINGRESPONSE2")
public class OutstandingListResponse implements Serializable {

    private static final long serialVersionUID = 7368437130451477729L;

    @XmlElement(name = "MsgData", required = true)
    private DataList outstandingListData;

    public DataList getOutstandingListData() {
        return outstandingListData;
    }

    public void setOutstandingListData(DataList outstandingListData) {
        this.outstandingListData = outstandingListData;
    }

}
