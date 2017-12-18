package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dto.user.request.DocumentDetails;
import com.netmagic.spectrum.dto.user.request.OtpRequest;
import com.netmagic.spectrum.dto.user.request.ResetPasswordRequest;
import com.netmagic.spectrum.dto.user.request.SignUpRequest;
import com.netmagic.spectrum.dto.user.request.SuperAdminRequest;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.request.UserPermissionRequest;
import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.OtpResponse;
import com.netmagic.spectrum.dto.user.response.ResetPasswordResponse;
import com.netmagic.spectrum.dto.user.response.SignUpResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserPermissions;
import com.netmagic.spectrum.dto.user.response.UserResponseTemp;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.helpers.MockCachedData;
import com.netmagic.spectrum.helpers.MockDaoData;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ UserServiceImpl.class })
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceSpy;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private UserAuthResponse userAuthResponseMock;

    @Mock
    private SugarCRMAuthorization sugarCRMAuthorizationMock;

    @Mock
    private CacheService<UserResponseTemp> userCacheServiceMock;

    @Mock
    private MasterUserTransactionDao masterUserTransactioDao;

    private static final String USER_AUTH_RESPONSE_FILE = "responses/userAuthentication/UserAuthResponse.json";

    private static final String USER_PERMISSION_VALID_RESPONSE = "responses/userAuthentication/userPermissionValidResponse.json";

    private static final String LEAD_RESPONSE = "responses/userAuthentication/LeadResponse.json";

    private static final String SIGN_UP_REQUEST = "requests/userAuthentication/SignUpRequest.json";

    private static final String SIGN_UP_RESPONSE = "responses/userAuthentication/SignUpResponse.json";

    private static final String VALIDATE_RESPONSE = "No Data Found";

    private static final String VALIDATE_REQUEST = "requests/userAuthentication/validation.json";

    private static final String RESET_PASSWORD_REQUEST = "requests/userAuthentication/ResetPasswordRequest.json";

    private static final String RESET_PASSWORD_RESPONSE = "responses/userAuthentication/ResetPasswordResponse.json";

    private static final String RESET_PASSWORD_INVALID_RESPONSE = "responses/userAuthentication/ResetPasswordMismatch.json";

    private static final String RESET_PASSWORD_INVALID_REQUEST = "requests/userAuthentication/ResetPasswordMistmatch.json";

    private static final String GENERATE_TOKEN_REQUEST_VALID_EMAIL = "requests/userAuthentication/generateTokenValidEmail.json";

    private static final String GENERATE_TOKEN_RESPONSE_VALID_EMAIL = "responses/userAuthentication/generate TokenValidEmail.json";

    private static final String USER_AUTH_REQUEST = "requests/userAuthentication/userAuthRequest.json";

    private static final String INTERNAL_USER_AUTH_RESPONSE = "responses/userAuthentication/internalUserResponse.json";

    private static final String SUPER_ADMIN_REQUEST = "requests/userAuthentication/superAdminRequest.json";

    @Before
    public void setUp() throws Exception {
        Whitebox.setInternalState(userServiceSpy, "mapper", new ObjectMapper());
    }

    @Test
    public void testAuthenticateUserWithMockData() throws Exception {
        UserAuthRequest userAuthRequest = ResourceLoader.readAndGetObject(USER_AUTH_REQUEST, UserAuthRequest.class);
        UserAuthResponse userAuthResponseExpected = ResourceLoader.readAndGetObject(USER_AUTH_RESPONSE_FILE,
                UserAuthResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<UserAuthResponse>> any()))
                .thenReturn(userAuthResponseExpected);
        UserAuthResponse userAuthResponseActual = Whitebox.invokeMethod(userServiceSpy, "authenticateUser",
                userAuthRequest);
        assertNotNull("response should not be null", userAuthResponseActual);
        assertEquals("reponse with actual mocked object", userAuthResponseExpected.getUserEmail(),
                userAuthResponseActual.getUserEmail());
    }

    @Test
    public void testAuthenticateUserWithCachedMockData() throws Exception {
        UserAuthRequest userAuthRequest = new UserAuthRequest();
        userAuthRequest.setUserEmail("temp@user.com");
        userAuthRequest.setUserPassword("Password123!");
        UserResponseTemp responseTemp = MockCachedData.mockCachedTempUser(userCacheServiceMock);
        UserAuthResponse userAuthResponseActual = Whitebox.invokeMethod(userServiceSpy, "authenticateUser",
                userAuthRequest);
        assertNotNull("response should not be null", userAuthResponseActual);
        assertEquals("reponse with actual mocked object", responseTemp.getEmailId(),
                userAuthResponseActual.getUserEmail());
    }

    @Test
    public void testGetPermissionsWithValidRequest() throws Exception {
        UserPermissions userPermissions = ResourceLoader.readAndGetObject(USER_PERMISSION_VALID_RESPONSE,
                UserPermissions.class);
        UserPermissionRequest userPermissionRequest = new UserPermissionRequest(MockData.USER_ID.getLong(),
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_ID.getLong());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<UserPermissions>> any()))
                .thenReturn(userPermissions);
        UserPermissions permissions = Whitebox.invokeMethod(userServiceSpy, "getPermissions", userPermissionRequest);
        assertNotNull("response of getPermissions should not be null", permissions);
        assertEquals("response of getPermissions must be equal to mock data", userPermissions, permissions);
    }

    @Test
    public void testGetUserSplashData() throws Exception {
        UserSplashResponse userSplashDataExpected = ResourceLoader.readAndGetObject(USER_AUTH_RESPONSE_FILE,
                UserSplashResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<UserSplashResponse>> any()))
                .thenReturn(userSplashDataExpected);
        UserSplashResponse userSplashDataActual = Whitebox.invokeMethod(userServiceSpy, "getUserSplashData",
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
        UserSplashResponse userSplashDataActual = Whitebox.invokeMethod(userServiceSpy, "getUserSplashData",
                nullUserId);
        assertNull("response of getSplashData should not be null", userSplashDataActual);
    }

    @Test
    public void testRegisterUser() throws Exception {
        String leadResponseJson = ResourceLoader.readFile(LEAD_RESPONSE);
        String signupResponseJson = ResourceLoader.readFile(SIGN_UP_RESPONSE);
        SignUpRequest signUpRequest = ResourceLoader.readAndGetObject(SIGN_UP_REQUEST, SignUpRequest.class);
        SignUpResponse signUpResponseExpected = ResourceLoader.readAndGetObject(SIGN_UP_RESPONSE, SignUpResponse.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SignUpRequest>> any(),
                Mockito.anyString())).thenReturn(leadResponseJson).thenReturn(signupResponseJson);
        SignUpResponse signUpResponseActual = Whitebox.invokeMethod(userServiceSpy, "registerUser", signUpRequest);
        assertNotNull("Response of registerUser should not be null", signUpResponseActual);
        assertEquals("Response of registerUser should be equal", signUpResponseExpected, signUpResponseActual);
    }

    @Test
    public void testRegisterUserWithInvalidToken() throws Exception {
        String leadResponseJson = ResourceLoader.readFile(LEAD_RESPONSE);
        String signupResponseJson = ResourceLoader.readFile(SIGN_UP_RESPONSE);
        SignUpRequest signUpRequest = ResourceLoader.readAndGetObject(SIGN_UP_REQUEST, SignUpRequest.class);
        SignUpResponse signUpResponseExpected = ResourceLoader.readAndGetObject(SIGN_UP_RESPONSE, SignUpResponse.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString())
                .thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SignUpRequest>> any(),
                Mockito.anyString())).thenReturn(leadResponseJson).thenReturn(signupResponseJson);
        SignUpResponse signUpResponseActual = Whitebox.invokeMethod(userServiceSpy, "registerUser", signUpRequest);
        assertNotNull("Response of registerUser should not be null", signUpResponseActual);
        assertEquals("Response of registerUser should be equal", signUpResponseExpected, signUpResponseActual);
    }

    @Test
    public void testverifyUserDetails() throws Exception {
        String validateRequestJson = ResourceLoader.readFile(VALIDATE_REQUEST);
        DocumentDetails validationRequest = new ObjectMapper().readValue(validateRequestJson, DocumentDetails.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<DocumentDetails>> any(),
                Mockito.anyString())).thenReturn(VALIDATE_RESPONSE);
        String validResponse = Whitebox.invokeMethod(userServiceSpy, "verifyUserDetails", validationRequest);
        assertNotNull("response of verifyUserDetails should not be null", validResponse);
        assertEquals("response of verifyUserDetails must be equal to mock data", VALIDATE_RESPONSE, validResponse);
    }

    @Test
    public void testVerifyUserDetailsWithInvalidToken() throws Exception {
        DocumentDetails validationRequest = ResourceLoader.readAndGetObject(VALIDATE_REQUEST, DocumentDetails.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<DocumentDetails>> any(),
                Mockito.anyString())).thenReturn(VALIDATE_RESPONSE);
        String validResponse = Whitebox.invokeMethod(userServiceSpy, "verifyUserDetails", validationRequest);
        assertNotNull("response of verifyUserDetails should not be null", validResponse);
        assertEquals("response of verifyUserDetails must be equal to mock data", VALIDATE_RESPONSE, validResponse);
    }

    @Test
    public void testResetPassword() throws Exception {
        ResetPasswordRequest request = ResourceLoader.readAndGetObject(RESET_PASSWORD_REQUEST,
                ResetPasswordRequest.class);
        ResetPasswordResponse responseExcepted = ResourceLoader.readAndGetObject(RESET_PASSWORD_RESPONSE,
                ResetPasswordResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ResetPasswordResponse>> any()))
                .thenReturn(responseExcepted);
        ResetPasswordResponse responseActual = Whitebox.invokeMethod(userServiceSpy, "resetPassword", request);
        assertNotNull("response of resetPassword should not be null", responseActual);
        assertEquals("response of resetPassword must be equal to mock data", responseExcepted, responseActual);
    }

    @Test
    public void testResetPasswordWithInvalidData() throws Exception {
        ResetPasswordRequest request = ResourceLoader.readAndGetObject(RESET_PASSWORD_INVALID_REQUEST,
                ResetPasswordRequest.class);
        ResetPasswordResponse responseExcepted = ResourceLoader.readAndGetObject(RESET_PASSWORD_INVALID_RESPONSE,
                ResetPasswordResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ResetPasswordResponse>> any()))
                .thenReturn(responseExcepted);
        ResetPasswordResponse responseActual = Whitebox.invokeMethod(userServiceSpy, "resetPassword", request);
        assertNotNull("response of resetPassword should not be null", responseActual);
        assertEquals("response of resetPassword must be equal to mock data", responseExcepted, responseActual);
    }

    @Test
    public void testGetOtpResponsewithValidEmail() throws Exception {
        OtpRequest otpRequest = ResourceLoader.readAndGetObject(GENERATE_TOKEN_REQUEST_VALID_EMAIL, OtpRequest.class);
        OtpResponse responseExcepted = ResourceLoader.readAndGetObject(GENERATE_TOKEN_RESPONSE_VALID_EMAIL,
                OtpResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OtpResponse>> any()))
                .thenReturn(responseExcepted);
        OtpResponse responseActual = Whitebox.invokeMethod(userServiceSpy, "getOtpResponse", otpRequest);
        assertNotNull("response of getOtpResponse should not be null", responseActual);
        assertEquals("response of getOtpResponse must be equal to mock data", responseExcepted, responseActual);
    }

    @Test
    public void testGetOtpResponsewithCachedUser() throws Exception {
        OtpRequest otpRequest = ResourceLoader.readAndGetObject(GENERATE_TOKEN_REQUEST_VALID_EMAIL, OtpRequest.class);
        otpRequest.setUserEmail(MockData.TEMP_USER_EMAIL.getString());
        MockCachedData.mockCachedTempUser(userCacheServiceMock);
        Mockito.when(userServiceSpy.sendMail(MockData.TEMP_USER_EMAIL.getString())).thenReturn(true);
        OtpResponse responseActual = Whitebox.invokeMethod(userServiceSpy, "getOtpResponse", otpRequest);
        assertNotNull("response of getOtpResponse should not be null", responseActual);
    }

    @Test
    public void testGetOtpResponsewithCachedUserSendMailFalse() throws Exception {
        OtpRequest otpRequest = ResourceLoader.readAndGetObject(GENERATE_TOKEN_REQUEST_VALID_EMAIL, OtpRequest.class);
        otpRequest.setUserEmail(MockData.TEMP_USER_EMAIL.getString());
        MockCachedData.mockCachedTempUser(userCacheServiceMock);
        Mockito.when(userServiceSpy.sendMail(MockData.TEMP_USER_EMAIL.getString())).thenReturn(false);
        OtpResponse responseActual = Whitebox.invokeMethod(userServiceSpy, "getOtpResponse", otpRequest);
        assertNotNull("response of getOtpResponse should not be null", responseActual);
    }

    @Test
    public void testAuthenticateInternalUserWithMockData() throws Exception {
        UserAuthRequest internalUserAuthRequest = ResourceLoader.readAndGetObject(USER_AUTH_REQUEST,
                UserAuthRequest.class);
        InternalUserResponse userAuthResponseExpected = ResourceLoader.readAndGetObject(INTERNAL_USER_AUTH_RESPONSE,
                InternalUserResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<InternalUserResponse>> any()))
                .thenReturn(userAuthResponseExpected);
        InternalUserResponse userAuthResponseActual = Whitebox.invokeMethod(userServiceSpy, "authenticateInternalUser",
                internalUserAuthRequest);
        assertNotNull("response should not be null", userAuthResponseActual);
        assertEquals("reponse with actual mocked object", userAuthResponseExpected, userAuthResponseActual);
    }

    @Test
    public void testGetRgistedUser() throws Exception {
        UserResponseTemp responseTemp = MockCachedData.mockCachedTempUser(userCacheServiceMock);
        SignUpRequest response = Whitebox.invokeMethod(userServiceSpy, "getRegistredUser",
                MockData.USER_EMAIL.getString());
        assertNotNull("response should not be null", response);
        assertEquals("reponse with actual mocked object", responseTemp.getEmailId(), response.getEmailId());
    }

    @Test
    public void testVerifyExistingCacheUser() throws Exception {
        MockCachedData.mockCachedTempUser(userCacheServiceMock);
        Boolean status = Whitebox.invokeMethod(userServiceSpy, "verifyExistingUser",
                MockData.TEMP_USER_EMAIL.getString());
        assertTrue(status);
    }

    @Test
    public void testVerifyExistingUser() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        Boolean status = Whitebox.invokeMethod(userServiceSpy, "verifyExistingUser", MockData.USER_EMAIL.getString());
        assertTrue(status);
    }

    @Test
    public void testSendMail() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        Boolean status = Whitebox.invokeMethod(userServiceSpy, "sendMail", MockData.USER_EMAIL.getString());
        assertTrue(status);
    }

    @Test
    public void testChangeUserPassword() throws Exception {
        ResetPasswordRequest passwordRequest = ResourceLoader.readAndGetObject(RESET_PASSWORD_REQUEST,
                ResetPasswordRequest.class);
        MockCachedData.mockCachedTempUser(userCacheServiceMock);
        ResetPasswordResponse response = Whitebox.invokeMethod(userServiceSpy, "changeUserPassword", passwordRequest);
        assertNotNull("response should not be null", response);
    }

    @Test
    public void testChangeUserPasswordInvalidUser() throws Exception {
        ResetPasswordRequest passwordRequest = ResourceLoader.readAndGetObject(RESET_PASSWORD_REQUEST,
                ResetPasswordRequest.class);
        ResetPasswordResponse response = Whitebox.invokeMethod(userServiceSpy, "changeUserPassword", passwordRequest);
        assertNotNull("response should not be null", response);
    }

    @Test
    public void testCreateSuperAdminUser() throws Exception {
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        SuperAdminRequest superAdminRequest = ResourceLoader.readAndGetObject(SUPER_ADMIN_REQUEST,
                SuperAdminRequest.class);
        MasterUserTransactionEntity masterEntity = MockDaoData
                .findByMerchantRefNumberAndPaymentStatus(masterUserTransactioDao);
        Boolean status = Whitebox.invokeMethod(userServiceSpy, "createSuperAdmin", masterEntity.getUserEmail(),
                masterEntity.getMerchantRefNumber(), superAdminRequest);
        assertTrue(status);
    }

    @Test
    public void testCreateSuperAdminCachedUser() throws Exception {
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        SuperAdminRequest superAdminRequest = ResourceLoader.readAndGetObject(SUPER_ADMIN_REQUEST,
                SuperAdminRequest.class);
        MockCachedData.mockCachedTempUser(userCacheServiceMock);
        MasterUserTransactionEntity masterEntity = MockDaoData
                .findByMerchantRefNumberAndPaymentStatus(masterUserTransactioDao);
        masterEntity.setHeliosAdmin(true);
        Boolean status = Whitebox.invokeMethod(userServiceSpy, "createSuperAdmin", masterEntity.getUserEmail(),
                masterEntity.getMerchantRefNumber(), superAdminRequest);
        assertTrue(status);
    }
}
