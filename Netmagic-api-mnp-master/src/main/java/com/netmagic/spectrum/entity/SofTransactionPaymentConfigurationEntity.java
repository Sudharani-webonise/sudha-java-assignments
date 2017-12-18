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
 * The following Entity class helps to determine sof transaction payment
 * Configuration data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "user_sof_tran_deta_desc")
public class SofTransactionPaymentConfigurationEntity implements Serializable {

    private static final long serialVersionUID = -5989048382989880519L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sof_tran_deta_id_pk")
    @Version
    private long sofTransactionId;

    @ManyToOne
    @JoinColumn(name = "sof_mas_user_tran_info_id_fk")
    private MasterUserTransactionEntity sofMasterUserTransaction;

    @Column(name = "sof_tran_req_body")
    private String sofTransactionrRquestBody;

    @Column(name = "sof_tran_status")
    private boolean sofTransactionStatus;

    @Column(name = "sof_tran_sof_number")
    private String sofTransactionSofNumber;

    @Column(name = "sof_tran_sof_type")
    private String sofTransactionSofType;

    @Column(name = "sof_tran_create_date_time")
    private Timestamp sofTransactionCreateTime;

    @Column(name = "sof_tran_last_retrival_date_time")
    private Timestamp sofTransactionLastRetriveTime;

    @Column(name = "sof_tran_retrive_count")
    private int sofTransactionRetriveCount;

    @Column(name = "sof_tran_token_id")
    private String sofTransactionTockenId;

    public long getSofTransactionId() {
        return sofTransactionId;
    }

    public void setSofTransactionId(long sofTransactionId) {
        this.sofTransactionId = sofTransactionId;
    }

    public MasterUserTransactionEntity getSofMasterUserTransaction() {
        return sofMasterUserTransaction;
    }

    public void setSofMasterUserTransaction(MasterUserTransactionEntity sofMasterUserTransaction) {
        this.sofMasterUserTransaction = sofMasterUserTransaction;
    }

    public String getSofTransactionrRquestBody() {
        return sofTransactionrRquestBody;
    }

    public void setSofTransactionrRquestBody(String sofTransactionrRquestBody) {
        this.sofTransactionrRquestBody = sofTransactionrRquestBody;
    }

    public String getSofTransactionSofType() {
        return sofTransactionSofType;
    }

    public void setSofTransactionSofType(String sofTransactionSofType) {
        this.sofTransactionSofType = sofTransactionSofType;
    }

    public boolean getSofTransactionStatus() {
        return sofTransactionStatus;
    }

    public void setSofTransactionStatus(boolean sofTransactionStatus) {
        this.sofTransactionStatus = sofTransactionStatus;
    }

    public String getSofTransactionSofNumber() {
        return sofTransactionSofNumber;
    }

    public void setSofTransactionSofNumber(String sofTransactionSofNumber) {
        this.sofTransactionSofNumber = sofTransactionSofNumber;
    }

    public Timestamp getSofTransactionCreateTime() {
        return sofTransactionCreateTime;
    }

    public void setSofTransactionCreateTime(Timestamp sofTransactionCreateTime) {
        this.sofTransactionCreateTime = sofTransactionCreateTime;
    }

    public Timestamp getSofTransactionLastRetriveTime() {
        return sofTransactionLastRetriveTime;
    }

    public void setSofTransactionLastRetriveTime(Timestamp sofTransactionLastRetriveTime) {
        this.sofTransactionLastRetriveTime = sofTransactionLastRetriveTime;
    }

    public int getSofTransactionRetriveCount() {
        return sofTransactionRetriveCount;
    }

    public void setSofTransactionRetriveCount(int sofTransactionRetriveCount) {
        this.sofTransactionRetriveCount = sofTransactionRetriveCount;
    }

    public String getSofTransactionTockenId() {
        return sofTransactionTockenId;
    }

    public void setSofTransactionTockenId(String sofTransactionTockenId) {
        this.sofTransactionTockenId = sofTransactionTockenId;
    }

}
