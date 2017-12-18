package com.netmagic.spectrum.commons;

import com.netmagic.spectrum.entity.PaymentForConfigurationEntity;

/**
 * This enum is specifies the ids stored in database for table tran_pytm_conf
 * {@link PaymentForConfigurationEntity }
 * 
 * @author webonise
 *
 */
public enum PaymentFor {

    INVOICE("1"), SOF("2"), ON_ACCOUNT("3");

    private String paymentFor;

    private PaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public long getPaymentId() {
        return Long.parseLong(paymentFor);
    }
}
