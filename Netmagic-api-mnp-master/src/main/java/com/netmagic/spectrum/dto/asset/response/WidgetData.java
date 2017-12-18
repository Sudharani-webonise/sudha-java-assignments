package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the uncategorized data for asset widget as provided from NM
 * servers
 * 
 * @author webonise
 *
 */
public class WidgetData implements Serializable {

    private static final long serialVersionUID = -2144396359630340446L;

    @JsonProperty("assetCount")
    private Long assetCount;
    @JsonProperty("listOfAssetTypeCount")
    private List<AssetType> assetTypes;

    public Long getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Long assetCount) {
        this.assetCount = assetCount;
    }

    public List<AssetType> getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(List<AssetType> assetTypes) {
        this.assetTypes = assetTypes;
    }
}
