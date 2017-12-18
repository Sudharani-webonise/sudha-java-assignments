package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Shopping Cart
 * 
 * @author Webonise
 *
 */
public enum ShoppingCart {

    LOCATION("getLocationDetails"),

    SUB_CATEGORY("getSubCategory"),

    SUB_SUB_CATEGORY("getSubSubCategory"),

    PRODUCTS("getProducts"),

    PRODUCT("getSingleProduct"),

    DISPLAY_PRICE("fnDisplayCalculatePrice"),

    CALCULATE_PRICE("CalculatePrice"),

    CREATE_NEW_SOF("fnCreateContract"),

    CREATE_AMENDMENT_SOF("fnAmendmentSOF"),

    CUSTOMER_CONTACT("fnGetContactList"),

    CUSTOMER_ADDRESS("getCustomerAddress");

    private String URL;

    private ShoppingCart(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
