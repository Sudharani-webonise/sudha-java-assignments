package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores information of the request parameters which are sent when
 * making API calls
 * 
 * @author webonise
 *
 */
public class OutstandingRequest implements Serializable {

    private static final long serialVersionUID = -6671025590051452108L;

    @JsonProperty("OutstandingAPIinput")
    private OutstandingRequestWrapper requestWrapper;

    public OutstandingRequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(OutstandingRequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

}
