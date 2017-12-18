package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request made for getting the customer contacts
 * 
 * @author webonise
 *
 */
public class CustomerContactRequest implements Serializable {

    private static final long serialVersionUID = 8351630571719256650L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("acc")
    private String customerId;

    public CustomerContactRequest() {
        super();
    }

    public CustomerContactRequest(String token, String customerId) {
        super();
        this.token = token;
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
