package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of a single asset type
 * 
 * @author webonise
 *
 */
public class AssetType implements Serializable {

    private static final long serialVersionUID = -439759972809739983L;

    @JsonProperty("assetTypeId")
    private Long id;
    @JsonProperty("assetType")
    private String name;
    @JsonProperty("assetCount")
    private Long assetCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Long assetCount) {
        this.assetCount = assetCount;
    }
}
