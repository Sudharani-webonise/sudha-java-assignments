package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the transaction data for business unit API
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class BusinessUnitData implements Serializable {

    private static final long serialVersionUID = 7558751051389665122L;

    @XmlElement(name = "Transaction", required = true)
    private List<BusinessUnitTransaction> transaction;

    public List<BusinessUnitTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<BusinessUnitTransaction> transaction) {
        this.transaction = transaction;
    }

}
