package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has the location list from cms portal for shopping cart
 * 
 * @author webonise
 *
 */
public class LocationList implements Serializable {

    private static final long serialVersionUID = 9021719779871245735L;

    @JsonProperty("items")
    private List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
