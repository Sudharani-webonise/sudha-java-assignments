package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of a single asset type
 * 
 * @author webonise
 *
 */

public class AssetList implements Serializable {

    private static final long serialVersionUID = -4938046031456648239L;

    @JsonProperty("listOfCustomerAssets")
    private List<Asset> assets;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
