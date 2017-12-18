package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserPermissions;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;

@PrepareForTest(AuthUser.class)
public class AuthUserTest {

    private AuthUser authuserSpy;

    private static final String MOCK_API_KEY = "mock_api_key";

    private static final String MOCK_SECRETE_KEY = "mock_secret_key";

    private static final String USER_PERMISSIONS = "responses/userAuthentication/userPermissionValidResponse.json";

    private static final String NAME = "mock name";

    private UserAuthResponse user;

    private InternalUserResponse internalUser;

    private SecurityContext securityContextSpy;

    @Mock
    private MobileUserAuthResponse mobileUser;

    @Before
    public void setUp() {
        user = new UserAuthResponse();
        internalUser = new InternalUserResponse();
        user.setApiKey(MOCK_API_KEY);
        user.setSecretKey(MOCK_SECRETE_KEY);
        user.setUserEmail(MockData.USER_EMAIL.getString());
        user.setUserId(MockData.USER_ID.getLong());
        user.setUserName(NAME);
        user.setExistingUser(true);
        internalUser.setUserId(MockData.USER_ID.getLong());
        internalUser.setApiKey(MOCK_API_KEY);
        internalUser.setUserFirstName(NAME);
        internalUser.setUserLastName(NAME);
        internalUser.setSecretKey(MOCK_SECRETE_KEY);
        internalUser.setUserEmail(MockData.USER_EMAIL.getString());
        authuserSpy = PowerMockito.spy(new AuthUser());
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        Whitebox.invokeMethod(authuserSpy, "authenticateUser", user);
    }

    @Test
    public void testAuthenticateMobileUser() throws Exception {
        Whitebox.invokeMethod(authuserSpy, "authenticateMobileUser", mobileUser);
    }

    @Test
    public void testAuthenticateInternalUser() throws Exception {
        Whitebox.invokeMethod(authuserSpy, "authenticateInternalUser", internalUser);
    }

    @Test
    public void testUpdateAuthAuthorities() throws Exception {
        UserPermissions userPermissionRequest = ResourceLoader.readAndGetObject(USER_PERMISSIONS,
                UserPermissions.class);
        boolean status = Whitebox.invokeMethod(authuserSpy, "updateAuthAuthorities", userPermissionRequest,
                MockData.USER.getString());
        assertEquals(true, status);
    }

    @Test
    public void testUpdateAuthAuthoritiesWithNull() throws Exception {
        UserPermissions userPermissions = new UserPermissions();
        boolean status = Whitebox.invokeMethod(authuserSpy, "updateAuthAuthorities", userPermissions,
                MockData.USER.getString());
        assertEquals(false, status);
    }

    @Test
    public void testGetAuthentedApiKey() {
        mockUserData();
        assertEquals(MOCK_API_KEY, authuserSpy.getAuthenticatedApiKey());
    }

    @Test
    public void testGetAuthentedEmailId() {
        mockUserData();
        assertEquals(MockData.USER_EMAIL.getString(), authuserSpy.getAuthentedEmailId());
    }

    @Test
    public void testGetAuthentedSecretKey() {
        mockUserData();
        assertEquals(MOCK_SECRETE_KEY, authuserSpy.getAuthenticatedSecretKey());
    }

    @Test
    public void testGetAuthenticatedUserId() {
        mockUserData();
        Long expectedId = new Long(MockData.USER_ID.getLong());
        assertEquals(expectedId, authuserSpy.getAuthenticatedUserId());
    }

    private void mockUserData() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.USER.getAuthType())));
        securityContextSpy = PowerMockito.spy(new SecurityContextImpl());
        securityContextSpy.setAuthentication(new UsernamePasswordAuthenticationToken(user, user, authorities));
        SecurityContextHolder.setContext(securityContextSpy);
    }

    @Test
    public void testGetAuthentedApiKeyForInternalUser() {
        mockIntenalUserData();
        assertEquals(MOCK_API_KEY, authuserSpy.getAuthenticatedApiKey());
    }

    @Test
    public void testGetAuthentedEmailIdForInternalUser() {
        mockIntenalUserData();
        assertEquals(MockData.USER_EMAIL.getString(), authuserSpy.getAuthentedEmailId());
    }

    @Test
    public void testGetAuthentedSecretKeyForInternalUser() {
        mockIntenalUserData();
        assertEquals(MOCK_SECRETE_KEY, authuserSpy.getAuthenticatedSecretKey());
    }

    @Test
    public void testGetAuthenticatedUserIdForInternalUser() {
        mockIntenalUserData();
        assertEquals(MockData.USER_ID.getLong(), authuserSpy.getAuthenticatedUserId());
    }

    private void mockIntenalUserData() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.INTERNAL_USER.getAuthType())));
        securityContextSpy = PowerMockito.spy(new SecurityContextImpl());
        securityContextSpy
                .setAuthentication(new UsernamePasswordAuthenticationToken(internalUser, internalUser, authorities));
        SecurityContextHolder.setContext(securityContextSpy);
    }

    @Test
    public void testGetAuthentedUserFirstNameForInternalUser() {
        mockIntenalUserData();
        assertEquals(NAME, authuserSpy.getFirstName());
    }

    @Test
    public void testGetAuthentedFirstNameForUser() {
        assertEquals(NAME, authuserSpy.getFirstName());
    }

    @Test
    public void testGetAuthentedLastNameForInternalUser() {
        mockIntenalUserData();
        assertEquals(NAME, authuserSpy.getLastName());
    }

    @Test
    public void testGetAuthentedLastNameForUser() {
        assertEquals(NAME, authuserSpy.getLastName());
    }

    @Test
    public void testGetAuthentedUserIdForInternalUser() {
        mockIntenalUserData();
        assertEquals(MockData.USER_ID.getLong(), authuserSpy.getAuthenticatedUserId());
    }

    @Test
    public void testGetAuthentedUserIdForUser() {
        assertEquals(MockData.USER_ID.getLong(), authuserSpy.getAuthenticatedUserId());
    }

    @Test
    public void testIsExistingForInternalUser() {
        mockIntenalUserData();
        assertEquals(false, authuserSpy.isExistingUser());
    }

    @Test
    public void testIsExistingForUser() {
        assertEquals(true, authuserSpy.isExistingUser());
    }
}
