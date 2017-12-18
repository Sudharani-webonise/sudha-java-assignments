package com.netmagic.spectrum.apiclient;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.netmagic.spectrum.apiclient.OustandingApiAuthorization;

@RunWith(MockitoJUnitRunner.class)
public class OustandingApiAuthorizationTest {

    @InjectMocks
    private OustandingApiAuthorization oustandingApiAuthorizationSpy;

    @Test
    public void testGetAuthorization() {
        Map<String, String> result = oustandingApiAuthorizationSpy.getAuthorization();
        assertNotNull(result);
    }

}
