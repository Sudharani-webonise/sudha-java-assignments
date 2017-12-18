package com.netmagic.spectrum.helpers;

/**
 * The enum consists of mock data that can be used in test cases.
 * 
 * @author webonsie
 *
 */
public enum MockData {

    SUCCESS("Success"),

    FAILURE("Failure"),

    UTF_8("UTF-8"),

    PROJECT_ID("123"),

    PROJECT_NAME("Mock project name"),

    MAIN_CUSTOMER_ID("123"),

    BILL_TO_CUSTOMER("6583841b-e160-4845-d7ce-579b386bffe6"),

    ASSOCIATE_CUSTOMER_ID("123"),

    USER_ID("23"),

    ASSET_ID("123"),

    TOKEN("Mock valid Token"),

    CONTACT_ID("1"),

    CONTACT_TYPE_ID("2"),

    COMBINATION_ID("41721049_3"),

    INVALID_TOKEN("Invalid Token/Session Expired"),

    USER("USER"),

    TAG_NAME("tag name"),

    TYPE_ID("44"),

    SOF_NUMBER("55"),

    LINE_ITEM_NUMBER("60"),

    SUB_CAT_ID("5"),

    PRODUCT_ID("91"),

    TICKET_NUMBER("TT241"),

    ATTACHMENT_ID("101"),

    PAGE_NUMBER("1"),

    ASCENDING("ASC"),

    CASE_SUB_TYPE("sub type"),

    SORT_BY("ticket number"),

    APPTYPE("IOS"),

    PURPOSE("calculate"),

    USER_EMAIL("abc@xyz.com"),

    TRANSACTION_NUMBER("nmit-tran-1234"),

    PAYMENT_SOURCE("ebs"),

    REFERENCE_NUMBER("reference_id"),

    AMOUNT("1000"),

    BUSINESS_UNIT("NMITS"),

    TEMP_USER_EMAIL("temp@user.com");

    private String data;

    private MockData(String data) {
        this.data = data;
    }

    public String getString() {
        return data;
    }

    public Long getLong() {
        return Long.parseLong(data);
    }

}
