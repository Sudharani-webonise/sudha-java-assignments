package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request to fetch data for a single product
 * 
 * @author webonise
 *
 */
public class ProductDetailRequest implements Serializable {

    private static final long serialVersionUID = -6663703777914028827L;

    @JsonProperty("pid")
    private String productId;

    public ProductDetailRequest() {
        super();
    }

    public ProductDetailRequest(String productId) {
        super();
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
