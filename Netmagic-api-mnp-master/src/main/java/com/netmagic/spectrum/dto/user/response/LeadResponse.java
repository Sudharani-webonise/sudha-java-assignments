package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds information received after successful Lead Creation
 * 
 * @author webonsie
 */
public class LeadResponse implements Serializable {

    private static final long serialVersionUID = 6924098529794112961L;

    @JsonProperty("lid")
    private String lid;
    @JsonProperty("klid")
    private String klid;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getKlid() {
        return klid;
    }

    public void setKlid(String klid) {
        this.klid = klid;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
