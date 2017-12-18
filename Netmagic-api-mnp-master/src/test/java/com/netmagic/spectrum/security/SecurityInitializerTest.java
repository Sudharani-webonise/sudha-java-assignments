package com.netmagic.spectrum.security;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(SecurityInitializer.class)
public class SecurityInitializerTest {

    @InjectMocks
    private SecurityInitializer securityInitializerSpy;

    @Test
    public void testSecurityInitializer() {
        assertNotNull(securityInitializerSpy);
    }

}
