package com.netmagic.spectrum.service;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface UserService {
    /**
     * This method is used to authenticate a valid user for Login
     *
     * @param userAuthRequest
     * @return {@link UserAuthResponse}
     * @throws RequestViolationException
     *             , InvalidCredentialsException, ServiceUnavailableException
     */
    UserAuthResponse authenticateUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException;

    /**
     * This method is used to get the permission for modules and their
     * functionality for a user based on the combination selected of main
     * customer and associated customer
     *
     * @param userPermissionRequest
     * @return {@link UserPermissions}
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    UserPermissions getPermissions(UserPermissionRequest userPermissionRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method is used to get user splash data.
     * 
     * @param long1
     * 
     * @return {@link UserSplashResponse}
     * @throws ServiceUnavailableException
     */
    UserSplashResponse getUserSplashData(Long userId) throws ServiceUnavailableException;

    /***
     * This method will create lead and convert it to Opportunity i.e.SignUp a
     * User.
     * 
     * @param signUpRequest
     * @return {@link SignUpResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    SignUpResponse registerUser(SignUpRequest signUpRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method is used for Validating different id's for an on-boarding
     * Customer
     * 
     * @param documentDetails
     * @return {@link String}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    String verifyUserDetails(DocumentDetails documentDetails)
            throws RequestViolationException, ServiceUnavailableException;

    /***
     * This method resets the user password
     * 
     * @param resetPasswordRequest
     * @param requestFor
     * @return ResetPasswordResponse
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method sends the request for generating an otp
     * 
     * @param otpRequest
     * @return
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    OtpResponse getOtpResponse(OtpRequest otpRequest) throws RequestViolationException, ServiceUnavailableException;

    /**
     * Method authorize the internal user
     * 
     * @param userAuthRequest
     * @return {@link InternalUserResponse}
     * @throws RequestViolationException
     * @throws InvalidCredentialsException
     * @throws ServiceUnavailableException
     */
    InternalUserResponse authenticateInternalUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException;

    /**
     * Method fetch the temporary user from cache by email Id.
     * 
     * @param userEmail
     * @return
     * @throws CacheServiceException
     */
    SignUpRequest getRegistredUser(String userEmail) throws CacheServiceException;

    /**
     * Method requests to create the use as super admin.
     * 
     * @param emailId
     * @param merchantRefNumber
     * @param superAdminRequest
     * @return {@link Boolean}
     * @throws ServiceUnavailableException
     * @throws JsonProcessingException
     */
    boolean createSuperAdmin(String emailId, String merchantRefNumber, SuperAdminRequest superAdminRequest)
            throws ServiceUnavailableException, JsonProcessingException;

    /**
     * This method checks if a user exists with the given email
     * 
     * @param emailId
     * @return {@link Boolean}
     * @throws ServiceUnavailableException
     */
    Boolean verifyExistingUser(String emailId) throws ServiceUnavailableException;

    /**
     * This method is used to send email to user when he forgets the password
     * and tries to regenerate it
     * 
     * @param emailId
     * @return {@link Boolean}
     * @throws ServiceUnavailableException
     */
    Boolean sendMail(String emailId) throws ServiceUnavailableException;

    /**
     * This method replaces the old password of new user with the new password
     * provided by him after he changed his password
     * 
     * @param resetPasswordRequest
     * @return {@link ResetPasswordResponse}
     * @throws ServiceUnavailableException
     * @throws JsonProcessingException
     */
    ResetPasswordResponse changeUserPassword(ResetPasswordRequest resetPasswordRequest)
            throws ServiceUnavailableException, JsonProcessingException;
}
