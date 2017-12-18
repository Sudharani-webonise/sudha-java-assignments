package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netmagic.spectrum.helpers.MockData;

public class StatusTest {

    @Test
    public void testStatusTestShouldBe() {
        assertEquals(MockData.SUCCESS.getString().toLowerCase(), Status.SUCCESS.getStatus());
    }
}
