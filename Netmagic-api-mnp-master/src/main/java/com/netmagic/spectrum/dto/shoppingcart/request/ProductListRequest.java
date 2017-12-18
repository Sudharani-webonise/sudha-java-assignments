package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the information of the request sent to get the product list
 * from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class ProductListRequest implements Serializable {

    private static final long serialVersionUID = -2053640709498833409L;

    @JsonProperty("loc")
    private String location;
    @JsonProperty("subsubcatid")
    private String subSubCategoryId;

    public ProductListRequest() {
        super();
    }

    public ProductListRequest(String location, String subSubCategoryId) {
        super();
        this.location = location;
        this.subSubCategoryId = subSubCategoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(String subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

}
