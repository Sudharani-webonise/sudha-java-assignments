package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaymentTypeTest {

    @Test
    public void testPaymentTypeShouldBe() {
        assertEquals(2, PaymentType.EBS.getPaymentId());
    }
}
