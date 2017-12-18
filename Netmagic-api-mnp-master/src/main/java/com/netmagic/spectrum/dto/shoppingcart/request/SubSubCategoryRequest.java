package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the information of the request sent to get the sub sub
 * category list from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class SubSubCategoryRequest implements Serializable {

    private static final long serialVersionUID = 7165763306946279361L;

    @JsonProperty("id")
    private String subCategoryId;

    public SubSubCategoryRequest() {
        super();
    }

    public SubSubCategoryRequest(String subCategoryId) {
        super();
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

}
