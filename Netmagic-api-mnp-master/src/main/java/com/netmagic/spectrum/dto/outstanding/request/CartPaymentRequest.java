package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * dto used to inform the peoplesoft after shopping of all products in cart
 * 
 * @author webonise
 *
 */
public class CartPaymentRequest implements Serializable {

    private static final long serialVersionUID = -1833167670755232668L;

    @JsonProperty("ShoppingCartPaymentRequest")
    private ShoppingCartPaymentRequest shoppingCartPaymentRequest;

    public ShoppingCartPaymentRequest getShoppingCartPaymentRequest() {
        return shoppingCartPaymentRequest;
    }

    public void setShoppingCartPaymentRequest(ShoppingCartPaymentRequest shoppingCartPaymentRequest) {
        this.shoppingCartPaymentRequest = shoppingCartPaymentRequest;
    }

}
