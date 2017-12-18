package com.netmagic.spectrum.service;

import com.netmagic.spectrum.dto.sso.response.DeleteLineItemCloudResponse;
import com.netmagic.spectrum.dto.sso.response.SSOTokenResponse;
import com.netmagic.spectrum.exception.UnauthorizedException;

public interface SSOService {

    /***
     * The method return the sso token response for the user
     * 
     * @return {@link SSOTokenResponse}
     * @throws UnauthorizedException
     */
    SSOTokenResponse getToken() throws UnauthorizedException;

    /***
     * this method returns SSO URL with generated Token
     * 
     * @param string
     * @return {@link String}
     * @throws UnauthorizedException
     */
    String getAuthenticatedSSOTokenURL(String ssoToken) throws UnauthorizedException;

    /***
     * This method returns the URL for classic CMP portal by appending the token
     * 
     * @param token
     * @return {@link String}
     * @throws UnauthorizedException
     */
    String getclassicMnpSSOTokenURL(String token) throws UnauthorizedException;

    /**
     * This method requests the cloud portal to delete the product which has
     * been removed from the cart
     * 
     * @param lineItemToken
     * @return {@link DeleteLineItemCloudResponse}
     * @throws UnauthorizedException
     */
    DeleteLineItemCloudResponse deleteLineItemFromCloud(String lineItemToken) throws UnauthorizedException;

}
