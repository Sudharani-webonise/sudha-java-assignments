package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParamTest {

    private static final String TWO_PARAM = "%s%s";

    @Test
    public void testParam() {
        assertEquals(TWO_PARAM, Param.TWO.getParam());
    }

}
