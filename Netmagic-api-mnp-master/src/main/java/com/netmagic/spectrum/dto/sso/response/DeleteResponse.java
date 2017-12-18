package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteResponse implements Serializable {

    private static final long serialVersionUID = -7512547166893115358L;

    @JsonProperty("Status")
    private DeleteStatus status;

    public DeleteStatus getStatus() {
        return status;
    }

    public void setStatus(DeleteStatus status) {
        this.status = status;
    }
}
