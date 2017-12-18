package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the information of the functionality available in a
 * module
 * 
 * @author webonise
 *
 */

public class ModuleFunctionality implements Serializable {

    private static final long serialVersionUID = 2465271011637810600L;

    @JsonProperty("functionId")
    private Long id;
    @JsonProperty("functionName")
    private String name;
    @JsonProperty("functionCode")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
