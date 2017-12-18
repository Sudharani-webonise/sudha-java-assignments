package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the information of the response of the product list and the
 * curated content from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class CMSProducts implements Serializable {

    private static final long serialVersionUID = 4010063766531444129L;

    @JsonProperty("cms")
    private Object cms;
    @JsonProperty("sugar")
    private List<Product> crmProducts;

    public Object getCms() {
        return cms;
    }

    public void setCms(Object cms) {
        this.cms = cms;
    }

    public List<Product> getCrmProducts() {
        return crmProducts;
    }

    public void setCrmProducts(List<Product> crmProducts) {
        this.crmProducts = crmProducts;
    }
}
