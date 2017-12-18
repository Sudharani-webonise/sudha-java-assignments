package com.netmagic.spectrum.commons;

import com.netmagic.spectrum.entity.TransactionPaymentConfigurationEntity;

/**
 * This enum is specifies the ids stored in database for table tran_pytm_conf
 * {@link TransactionPaymentConfigurationEntity }
 * 
 * @author webonise
 *
 */
public enum PaymentType {

    CREDIT("1"), EBS("2");

    private String paymentType;

    private PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getPaymentId() {
        return Long.parseLong(paymentType);
    }
}
