package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Assets
 * 
 * @author Webonise
 *
 */
public enum Assets {

    WIDGET("assetAPI/asset/widget/count"),

    LIST("assetAPI/asset/widget/listing"),

    TAG_LIST("assetAPI/asset/tag/list"),

    ADD_TAG("assetAPI/asset/tag/add"),

    REMOVE_TAG("assetAPI/asset/tag/remove");

    private String URL;

    private Assets(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

}
