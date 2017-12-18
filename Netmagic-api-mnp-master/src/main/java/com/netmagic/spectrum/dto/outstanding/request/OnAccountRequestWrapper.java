package com.netmagic.spectrum.dto.outstanding.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountPaymentRequest;

public class OnAccountRequestWrapper {
    @JsonProperty("EBSOnAccountPaymentReq")
    private OnAccountPaymentRequest onAccountPaymentRequest;

    public OnAccountRequestWrapper() {
        super();
    }

    public OnAccountRequestWrapper(OnAccountPaymentRequest onAccountPaymentRequest) {
        super();
        this.onAccountPaymentRequest = onAccountPaymentRequest;
    }

    public OnAccountPaymentRequest getOnAccountPaymentRequest() {
        return onAccountPaymentRequest;
    }

    public void setOnAccountPaymentRequest(OnAccountPaymentRequest onAccountPaymentRequest) {
        this.onAccountPaymentRequest = onAccountPaymentRequest;
    }

}
