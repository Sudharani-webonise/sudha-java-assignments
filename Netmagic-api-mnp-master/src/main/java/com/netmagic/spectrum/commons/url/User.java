package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for User
 * 
 * @author Webonise
 *
 */
public enum User {

    LOGIN("userAPI/user/auth"),

    INTERNAL_LOGIN("userAPI/user/internal/auth"),

    PERMISSION("userAPI/user/permission"),

    SPLASH("userAPI/user/splash/data?userId="),

    RESET_PASSWORD("userAPI/user/password/update"),

    GENERATE_OTP("userAPI/user/token/generate"),

    MODULE_CONFIG("userAPI/modules/config?accesskey="),

    CREATE_LEAD("fnSetLeadDetails"),

    OPPORTUNITY_METHOD("fnConvertToOpportunity"),

    VALIDATE_METHOD("fnValidateDetails"),

    SUPER_ADMIN("userAPI/user/superAdmin/pwd"),

    VERIFY_EXISTING_USER("userAPI/user/emailId/isExisted?userEmailId="),

    SEND_MAIL("userAPI/signUpUser/password/reset/link?userEmailId=");

    private String URL;

    private User(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

}
