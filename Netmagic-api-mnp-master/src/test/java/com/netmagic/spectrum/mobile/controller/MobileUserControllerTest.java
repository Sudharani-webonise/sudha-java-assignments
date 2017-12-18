package com.netmagic.spectrum.mobile.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.mobile.service.MobileUserService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ MobileUserController.class })
public class MobileUserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MobileUserService moblieUserServiceSpy;

    @InjectMocks
    private MobileUserController moblieUserControllerSpy;

    @Mock
    private AuthUser authUserSpy;

    @Mock
    private HttpSession httpSessionSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(moblieUserControllerSpy).build();
    }

    @Test
    public void testLogin() throws Exception {
        MobileUserAuthResponse user = new MobileUserAuthResponse();
        Mockito.when(moblieUserServiceSpy.authenticateUser(Mockito.any(UserAuthRequest.class)))
                .thenReturn(user);
        mockMvc.perform(post("/mobile/api/user/login")
                .content(ResourceLoader.asJsonString(new UserAuthRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testFetchMobileSplashData() throws Exception {
        mockMvc.perform(get("/mobile/api/user/splash/data").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
