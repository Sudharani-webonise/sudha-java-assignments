package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the permission a user has for a particular module based
 * on the role
 * 
 * @author Pareekshit
 *
 */
public class RolePermission implements Serializable {

    private static final long serialVersionUID = -8512876750553540348L;

    @JsonProperty("functionName")
    private String functionName;
    @JsonProperty("functionExits")
    private String functionExists;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionExists() {
        return functionExists;
    }

    public void setFunctionExists(String functionExists) {
        this.functionExists = functionExists;
    }
}
