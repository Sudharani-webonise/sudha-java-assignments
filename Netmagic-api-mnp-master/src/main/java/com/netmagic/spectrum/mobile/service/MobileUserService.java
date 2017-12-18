package com.netmagic.spectrum.mobile.service;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;

import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface MobileUserService {

    /**
     * This method is used to authenticate a valid user for Login
     * 
     * @param userAuthRequest
     * @return MobileUserAuthResponse
     * @throws RequestViolationException
     * @throws InvalidCredentialsException
     * @throws ServiceUnavailableException
     */
    MobileUserAuthResponse authenticateUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException;

    /***
     * This method is used to get user splash data.
     * 
     * @param authenticatedUserId
     * @return UsersplashResponse
     * @throws ServiceUnavailableException
     */
    UserSplashResponse getUserSplashData(Long authenticatedUserId) throws ServiceUnavailableException;
}
