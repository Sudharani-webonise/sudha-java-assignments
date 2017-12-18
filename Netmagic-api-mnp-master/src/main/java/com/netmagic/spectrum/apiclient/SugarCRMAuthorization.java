package com.netmagic.spectrum.apiclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.dto.service.request.AccessTokenRestData;
import com.netmagic.spectrum.dto.service.request.Token;
import com.netmagic.spectrum.exception.InvalidAccessException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

/**
 * This class is used to create generic requests to SugarCRM and authorization
 * 
 * @author webonise
 *
 */
@Component
public class SugarCRMAuthorization {

    private static final Logger LOGGER = LoggerFactory.getLogger(SugarCRMAuthorization.class);

    private static final String INVALID_ACCESS = "Invalid Access";

    private static final String METHOD = "?method=";

    private static final String USER_AUTHENTICATION = "userAuthentication";

    @Value("${sugar.web.base.url}")
    private String sugarCrmBaseUrl;

    @Value("${service.user}")
    private String sugarUser;

    @Value("${service.authKey}")
    private String authenticationKey;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private CacheService<Token> tokenCacheService;

    public String fetchServiceToken()
            throws RequestViolationException, InvalidAccessException, JsonProcessingException {
        Token token = tokenCacheService.get(new Token());
        return token == null ? generateServiceToken() : token.getToken();
    }

    public String generateServiceToken()
            throws RequestViolationException, InvalidAccessException, JsonProcessingException {
        AccessTokenRestData restData = new AccessTokenRestData(sugarUser, authenticationKey);
        String token = getSugarServiceResponse(restData, USER_AUTHENTICATION);
        if ( token.equalsIgnoreCase(INVALID_ACCESS) || token.isEmpty() ) {
            throw new InvalidAccessException("Either the auth key was invalid or a null token is generated");
        }
        tokenCacheService.save(new Token(token));
        return token;
    }

    public <T> String getSugarServiceResponse(T resquestData, String method)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            List<T> wrappedList = new ArrayList<T>();
            wrappedList.add(resquestData);
            String url = String.format(Param.THREE.getParam(), sugarCrmBaseUrl, METHOD, method);
            String result = apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(wrappedList), Collections.emptyMap(), String.class);
            return result;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Shopping Cart URI");
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing Json service response", ex);
            throw new RequestViolationException("Error in processing Json service response");
        }

    }
}
