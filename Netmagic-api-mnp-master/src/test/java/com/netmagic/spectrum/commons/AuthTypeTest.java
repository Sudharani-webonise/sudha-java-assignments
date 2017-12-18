package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netmagic.spectrum.helpers.MockData;

public class AuthTypeTest {

    @Test
    public void testAuthTypeShouldBe() {
        assertEquals(MockData.USER.getString(), AuthType.USER.getAuthType());
    }

}
