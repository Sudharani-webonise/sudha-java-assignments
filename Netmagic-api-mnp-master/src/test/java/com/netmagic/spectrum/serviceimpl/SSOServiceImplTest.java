package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.AuthType;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.sso.response.DeleteLineItemCloudResponse;
import com.netmagic.spectrum.dto.sso.response.SSOTokenResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.SSOServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ SSOServiceImpl.class })
public class SSOServiceImplTest {

    private static final String GET_TOKEN_VALID_RESPONSE = "responses/sso/GetTokenResponse.json";

    private static final String MOCK_SECRETE_KEY = "mock_secret_key";

    private static final String MOCK_API_KEY = "mock_api_key";

    private static final String DELETE_LINE_ITEM_RESPONSE = "responses/sso/deleteLineItemResponse.json";

    private SSOServiceImpl ssoServiceSpy;

    private UserAuthResponse user;

    private SecurityContext securityContextSpy;

    private AuthUser authUserMock;

    @Mock
    private ApiClient apiClientMock;

    @Before
    public void setUp() throws Exception {
        ssoServiceSpy = PowerMockito.spy(new SSOServiceImpl());
        user = new UserAuthResponse();
        user.setSecretKey(MOCK_SECRETE_KEY);
        user.setApiKey(MOCK_API_KEY);
        user.setUserId(MockData.USER_ID.getLong());
        authUserMock = PowerMockito.spy(new AuthUser());
        Whitebox.setInternalState(ssoServiceSpy, "apiClient", apiClientMock);
        Whitebox.setInternalState(ssoServiceSpy, "authUser", authUserMock);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.USER.getAuthType())));
        securityContextSpy = PowerMockito.spy(new SecurityContextImpl());
        securityContextSpy.setAuthentication(new UsernamePasswordAuthenticationToken(user, user, authorities));
        SecurityContextHolder.setContext(securityContextSpy);
    }

    @Test
    public void testGetToken() throws Exception {
        SSOTokenResponse ssoTokenResponse = ResourceLoader.readAndGetObject(GET_TOKEN_VALID_RESPONSE,
                new TypeReference<SSOTokenResponse>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<SSOTokenResponse>> any()))
                .thenReturn(ssoTokenResponse);
        SSOTokenResponse tokenResponse = Whitebox.invokeMethod(ssoServiceSpy, "getToken");
        assertNotNull("response of getTokenshould not be null", tokenResponse);
        assertEquals("response of getToken must be equal to mock data", ssoTokenResponse, tokenResponse);
    }

    @Test(expected = Exception.class)
    public void testGetTokenforNullApiKeyAndSecreteKey() throws Exception {
        user.setApiKey(null);
        user.setSecretKey(null);
        Whitebox.invokeMethod(ssoServiceSpy, "getToken");
    }

    @Test
    public void testAuthenticateSSOToken() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(Mockito.anyString());
        String tokenResponse = Whitebox.invokeMethod(ssoServiceSpy, "getAuthenticatedSSOTokenURL",
                MockData.TOKEN.toString());
        assertNotNull("response of getTicketSubCaseTypes should not be null", tokenResponse);
    }

    @Test(expected = Exception.class)
    public void testAuthenticateSSOTokenEmptyToken() throws Exception {
        Whitebox.invokeMethod(ssoServiceSpy, "getAuthenticatedSSOTokenURL", "");
    }

    @Test
    public void testGetclassicMnpSSOTokenURL() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(Mockito.anyString());
        String tokenResponse = Whitebox.invokeMethod(ssoServiceSpy, "getclassicMnpSSOTokenURL",
                MockData.TOKEN.toString());
        assertNotNull("response of getTicketSubCaseTypes should not be null", tokenResponse);
    }

    @Test(expected = Exception.class)
    public void testGetclassicMnpSSOTokenURLEmptyToken() throws Exception {
        Whitebox.invokeMethod(ssoServiceSpy, "getclassicMnpSSOTokenURL", "");
    }

    @Test
    public void testDeleteLineItemFromCloud() throws Exception {
        DeleteLineItemCloudResponse deleteLineItemCloudResponse = ResourceLoader
                .readAndGetObject(DELETE_LINE_ITEM_RESPONSE, DeleteLineItemCloudResponse.class);
        Mockito.when(apiClientMock.performDeleteCloud(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<DeleteLineItemCloudResponse>> any()))
                .thenReturn(deleteLineItemCloudResponse);
        DeleteLineItemCloudResponse tokenResponse = Whitebox.invokeMethod(ssoServiceSpy, "deleteLineItemFromCloud",
                MockData.LINE_ITEM_NUMBER.getString());
        assertNotNull("response of deleteLineItemFromCloud not be null", tokenResponse);
        assertEquals("response of deleteLineItemFromCloud must be equal to mock data", deleteLineItemCloudResponse,
                tokenResponse);
    }
}
