package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the information of the response of location list from cms
 * portal for shopping cart
 * 
 * @author webonise
 *
 */
public class Location implements Serializable {

    private static final long serialVersionUID = 1725993957989832708L;

    @JsonProperty("loc")
    private String locationId;
    @JsonProperty("locname")
    private String locationName;
    @JsonProperty("alias")
    private String alias;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
