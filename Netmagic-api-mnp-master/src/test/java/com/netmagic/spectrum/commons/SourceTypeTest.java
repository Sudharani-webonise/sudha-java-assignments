package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SourceTypeTest {

    @Test
    public void testSourceTypeShouldBe() {
        assertEquals(1, SourceType.PEOPLESOFT.getSourceId());
    }
}