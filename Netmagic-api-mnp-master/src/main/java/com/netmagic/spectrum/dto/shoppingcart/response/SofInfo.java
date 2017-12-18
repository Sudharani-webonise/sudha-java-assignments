package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the proposalId and sof id's after successful creation of
 * Sof via shopping cart
 * 
 * @author webonise
 *
 */
public class SofInfo implements Serializable {

    private static final long serialVersionUID = -7768831055912854303L;

    @JsonProperty("prop_id")
    private String propId;
    @JsonProperty("ProjectId")
    private String projectId;
    @JsonProperty("sof_id")
    private List<String> sofs;

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public List<String> getSofs() {
        return sofs;
    }

    public void setSofs(List<String> sofs) {
        this.sofs = sofs;
    }
}
