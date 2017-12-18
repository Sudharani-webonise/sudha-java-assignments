package com.netmagic.spectrum.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RandomStringTest {

    @Test
    public void testGenerateCloudLineItemToken() {
        assertNotNull(RandomString.generateCloudToken());
    }

    @Test
    public void testGenerateSofToken() {
        assertNotNull(RandomString.generateSofToken());
    }

    @Test
    public void testGenerateMnpToken() {
        assertNotNull(RandomString.generateMnpToken());
    }

}
