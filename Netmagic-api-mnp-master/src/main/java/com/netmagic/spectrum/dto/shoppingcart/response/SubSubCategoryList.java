package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the sub sub category list from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class SubSubCategoryList implements Serializable {

    private static final long serialVersionUID = 1437163146076562442L;

    @JsonProperty("items")
    private List<SubSubCategory> subSubCategories;

    public List<SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(List<SubSubCategory> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }

}
