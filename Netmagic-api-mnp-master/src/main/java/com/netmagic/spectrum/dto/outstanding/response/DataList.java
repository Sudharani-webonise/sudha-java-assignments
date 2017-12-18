package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class stores the transaction data for outstanding List
 * 
 * @author webonise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "transaction" })
public class DataList implements Serializable {

    private static final long serialVersionUID = -6328539810714506283L;

    @XmlElement(name = "Transaction", required = true)
    private TransactionList transaction;

    public TransactionList getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionList transaction) {
        this.transaction = transaction;
    }

}
