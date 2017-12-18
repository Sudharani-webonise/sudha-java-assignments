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
 * The following Entity class helps to determine Payment For Configuration data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "pytm_for_conf")
public class PaymentForConfigurationEntity implements Serializable {

    private static final long serialVersionUID = -2256405553729930373L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pytm_for_id_pk")
    @Version
    private long paymentForId;

    @Column(name = "pytm_for_name")
    private String paymentForName;

    @Column(name = "pytm_for_status")
    private boolean paymentForStatus;

    @Column(name = "pytm_for_create_date_time")
    private Timestamp paymentForCreatedTime;

    public long getPaymentForId() {
        return paymentForId;
    }

    public void setPaymentForId(long paymentForId) {
        this.paymentForId = paymentForId;
    }

    public String getPaymentForName() {
        return paymentForName;
    }

    public void setPaymentForName(String paymentForName) {
        this.paymentForName = paymentForName;
    }

    public boolean isPaymentForStatus() {
        return paymentForStatus;
    }

    public void setPaymentForStatus(boolean paymentForStatus) {
        this.paymentForStatus = paymentForStatus;
    }

    public Timestamp getPaymentForCreatedTime() {
        return paymentForCreatedTime;
    }

    public void setPaymentForCreatedTime(Timestamp paymentForCreatedTime) {
        this.paymentForCreatedTime = paymentForCreatedTime;
    }

}