package com.netmagic.spectrum.dto.service.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class stores data to be send to service ledger API in request Body
 * 
 * @author webonise
 * 
 */
public class ServiceAccessTokenRequest implements Serializable {

    private static final long serialVersionUID = 6978937913413439686L;

    @JsonProperty("method")
    private String method;

    @JsonProperty("input_type")
    private String inputType;

    @JsonProperty("rest_data")
    private AccessTokenRestData restData;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public AccessTokenRestData getRestData() {
        return restData;
    }

    public void setRestData(AccessTokenRestData restData) {
        this.restData = restData;
    }
}
