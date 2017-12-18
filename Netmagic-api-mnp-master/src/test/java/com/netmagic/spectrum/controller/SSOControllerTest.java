package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.dto.sso.response.SSOTokenResponse;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.SSOService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(SSOController.class)
public class SSOControllerTest {

    private MockMvc mockData;

    @InjectMocks
    private SSOController ssoControllerSpy;

    @Mock
    private SSOService ssoServiceSpy;

    private static final String GET_TOKEN_RESPONSE = "responses/sso/GetTokenResponse.json";

    @Before
    public void setUp() throws IOException {
        String ssoToken = ResourceLoader.readFile(GET_TOKEN_RESPONSE);
        SSOTokenResponse ssoTokenResponse = new ObjectMapper().readValue(ssoToken,
                new TypeReference<SSOTokenResponse>() {
                });
        mockData = MockMvcBuilders.standaloneSetup(ssoControllerSpy).build();
        Mockito.when(ssoServiceSpy.getToken()).thenReturn(ssoTokenResponse);
    }

    @Test
    public void testGetSSOToken() throws Exception {
        mockData.perform(get("/api/sso/token/url")).andExpect(status().isOk());
    }

    @Test
    public void testGetMnpSSOToken() throws Exception {
        mockData.perform(get("/api/sso/classicMnp/token/url")).andExpect(status().isOk());
    }

}
