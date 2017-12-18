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
 * The following Entity class helps to determine on account transaction payment
 * information data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "user_on_acnt_tran_deta_desc")
public class OnAccountTransactionDetailsEntity implements Serializable {

    private static final long serialVersionUID = -6476701753988300732L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "on_acnt_tran_id_pk")
    @Version
    private long onAccountTransactionId;

    @Column(name = "on_acnt_tran_create_date_time")
    private Timestamp onAccountTransactionCreateTime;

    @Column(name = "on_acnt_tran_status")
    private boolean onAccountTransactionStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "on_acnt_mas_user_tran_info_id_fk")
    private MasterUserTransactionEntity onAccountMasterUserTransaction;

    public long getOnAccountTransactionId() {
        return onAccountTransactionId;
    }

    public void setOnAccountTransactionId(long onAccountTransactionId) {
        this.onAccountTransactionId = onAccountTransactionId;
    }

    public Timestamp getOnAccountTransactionCreateTime() {
        return onAccountTransactionCreateTime;
    }

    public void setOnAccountTransactionCreateTime(Timestamp onAccountTransactionCreateTime) {
        this.onAccountTransactionCreateTime = onAccountTransactionCreateTime;
    }

    public boolean isOnAccountTransactionStatus() {
        return onAccountTransactionStatus;
    }

    public void setOnAccountTransactionStatus(boolean onAccountTransactionStatus) {
        this.onAccountTransactionStatus = onAccountTransactionStatus;
    }

    public MasterUserTransactionEntity getOnAccountMasterUserTransaction() {
        return onAccountMasterUserTransaction;
    }

    @OneToOne(mappedBy = "onAccountTransactionDetailsEntity")
    public void setOnAccountMasterUserTransaction(MasterUserTransactionEntity onAccountMasterUserTransaction) {
        this.onAccountMasterUserTransaction = onAccountMasterUserTransaction;
    }
}
