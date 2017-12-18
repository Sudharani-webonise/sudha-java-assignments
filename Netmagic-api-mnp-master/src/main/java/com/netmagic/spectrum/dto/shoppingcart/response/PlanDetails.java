package com.netmagic.spectrum.dto.shoppingcart.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanDetails {

    @JsonProperty("plan")
    private String subSubCategory;

    public String getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(String subSubCategory) {
        this.subSubCategory = subSubCategory;
    }
}
