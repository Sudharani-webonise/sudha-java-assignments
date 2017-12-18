package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request for token generation
 * 
 * @author webonise
 *
 */
public class OtpRequest implements Serializable {

    private static final long serialVersionUID = 1042764344649994290L;

    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("otpThrough")
    private String otpThrough;
    @JsonProperty("requestFor")
    private String requestFor;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOtpThrough() {
        return otpThrough;
    }

    public void setOtpThrough(String otpThrough) {
        this.otpThrough = otpThrough;
    }

    public String getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(String requestFor) {
        this.requestFor = requestFor;
    }
}
