package com.netmagic.spectrum.dto.shoppingcart.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request to fetch the customer address
 * 
 * @author webonise
 *
 */
public class CustomerAddressRequest {
    @JsonProperty("token")
    private String token;
    @JsonProperty("acc")
    private String mainCustomerId;
    @JsonProperty("addresstype")
    private String addresstype;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(String mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }
}
