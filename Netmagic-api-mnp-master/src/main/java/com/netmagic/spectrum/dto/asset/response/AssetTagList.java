package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the list of asset tag
 * 
 * @author webonise
 *
 */
public class AssetTagList implements Serializable {

    private static final long serialVersionUID = 4570588266284410730L;

    @JsonProperty("listOfAssetTags")
    private List<String> assetTagList;

    public List<String> getAssetTagList() {
        return assetTagList;
    }

    public void setAssetTagList(List<String> assetTagList) {
        this.assetTagList = assetTagList;
    }
}
