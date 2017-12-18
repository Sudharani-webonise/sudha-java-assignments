package com.netmagic.spectrum.dto.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the mobile user authentication
 * Response
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
@JsonIgnoreProperties({ "apiKey", "secretKey" })
public class MobileUserAuthResponse extends UserAuthResponse {

    private static final long serialVersionUID = -548345791545436565L;

    @JsonProperty("customerSplashPageDataBeans")
    private List<MobileCustomerDetails> mobileCustomerDetails;

    public List<MobileCustomerDetails> getMobileCustomerDetails() {
        return mobileCustomerDetails;
    }

    public void setMobileCustomerDetails(List<MobileCustomerDetails> mobileCustomerDetails) {
        this.mobileCustomerDetails = mobileCustomerDetails;
    }

}
