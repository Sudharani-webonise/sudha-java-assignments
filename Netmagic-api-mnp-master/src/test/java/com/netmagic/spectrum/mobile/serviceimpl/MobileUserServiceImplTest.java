package com.netmagic.spectrum.mobile.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.mobile.service.impl.MobileUserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ MobileUserServiceImpl.class })
public class MobileUserServiceImplTest {

    @InjectMocks
    private MobileUserServiceImpl mobileuserServiceSpy;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private MobileUserAuthResponse userAuthResponseMock;

    private static final String USER_AUTH_RESPONSE_FILE = "responses/userAuthentication/MobileUserAuthResponse.json";

    @Test
    public void testAuthenticateUserWithMockData() throws Exception {
        MobileUserAuthResponse userAuthResponseExpected = ResourceLoader.readAndGetObject(USER_AUTH_RESPONSE_FILE,
                MobileUserAuthResponse.class);
        performPostTestCall(userAuthResponseExpected);
        MobileUserAuthResponse userAuthResponseActual = callAutentcateUserMethod();
        assertNotNull("response should not be null", userAuthResponseActual);
        assertEquals("reponse with actual mocked object", userAuthResponseExpected.getUserEmail(),
                userAuthResponseActual.getUserEmail());
    }

    private UserAuthRequest getUserAuthMockData() {
        UserAuthRequest authRequest = new UserAuthRequest();
        authRequest.setUserEmail("a@a.com");
        authRequest.setUserPassword("pass");
        return authRequest;
    }

    private void performPostTestCall(MobileUserAuthResponse userAuthResponseExpected) {
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<MobileUserAuthResponse>> any()))
                .thenReturn(userAuthResponseExpected);
    }

    private MobileUserAuthResponse callAutentcateUserMethod() throws Exception {
        MobileUserAuthResponse userAuthResponseActual = Whitebox.invokeMethod(mobileuserServiceSpy, "authenticateUser",
                getUserAuthMockData());
        return userAuthResponseActual;
    }

    @Test
    public void testGetUserSplashData() throws Exception {
        UserSplashResponse userSplashDataExpected = ResourceLoader.readAndGetObject(USER_AUTH_RESPONSE_FILE,
                UserSplashResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<UserSplashResponse>> any()))
                .thenReturn(userSplashDataExpected);
        UserSplashResponse userSplashDataActual = Whitebox.invokeMethod(mobileuserServiceSpy, "getUserSplashData",
                MockData.USER_ID.getLong());
        assertNotNull("response of getSplashData should not be null", userSplashDataActual);
        assertEquals("response of getSplashData should be equal", userSplashDataExpected, userSplashDataActual);
    }

    @Test
    public void testGetUserSplashDataForNull() throws Exception {
        Long nullUserId = null;
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<UserSplashResponse>> any()))
                .thenReturn(null);
        UserSplashResponse userSplashDataActual = Whitebox.invokeMethod(mobileuserServiceSpy, "getUserSplashData",
                nullUserId);
        assertNull("response of getSplashData should not be null", userSplashDataActual);
    }
}
