package com.netmagic.spectrum.controller;

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
import com.netmagic.spectrum.dto.user.request.DocumentDetails;
import com.netmagic.spectrum.dto.user.request.OtpRequest;
import com.netmagic.spectrum.dto.user.request.ResetPasswordRequest;
import com.netmagic.spectrum.dto.user.request.SignUpRequest;
import com.netmagic.spectrum.dto.user.request.SuperAdminRequest;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ UserController.class })
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userServiceSpy;

    @InjectMocks
    private UserController userControllerSpy;

    @Mock
    private AuthUser authUserSpy;

    @Mock
    private HttpSession httpSessionSpy;

    private static final String INTERNAL_USER_AUTH_RESPONSE = "responses/userAuthentication/internalUserResponse.json";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userControllerSpy).build();
    }

    @Test
    public void testLogin() throws Exception {
        UserAuthResponse user = new UserAuthResponse();
        Mockito.when(userServiceSpy.authenticateUser(Mockito.any(UserAuthRequest.class))).thenReturn(user);
        mockMvc.perform(post("/api/user/login").content(ResourceLoader.asJsonString(new UserAuthRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testInternalLogin() throws Exception {
        InternalUserResponse userAuthResponseExpected = ResourceLoader.readAndGetObject(INTERNAL_USER_AUTH_RESPONSE,
                InternalUserResponse.class);
        Mockito.when(userServiceSpy.authenticateInternalUser(Mockito.any(UserAuthRequest.class)))
                .thenReturn(userAuthResponseExpected);
        mockMvc.perform(post("/api/user/internal/login").content(ResourceLoader.asJsonString(new UserAuthRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testFetchPermissions() throws Exception {
        mockMvc.perform(get("/api/user/permissions").contentType(MediaType.APPLICATION_JSON)
                .param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())).andExpect(status().isOk());
    }

    @Test
    public void testFetchSplashData() throws Exception {
        mockMvc.perform(get("/api/user/splash/data").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        mockMvc.perform(post("/api/user/signup").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new SignUpRequest()))).andExpect(status().isOk());
    }

    @Test
    public void testVerifyDocuments() throws Exception {
        mockMvc.perform(post("/api/user/verify").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new DocumentDetails()))).andExpect(status().isOk());
    }

    @Test
    public void testRestPassword() throws Exception {
        mockMvc.perform(post("/api/user/generate/token").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new OtpRequest()))).andExpect(status().isOk());
    }

    @Test
    public void testRestPasswordAndUpdate() throws Exception {
        mockMvc.perform(post("/api/user/password/update").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new ResetPasswordRequest()))).andExpect(status().isOk());
    }

    @Test
    public void testGetRegistredUser() throws Exception {
        mockMvc.perform(get("/api/user/new/user/details").contentType(MediaType.APPLICATION_JSON).param("userEmail",
                MockData.USER_EMAIL.getString())).andExpect(status().isOk());
    }

    @Test
    public void testRequesForSuperAdmin() throws Exception {
        mockMvc.perform(post("/api/user/create/super/admin").contentType(MediaType.APPLICATION_JSON)
                .param("emailId", MockData.USER_EMAIL.getString())
                .param("merchantRefNumber", MockData.REFERENCE_NUMBER.getString())
                .content(ResourceLoader.asJsonString(new SuperAdminRequest()))).andExpect(status().isOk());
    }

    @Test
    public void testVarifyExistingUser() throws Exception {
        mockMvc.perform(get("/api/user/verify/existing").contentType(MediaType.APPLICATION_JSON).param("emailId",
                MockData.USER_EMAIL.getString())).andExpect(status().isOk());
    }

    @Test
    public void testRequestToResetForTemUser() throws Exception {
        mockMvc.perform(post("/api/user/password/update/tempUser").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new ResetPasswordRequest()))).andExpect(status().isOk());
    }

}
