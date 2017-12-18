package com.netmagic.spectrum.dto.asset.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the data for the add/remove asset tag request.
 * 
 * @author webonsie
 *
 */
public class AssetTagRequest implements Serializable {

    private static final long serialVersionUID = 4818298979464178022L;

    @JsonProperty("assetId")
    private Long assetId;
    @JsonProperty("tagName")
    private String tagName;

    public AssetTagRequest() {
        super();
    }

    public AssetTagRequest(Long assetId, String tagName) {
        super();
        this.assetId = assetId;
        this.tagName = tagName;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
