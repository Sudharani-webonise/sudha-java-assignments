package com.netmagic.spectrum.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

/**
 * The following Entity class helps to determine invoice transaction payment
 * information data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "user_invo_tran_deta_desc")
public class InvoiceTransactionDetailsEntity implements Serializable {

    private static final long serialVersionUID = 8314515456551514194L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invo_tran_id_pk")
    @Version
    private long invoiceTransactionId;

    @ManyToOne
    @JoinColumn(name = "invo_mas_user_tran_info_id_fk")
    private MasterUserTransactionEntity invoiceMasterUserTransaction;

    @Column(name = "invo_tran_create_date_time")
    private Timestamp sofTransactionCreateTime;

    @Column(name = "invo_tran_status")
    private boolean invoiceTransactionStatus;

    @Column(name = "invo_tran_number")
    private String invoiceTransactionNumber;

    @Column(name = "invo_tran_amount")
    private double invoiceAmount;

    @Column(name = "invo_tran_tds_amount")
    private double invoiceTdsAmount;

    @Column(name = "invo_tran_Outstanding_amount")
    private double invoiceOutstandingAmount;

    public long getInvoiceTransactionId() {
        return invoiceTransactionId;
    }

    public void setInvoiceTransactionId(long invoiceTransactionId) {
        this.invoiceTransactionId = invoiceTransactionId;
    }

    public Timestamp getSofTransactionCreateTime() {
        return sofTransactionCreateTime;
    }

    public void setSofTransactionCreateTime(Timestamp sofTransactionCreateTime) {
        this.sofTransactionCreateTime = sofTransactionCreateTime;
    }

    public boolean isInvoiceTransactionStatus() {
        return invoiceTransactionStatus;
    }

    public void setInvoiceTransactionStatus(boolean invoiceTransactionStatus) {
        this.invoiceTransactionStatus = invoiceTransactionStatus;
    }

    public String getInvoiceTransactionNumber() {
        return invoiceTransactionNumber;
    }

    public void setInvoiceTransactionNumber(String invoiceTransactionNumber) {
        this.invoiceTransactionNumber = invoiceTransactionNumber;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getInvoiceTdsAmount() {
        return invoiceTdsAmount;
    }

    public void setInvoiceTdsAmount(double invoiceTdsAmount) {
        this.invoiceTdsAmount = invoiceTdsAmount;
    }

    public double getInvoiceOutstandingAmount() {
        return invoiceOutstandingAmount;
    }

    public void setInvoiceOutstandingAmount(double invoiceOutstandingAmount) {
        this.invoiceOutstandingAmount = invoiceOutstandingAmount;
    }

    public MasterUserTransactionEntity getInvoiceMasterUserTransaction() {
        return invoiceMasterUserTransaction;
    }

    public void setInvoiceMasterUserTransaction(MasterUserTransactionEntity invoiceMasterUserTransaction) {
        this.invoiceMasterUserTransaction = invoiceMasterUserTransaction;
    }

}
