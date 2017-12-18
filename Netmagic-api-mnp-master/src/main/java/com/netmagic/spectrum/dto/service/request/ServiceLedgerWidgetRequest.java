package com.netmagic.spectrum.dto.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the request rest data required for API in service ledger
 * 
 * @author webonise
 *
 */
public class ServiceLedgerWidgetRequest {
    @JsonProperty("method")
    private String method;

    @JsonProperty("input_type")
    private String inputType;

    @JsonProperty("response_type")
    private String responseType;

    @JsonProperty("rest_data")
    private WidgetRestData restData;

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

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public WidgetRestData getRestData() {
        return restData;
    }

    public void setRestData(WidgetRestData restData) {
        this.restData = restData;
    }

}
