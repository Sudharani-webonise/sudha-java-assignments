package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Customer
 * 
 * @author Webonise
 *
 */
public enum Customer {

    PROJECTS("userAPI/user/projects?mainCustomerId="),

    ASSOCIATE_CUSTOMERS_INTERNAL_API("customerAPI/customer/associate/list?customerId="),

    PROJECTS_INTERNAL_API("customerAPI/customer/project/list?callingCustomerId="),

    ASSOCIATE_CUSTOMERS("customerAPI/associateCust/list?userId="),

    CONTACTS("customerAPI/customer/contact/list"),

    COMBINATION_ID("customerAPI/customer/getCombinationId"),

    VALIDATE_CUST_ID("billingGroupAPI/group/validate?combinationId="),

    CUSTOMER_ADDRESS("customer/Billing/address?customerSugarId=");

    private String URL;

    private Customer(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

}
