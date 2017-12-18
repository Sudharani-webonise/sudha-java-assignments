package com.netmagic.spectrum.dto.outstanding.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 
 * @author webonsie
 *
 */
public class CartPaymentResponse implements Serializable {

    private static final long serialVersionUID = -2612095607297776425L;

    @JsonProperty("ShopCartPayResponse")
    private ShopCartPayResponse shopCartPayResponse;

    public ShopCartPayResponse getShopCartPayResponse() {
        return shopCartPayResponse;
    }

    public void setShopCartPayResponse(ShopCartPayResponse shopCartPayResponse) {
        this.shopCartPayResponse = shopCartPayResponse;
    }

}
