package com.netmagic.spectrum.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.service.AuthorisationService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ AuthorisationController.class })
public class AuthorisationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorisationService authorisationServiceSpy;

    @InjectMocks
    private AuthorisationController authorisationControllerSpy;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorisationControllerSpy).build();
    }

    @Test
    public void testGetModulesMapping() throws Exception {
        mockMvc.perform(get("/api/authorisation/modules/config")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        verify(authorisationServiceSpy, times(1)).getModulesFunctionalities();
        verifyNoMoreInteractions(authorisationServiceSpy);
    }
}
