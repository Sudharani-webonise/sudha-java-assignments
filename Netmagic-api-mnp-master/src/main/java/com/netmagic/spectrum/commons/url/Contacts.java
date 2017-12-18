package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Contacts
 * 
 * @author Webonise
 *
 */
public enum Contacts {

    WIDGET("userAPI/user/widget/count"),

    LIST("userAPI/user/widget/listing"),

    DETAILS("userAPI/user/view?userId="),

    ADD("userAPI/user/add"),

    UPDATE("userAPI/user/update"),

    INVITE_CONTACT("userAPI/user/invite"),

    TYPES("customer/getCustomerContactTypeDetails"),

    SUB_TYPES("customer/getCustomerContactSubTypeDetails?contactTypeId="),

    CALLING_CONFIG("userAPI/contact/number/config"),

    INTERNAL_CONTACTS("customerAPI/customer/netmagic/contacts?mainCustomerId=");

    private String URL;

    private Contacts(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
