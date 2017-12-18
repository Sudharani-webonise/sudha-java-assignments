package com.netmagic.spectrum.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

/**
 * The following Entity class helps to determine User Transaction Information
 * data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "user_tran_deta_desc")
public class UserTransactionDetailsEntity implements Serializable {

    private static final long serialVersionUID = 4038792804270496071L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tran_deta_id_pk")
    @Version
    private long transactionDetailId;

    @Column(name = "othr_sour_conf_sour_id_pk")
    private long otherSourceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mas_user_tran_info_id_fk")
    private MasterUserTransactionEntity masterUserTransaction;

    @Column(name = "tran_status")
    private boolean transactionStatus;

    @Column(name = "tran_create_date_time")
    private Timestamp tansactionCreateTime;

    @Column(name = "tran_last_retrival_date_time")
    private Timestamp transactionRetrivalTime;

    @Column(name = "tran_retrive_count")
    private int transactionRetriveCount;

    @Column(name = "tran_busn_unit")
    private String businessUnit;

    public long getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(long transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public long getOtherSourceId() {
        return otherSourceId;
    }

    public void setOtherSourceId(long otherSourceId) {
        this.otherSourceId = otherSourceId;
    }

    public MasterUserTransactionEntity getMasterUserTransaction() {
        return masterUserTransaction;
    }

    @OneToOne(mappedBy = "userTransactionDetailsEntity")
    public void setMasterUserTransaction(MasterUserTransactionEntity masterUserTransaction) {
        this.masterUserTransaction = masterUserTransaction;
    }

    public boolean isTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(boolean transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Timestamp getTansactionCreateTime() {
        return tansactionCreateTime;
    }

    public void setTansactionCreateTime(Timestamp tansactionCreateTime) {
        this.tansactionCreateTime = tansactionCreateTime;
    }

    public Timestamp getTransactionRetrivalTime() {
        return transactionRetrivalTime;
    }

    public void setTransactionRetrivalTime(Timestamp transactionRetrivalTime) {
        this.transactionRetrivalTime = transactionRetrivalTime;
    }

    public int getTransactionRetriveCount() {
        return transactionRetriveCount;
    }

    public void setTransactionRetriveCount(int transactionRetriveCount) {
        this.transactionRetriveCount = transactionRetriveCount;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

}