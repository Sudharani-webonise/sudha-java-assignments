package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the status which is sent by the cloud portal after a delete
 * request for a product
 * 
 * @author webonise
 *
 */

public class DeleteLineItemCloudResponse implements Serializable {

    private static final long serialVersionUID = 336853847956960393L;

    @JsonProperty("Response")
    private DeleteResponse response;

    public DeleteResponse getResponse() {
        return response;
    }

    public void setResponse(DeleteResponse response) {
        this.response = response;
    }

}
