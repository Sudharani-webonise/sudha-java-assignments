package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to map the Product pricing response from Sugar CRM
 * 
 * @author webonise
 *
 */
@JsonIgnoreProperties({ "status", "message" })
public class ProductPriceResponse implements Serializable {

    private static final long serialVersionUID = -4219923840867436080L;

    @JsonProperty("data")
    private PriceResponseData priceResponseData;

    public PriceResponseData getPriceResponseData() {
        return priceResponseData;
    }

    public void setPriceResponseData(PriceResponseData priceResponseData) {
        this.priceResponseData = priceResponseData;
    }

}
