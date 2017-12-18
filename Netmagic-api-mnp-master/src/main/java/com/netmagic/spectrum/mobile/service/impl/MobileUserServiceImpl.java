package com.netmagic.spectrum.mobile.service.impl;

import java.util.Collections;

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
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.User;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.mobile.service.MobileUserService;

@Service
public class MobileUserServiceImpl implements MobileUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileUserServiceImpl.class);

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    private static final String ERROR_MESSAGE = "errMsg";

    @Override
    public MobileUserAuthResponse authenticateUser(UserAuthRequest userAuthRequest)
            throws RequestViolationException, InvalidCredentialsException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, User.LOGIN.getURL());
            MobileUserAuthResponse user = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(userAuthRequest), Collections.emptyMap(), MobileUserAuthResponse.class);
            if ( !user.getAdditionalProperties().isEmpty() ) {
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
}
