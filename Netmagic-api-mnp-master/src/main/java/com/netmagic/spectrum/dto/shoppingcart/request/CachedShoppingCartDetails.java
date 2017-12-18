package com.netmagic.spectrum.dto.shoppingcart.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.dto.shoppingcart.response.PriceResponseData;

/**
 * This class stores the shopping cart data for a user as per selected
 * combination of customers.
 * 
 * @author webonise
 *
 */
@JsonIgnoreProperties({ "OBJECT_KEY", "CACHE_KEY" })
public class CachedShoppingCartDetails implements Cacheable {

    private static final long serialVersionUID = -2865345954018491574L;

    private static final String OBJECT_KEY = "SHOPPING_CART";

    private static final String CACHE_KEY = "LINE_ITEMS";

    private String userEmail;

    private String billToCustomer;

    private String supportToCustomer;

    private PriceResponseData priceResponseData;

    public CachedShoppingCartDetails() {
        super();
    }

    public CachedShoppingCartDetails(String userEmail, String billToCustomer, String supportToCustomer) {
        super();
        this.userEmail = userEmail.toLowerCase();
        this.billToCustomer = billToCustomer;
        this.supportToCustomer = supportToCustomer;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBillToCustomer() {
        return billToCustomer;
    }

    public void setBillToCustomer(String billToCustomer) {
        this.billToCustomer = billToCustomer;
    }

    public String getSupportToCustomer() {
        return supportToCustomer;
    }

    public void setSupportToCustomer(String supportToCustomer) {
        this.supportToCustomer = supportToCustomer;
    }

    public PriceResponseData getPriceResponseData() {
        return priceResponseData;
    }

    public void setPriceResponseData(PriceResponseData priceResponseData) {
        this.priceResponseData = priceResponseData;
    }

    @Override
    public String getCacheKey() {
        return String.format(Param.FOUR.getParam(), CACHE_KEY, userEmail, billToCustomer, supportToCustomer);
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }
}
