package com.netmagic.spectrum.dto.provision;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds Line Item product details
 * 
 * @author webonise
 */
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -4945935748862900122L;

    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("productQty")
    private String productQty;
    @JsonProperty("comment")
    private String comment;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
