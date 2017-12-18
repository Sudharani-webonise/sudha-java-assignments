package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This Class holds Information to reset Password
 * 
 * @author webonise
 */
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 2931215471360625361L;

    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("token")
    private String token;
    @JsonProperty("newPassword")
    private String newPassword;
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
