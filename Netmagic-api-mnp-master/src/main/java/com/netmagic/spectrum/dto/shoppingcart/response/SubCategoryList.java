package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the sub category list from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class SubCategoryList implements Serializable {

    private static final long serialVersionUID = 3126898087566132996L;

    @JsonProperty("items")
    private List<SubCategory> subCategories;

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
