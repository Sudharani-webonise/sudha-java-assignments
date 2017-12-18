package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Role
 * 
 * @author Webonise
 *
 */
public enum Role {

    VIEW_ROLE("userAPI/user/priority/roles/permissions?userId="),

    ADD_ROLE("userAPI/manage/role/add");

    private String URL;

    private Role(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
