package com.netmagic.spectrum.security;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(WebSecurityConfig.class)
public class WebSecurityConfigTest {

    @InjectMocks
    private WebSecurityConfig webSecurityConfigSpy;

    @Mock
    private ObjectPostProcessor<Object> objectPostProcessorSpy;

    @Mock
    private AuthenticationManagerBuilder authenticationBuilderSpy;

    @Mock
    private Map<Class<Object>, Object> sharedObjectsSpy;


    @Test
    public void testConfigure() throws Exception {
        webSecurityConfigSpy
                .configure(new HttpSecurity(objectPostProcessorSpy, authenticationBuilderSpy, sharedObjectsSpy));
    }
}
