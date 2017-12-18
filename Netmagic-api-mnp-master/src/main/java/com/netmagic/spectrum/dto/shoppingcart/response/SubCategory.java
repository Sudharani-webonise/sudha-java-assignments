package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the sub category information from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 5427779691801065641L;

    @JsonProperty("subcatid")
    private String subCategoryId;
    @JsonProperty("subcatname")
    private String subCategoryName;
    @JsonProperty("alias")
    private String alias;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
