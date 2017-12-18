package com.netmagic.spectrum.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

/**
 * The following Entity class helps to determine Transaction Payment
 * Configuration data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "tran_pytm_conf")
public class TransactionPaymentConfigurationEntity implements Serializable {

    private static final long serialVersionUID = -2219405553729930373L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pytm_id_pk")
    @Version
    private long paymentId;

    @Column(name = "pytm_name")
    private String paymentName;

    @Column(name = "pytm_status")
    private boolean paymentStatus;

    @Column(name = "pytm_create_date_time")
    private Timestamp paymentCreatedTime;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentCreatedTime() {
        return paymentCreatedTime;
    }

    public void setPaymentCreatedTime(Timestamp paymentCreatedTime) {
        this.paymentCreatedTime = paymentCreatedTime;
    }
}
