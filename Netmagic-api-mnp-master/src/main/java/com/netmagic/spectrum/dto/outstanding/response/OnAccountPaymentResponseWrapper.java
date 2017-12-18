package com.netmagic.spectrum.dto.outstanding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.outstanding.response.OnAccountPaymentResponse;

public class OnAccountPaymentResponseWrapper {
    @JsonProperty("EBSOnAccountPaymentRes")
    private OnAccountPaymentResponse onAccountPaymentResponse;

    public OnAccountPaymentResponse getOnAccountPaymentResponse() {
        return onAccountPaymentResponse;
    }

    public void setOnAccountPaymentResponse(OnAccountPaymentResponse onAccountPaymentResponse) {
        this.onAccountPaymentResponse = onAccountPaymentResponse;
    }
}
