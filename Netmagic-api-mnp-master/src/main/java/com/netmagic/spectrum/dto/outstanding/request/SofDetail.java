package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author webonise
 *
 */
public class SofDetail implements Serializable {

    private static final long serialVersionUID = 8386123427772896503L;

    @JsonProperty("sofNumber")
    private String sofNumber;
    @JsonProperty("sofAmount")
    private Double sofAmount;

    public SofDetail() {
        super();
    }

    public SofDetail(String sofNumber, Double sofAmount) {
        super();
        this.sofNumber = sofNumber;
        this.sofAmount = sofAmount;
    }

    public String getSofNumber() {
        return sofNumber;
    }

    public void setSofNumber(String sofNumber) {
        this.sofNumber = sofNumber;
    }

    public Double getSofAmount() {
        return sofAmount;
    }

    public void setSofAmount(Double sofAmount) {
        this.sofAmount = sofAmount;
    }

}
