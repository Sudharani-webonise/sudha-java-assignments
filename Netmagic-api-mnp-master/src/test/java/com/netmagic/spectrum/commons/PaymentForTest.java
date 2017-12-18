package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest(PaymentFor.class)
public class PaymentForTest {

    @Test
    public void testPaymentForShouldBe() {
        assertEquals(2, PaymentFor.SOF.getPaymentId());
    }
}
