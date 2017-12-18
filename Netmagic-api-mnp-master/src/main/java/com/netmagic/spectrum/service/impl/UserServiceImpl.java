package com.netmagic.spectrum.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.ResourceHelper;
import com.netmagic.spectrum.commons.Status;
import com.netmagic.spectrum.commons.url.User;
import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dto.user.request.DocumentDetails;
import com.netmagic.spectrum.dto.user.request.OtpRequest;
import com.netmagic.spectrum.dto.user.request.ResetPasswordRequest;
import com.netmagic.spectrum.dto.user.request.SignUpRequest;
import com.netmagic.spectrum.dto.user.request.SuperAdminRequest;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.request.UserPermissionRequest;
import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.LeadResponse;
import com.netmagic.spectrum.dto.user.response.OtpResponse;
import com.netmagic.spectrum.dto.user.response.ResetPasswordResponse;
import com.netmagic.spectrum.dto.user.response.SignUpResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserPermissions;
import com.netmagic.spectrum.dto.user.response.UserResponseTemp;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.PasswordExpiredException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.UserService;
import com.netmagic.spectrum.utils.DateUtils;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SugarCRMAuthorization sugarCRMAuthorization;

    @Autowired
    private CacheService<UserResponseTemp> userCacheService;

    private static final String TOKEN_EXPIRED = "Invalid Token/Session Expired";

    private static final String ERROR_MESSAGE = "errMsg";

    private static final String PASSWORD_EXPIRED = "Password Expired";

    private static final String YES = "Y";

    @Autowired
    private MasterUserTransactionDao masterUserTransactioDao;

    @Override
    public UserAuthResponse authenticateUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException {
        try {
            UserResponseTemp tempUser = new UserResponseTemp(userAuthRequest.getUserEmail());
            UserResponseTemp response = userCacheService.get(tempUser);
            if ( response != null ) {
                if ( response.getPassword().equals(userAuthRequest.getUserPassword()) ) {
                    UserAuthResponse userAuthResponse = new UserAuthResponse();
                    userAuthResponse.setUserEmail(response.getEmailId());
                    userAuthResponse.setExistingUser(false);
                    userAuthResponse.setUserName(
                            String.format(Param.TWO.getParam(), response.getFirstName(), response.getLastName()));
                    userAuthResponse.setCustomerDetails(null);
                    return userAuthResponse;
                }
            }
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.LOGIN.getURL());
            UserAuthResponse user = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(userAuthRequest), Collections.emptyMap(), UserAuthResponse.class);
            user.setExistingUser(true);
            if ( !user.getAdditionalProperties().isEmpty() ) {
                if ( (PASSWORD_EXPIRED).equalsIgnoreCase((String) user.getAdditionalProperties().get(ERROR_MESSAGE)) )
                    throw new PasswordExpiredException(user.getAdditionalProperties().get(ERROR_MESSAGE).toString());
                throw new InvalidCredentialsException(user.getAdditionalProperties().get(ERROR_MESSAGE).toString());
            }
            return user;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing user login request", ex);
            throw new RequestViolationException("Error in processing user login request");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for User Service URI");
        }
    }

    @Override
    public UserPermissions getPermissions(UserPermissionRequest userPermissionRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.PERMISSION.getURL());
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(userPermissionRequest), Collections.emptyMap(), UserPermissions.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in parsing the user permission request ", ex);
            throw new RequestViolationException("Error occured in parsing user permission request ", ex);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for User Service URI");
        }
    }

    @Override
    public UserSplashResponse getUserSplashData(Long userId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, User.SPLASH.getURL(), userId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), UserSplashResponse.class);
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("error on GET User Service URI");
        }
    }

    @Override
    public SignUpResponse registerUser(SignUpRequest signUpRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            signUpRequest.setToken(sugarCRMAuthorization.fetchServiceToken());
            String leadResponse = sugarCRMAuthorization.getSugarServiceResponse(signUpRequest,
                    User.CREATE_LEAD.getURL());
            if ( leadResponse.equalsIgnoreCase(TOKEN_EXPIRED) || leadResponse.isEmpty() ) {
                signUpRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                leadResponse = sugarCRMAuthorization.getSugarServiceResponse(signUpRequest, User.CREATE_LEAD.getURL());
            }
            LeadResponse response = mapper.readValue(leadResponse, LeadResponse.class);
            signUpRequest.setLid(response.getLid());
            signUpRequest.setSugarLid(response.getKlid());
            String opportunityResponse = sugarCRMAuthorization.getSugarServiceResponse(signUpRequest,
                    User.OPPORTUNITY_METHOD.getURL());
            if ( opportunityResponse.equalsIgnoreCase(TOKEN_EXPIRED) || opportunityResponse.isEmpty() ) {
                signUpRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                opportunityResponse = sugarCRMAuthorization.getSugarServiceResponse(signUpRequest,
                        User.OPPORTUNITY_METHOD.getURL());
            }
            String tempUserResponse = mergeJSONs(mapper.writeValueAsString(signUpRequest), opportunityResponse);
            UserResponseTemp userResponseTemp = mapper.readValue(tempUserResponse, UserResponseTemp.class);
            userCacheService.save(userResponseTemp, 30, TimeUnit.DAYS);
            return mapper.readValue(opportunityResponse, SignUpResponse.class);
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error getting response from register User API ", ex);
            throw new RequestViolationException("Error in register User API");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for register User URI");
        }
    }

    @SuppressWarnings("unchecked")
    private String mergeJSONs(String original, String appender) throws IOException {
        Map<String, Object> originalMap = mapper.readValue(original, Map.class);
        Map<String, Object> appenderMap = mapper.readValue(appender, Map.class);
        Map<String, Object> combinedMap = new HashMap<String, Object>(appenderMap);
        combinedMap.putAll(originalMap);
        return mapper.writeValueAsString(combinedMap);
    }

    @Override
    public String verifyUserDetails(DocumentDetails documentDetails)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            documentDetails.setToken(sugarCRMAuthorization.fetchServiceToken());
            String validationResult = sugarCRMAuthorization.getSugarServiceResponse(documentDetails,
                    User.VALIDATE_METHOD.getURL());
            if ( validationResult.equalsIgnoreCase(TOKEN_EXPIRED) ) {
                documentDetails.setToken(sugarCRMAuthorization.generateServiceToken());
            }
            return sugarCRMAuthorization.getSugarServiceResponse(documentDetails, User.VALIDATE_METHOD.getURL());
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error getting response from Validation API ", ex);
            throw new RequestViolationException("Error in getting response from Validation API");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Validation URI");
        }
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.RESET_PASSWORD.getURL());
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(resetPasswordRequest), Collections.emptyMap(),
                    ResetPasswordResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing Reset Password request", ex);
            throw new RequestViolationException("Error in processing Reset Password request");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Reset Password URI");
        }
    }

    @Override
    public OtpResponse getOtpResponse(OtpRequest otpRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            UserResponseTemp tempUser = new UserResponseTemp(otpRequest.getUserEmail());
            UserResponseTemp response = userCacheService.get(tempUser);
            if ( response != null ) {
                if ( sendMail(otpRequest.getUserEmail()) ) {
                    OtpResponse otpResponse = new OtpResponse();
                    otpResponse.setUserEmail(response.getEmailId());
                    otpResponse.setResponse("success");
                    return otpResponse;
                } else {
                    OtpResponse otpResponse = new OtpResponse();
                    otpResponse.setAdditionalProperties("errCode", "5006");
                    return otpResponse;
                }
            }
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.GENERATE_OTP.getURL());
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(otpRequest), Collections.emptyMap(), OtpResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing generate token request", ex);
            throw new RequestViolationException("Error in processing generate token request");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for generate token URI");
        }
    }

    @Override
    public InternalUserResponse authenticateInternalUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.INTERNAL_LOGIN.getURL());
            InternalUserResponse user = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(userAuthRequest), Collections.emptyMap(), InternalUserResponse.class);
            if ( !user.getAdditionalProperties().isEmpty() ) {
                throw new InvalidCredentialsException(user.getAdditionalProperties().get(ERROR_MESSAGE).toString());
            }
            user.setIsInternalUser(YES);
            return user;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing internal user login request", ex);
            throw new RequestViolationException("Error in processing internal user login request");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Internal User Login URI");
        }
    }

    @Override
    public SignUpRequest getRegistredUser(String userEmail) throws CacheServiceException {
        try {
            UserResponseTemp userResponseTemp = new UserResponseTemp(userEmail);
            return userCacheService.get(userResponseTemp);
        } catch (Exception ex) {
            LOGGER.error("invalid user in cache", ex);
            throw new CacheServiceException("invalid user");
        }
    }

    @Override
    public boolean createSuperAdmin(String emailId, String merchantRefNumber, SuperAdminRequest superAdminRequest)
            throws ServiceUnavailableException, JsonProcessingException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.SUPER_ADMIN.getURL());
            boolean adminStatus = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(superAdminRequest), Collections.emptyMap(), Boolean.class);
            MasterUserTransactionEntity masterUser = masterUserTransactioDao
                    .findByMerchantRefNumberAndPaymentStatus(merchantRefNumber, true);
            masterUser.setSuperAdminRquestBody(ResourceHelper.objectAsJsonString(superAdminRequest));
            UserResponseTemp tempUser = new UserResponseTemp(emailId);
            UserResponseTemp tempUserResponse = userCacheService.get(tempUser);
            if ( adminStatus ) {
                if ( tempUserResponse != null ) {
                    if ( tempUserResponse.getPassword().equals(superAdminRequest.getPassword()) ) {
                        userCacheService.delete(tempUser);
                    } else {
                        throw new ServiceUnavailableException("invalid password please check.");
                    }
                }
                masterUser.setHeliosAdmin(true);
                masterUser.setUserTransactionHeliosAdminCreatedTime(DateUtils.getTimestamp());
            }
            masterUserTransactioDao.save(masterUser);
            userCacheService.delete(tempUserResponse);
            return adminStatus;
        } catch (Exception ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Internal User Login URI");
        }
    }

    @Override
    public Boolean verifyExistingUser(String emailId) throws ServiceUnavailableException {
        try {
            UserResponseTemp tempUser = new UserResponseTemp(emailId);
            UserResponseTemp response = userCacheService.get(tempUser);
            if ( response != null ) {
                return true;
            }
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, User.VERIFY_EXISTING_USER.getURL(),
                    emailId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), Boolean.class);
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("error on GET User Service URI");
        }

    }

    @Override
    public Boolean sendMail(String emailId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, User.SEND_MAIL.getURL(), emailId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), Boolean.class);
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("error on GET User Service URI");
        }
    }

    @Override
    public ResetPasswordResponse changeUserPassword(ResetPasswordRequest resetPasswordRequest)
            throws ServiceUnavailableException {
        try {
            UserResponseTemp tempUser = new UserResponseTemp(resetPasswordRequest.getUserEmail());
            UserResponseTemp response = userCacheService.get(tempUser);
            ResetPasswordResponse passwordResponse = new ResetPasswordResponse();
            if ( response != null ) {
                response.setPassword(resetPasswordRequest.getNewPassword());
                response.setConfirmPassword(resetPasswordRequest.getConfirmPassword());
                userCacheService.save(response);
                passwordResponse.setStatus(Status.SUCCESS.getStatus());
                return passwordResponse;
            }
            passwordResponse.setStatus(Status.FAILURE.getStatus());
            return passwordResponse;
        } catch (CacheServiceException | ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Reset Password URI");
        }
    }
}
