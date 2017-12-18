package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServiceStatusTest {

    private static final String ACTIVE = "Active";

    @Test
    public void testServiceStatusShouldBe() {
        assertEquals(ACTIVE, ServiceStatus.ACTIVE.getStatus());
    }
}
