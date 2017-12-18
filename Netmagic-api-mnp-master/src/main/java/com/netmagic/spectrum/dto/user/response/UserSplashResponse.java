package com.netmagic.spectrum.dto.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/***
 * This class maps the request object for the user splash Response
 * 
 * @author webonise
 */
@JsonIgnoreProperties({ "authToken", "loginUserId", "loginUserName", "loginUserEmail", "apiKey", "secretKey" })
public class UserSplashResponse extends UserAuthResponse {

    private static final long serialVersionUID = -6076753349882791481L;

}
