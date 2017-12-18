package com.netmagic.spectrum.dto.ticket.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores information of the users who are to be notified when a
 * ticket is created
 * 
 * @author webonise
 *
 */

public class ContactNotification implements Serializable {

    private static final long serialVersionUID = 571893335119900552L;

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("userMobile")
    private String userMobile;
    @JsonProperty("isNewlyAdded")
    private String isNewlyAdded;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIsNewlyAdded() {
        return isNewlyAdded;
    }

    public void setIsNewlyAdded(String isNewlyAdded) {
        this.isNewlyAdded = isNewlyAdded;
    }
}
