package com.netmagic.spectrum.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Base64;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.dto.sso.response.DeleteLineItemCloudResponse;
import com.netmagic.spectrum.dto.sso.response.SSOTokenResponse;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.exception.UnauthorizedException;
import com.netmagic.spectrum.service.SSOService;
import com.netmagic.spectrum.utils.DateUtils;

@Service
public class SSOServiceImpl implements SSOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSOServiceImpl.class);

    private static final String PATH_API_KEY = "?apiKey=";

    private static final String PATH_TIME_STAMP = "&timeStamp=";

    private static final String PATH_SIGNATURE = "&signature=";

    private static final String API_KEY = "apiKey";

    private static final String VERSION = "version";

    private static final String TIME_STAMP = "timeStamp";

    private static final String HMAC_SHA_1 = "HmacSHA1";

    private static final String TOKEN_PATH_VAR = "?token=";

    @Value("${sso.apiBaseUrl}")
    private String ssoApiBaseUrl;

    @Value("${sso.version}")
    private String ssoVersion;

    @Value("${sso.authTokenUrl}")
    private String ssoAuthTokenURL;

    @Value("${classic.mnp.url}")
    private String classicCmpTokenURL;

    @Value("${cloud.shoppingCart.apiBaseUrl}")
    private String cloudShoppingCartBaseUrl;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private ApiClient apiClient;

    @Override
    public SSOTokenResponse getToken() throws UnauthorizedException {
        try {
            if ( authUser.getAuthenticatedApiKey().isEmpty() || authUser.getAuthenticatedApiKey() == null
                    || authUser.getAuthenticatedSecretKey().isEmpty()
                    || authUser.getAuthenticatedSecretKey() == null ) {
                LOGGER.error("api key / secret key is null");
                throw new InvalidKeyException("Invalid key GET request for SSO Service URI");
            }
            String timeStamp = DateUtils.getUTCFormattedTimestamp();
            String signature = getSignature(authUser.getAuthenticatedSecretKey(),
                    getSortedCombinationForSignature(timeStamp));
            String queryString = String.format(Param.EIGHT.getParam(), ssoApiBaseUrl, authUser.getAuthentedEmailId(),
                    PATH_API_KEY, authUser.getAuthenticatedApiKey(), PATH_TIME_STAMP, timeStamp, PATH_SIGNATURE,
                    signature);
            return apiClient.performGet(queryString, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,
                    Collections.emptyMap(), SSOTokenResponse.class);
        } catch (InvalidKeyException ex) {
            LOGGER.error("Invalid Key for SSO GET call", ex);
            throw new UnauthorizedException("Invalid key GET request for SSO Service URI");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Error in generating signature GET call", ex);
            throw new UnauthorizedException("unable to generate Signature for SSO");
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new UnauthorizedException("User doent not registered for SSO Service URI");
        }
    }

    @Override
    public DeleteLineItemCloudResponse deleteLineItemFromCloud(String lineItemToken) throws UnauthorizedException {
        try {
            if ( authUser.getAuthenticatedApiKey().isEmpty() || authUser.getAuthenticatedApiKey() == null
                    || authUser.getAuthenticatedSecretKey().isEmpty()
                    || authUser.getAuthenticatedSecretKey() == null ) {
                LOGGER.error("api/secret key is null");
                throw new InvalidKeyException("Invalid API/SECRET key request for Delete Line Item Service URI");
            }
            String timeStamp = DateUtils.getUTCFormattedTimestamp();
            String signature = getSignature(authUser.getAuthenticatedSecretKey(),
                    getSortedCombinationForSignature(timeStamp));
            String queryString = String.format(Param.EIGHT.getParam(), cloudShoppingCartBaseUrl, lineItemToken,
                    PATH_API_KEY, authUser.getAuthenticatedApiKey(), PATH_TIME_STAMP, timeStamp, PATH_SIGNATURE,
                    signature);
            return apiClient.performDeleteCloud(queryString, MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE, Collections.emptyMap(), DeleteLineItemCloudResponse.class);
        } catch (InvalidKeyException ex) {
            LOGGER.error("Invalid Key for SSO GET call", ex);
            throw new UnauthorizedException("Invalid key GET request for SSO Service URI");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Error in generating signature GET call", ex);
            throw new UnauthorizedException("unable to generate Signature for SSO");
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new UnauthorizedException("User is not registered for SSO Service URI");
        }
    }

    private String getSortedCombinationForSignature(String timeStamp) {
        StringBuffer requestString = new StringBuffer();
        Map<String, String> params = new HashMap<String, String>();
        params.put(API_KEY, authUser.getAuthenticatedApiKey());
        params.put(VERSION, ssoVersion);
        params.put(TIME_STAMP, timeStamp);
        params.entrySet().stream().sorted(Map.Entry.<String, String> comparingByKey())
                .forEach(param -> requestString.append(param.getKey()).append(param.getValue()));
        return requestString.toString();
    }

    @SuppressWarnings("deprecation")
    private String getSignature(String authenticatedSecretKey, String sortedParams)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String signature = computeHmac(authenticatedSecretKey, sortedParams);
        return java.net.URLEncoder.encode(signature);
    }

    private String computeHmac(String authenticatedSecretKey, String sortedParams)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA_1);
        SecretKeySpec secret = new SecretKeySpec(authenticatedSecretKey.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(sortedParams.getBytes());
        return Base64.encodeBytes(digest);
    }

    @Override
    public String getAuthenticatedSSOTokenURL(String ssoToken) throws UnauthorizedException {
        try {
            if ( ssoToken.isEmpty() || ssoToken == null ) {
                throw new ServiceUnavailableException("Invalid token generated from System");
            }
            return String.format(Param.THREE.getParam(), ssoAuthTokenURL, TOKEN_PATH_VAR, ssoToken);
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new UnauthorizedException("Invalid token generated from System SSO Service URI");
        }
    }

    @Override
    public String getclassicMnpSSOTokenURL(String ssoToken) throws UnauthorizedException {
        try {
            if ( ssoToken.isEmpty() || ssoToken == null ) {
                throw new ServiceUnavailableException("Invalid token generated from System");
            }
            return String.format(Param.THREE.getParam(), classicCmpTokenURL, TOKEN_PATH_VAR, ssoToken);
        } catch (Exception ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new UnauthorizedException("Invalid token generated from System SSO Service URI");
        }
    }

}
