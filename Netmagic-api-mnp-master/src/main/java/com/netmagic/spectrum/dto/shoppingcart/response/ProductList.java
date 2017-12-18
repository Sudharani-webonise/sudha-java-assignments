package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the product list from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class ProductList implements Serializable {

    private static final long serialVersionUID = 1435931209817049036L;

    @JsonProperty("items")
    private List<CMSProducts> products;

    public List<CMSProducts> getProducts() {
        return products;
    }

    public void setProducts(List<CMSProducts> products) {
        this.products = products;
    }

}
