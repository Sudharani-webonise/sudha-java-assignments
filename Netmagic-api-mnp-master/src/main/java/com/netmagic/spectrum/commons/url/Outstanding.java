package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Outstanding
 * 
 * @author Webonise
 *
 */
public enum Outstanding {

    WIDGET_API("OUTSTANDINGAPI1.v1"),

    LIST_API("OUTSTANDINGAPI2.v1"),

    INVOICE_PAYMENT_API("PAYMENTREQUEST.v1/"),

    ONLLINE_CREDIT_LIMIT_API("ONLINECREDITLIMIT.v1/"),

    BUSINESS_UNIT_API("BUSINESS_UNIT_LIST.v1"),

    SHOPPING_PAYMENT_REQUEST_API("SHOPCART_PAYMENT_REQUEST.v1/"),

    ON_ACCOUNT_PAYMENT_API("EBSONACCPAY.v1/");

    private String URL;

    private Outstanding(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
