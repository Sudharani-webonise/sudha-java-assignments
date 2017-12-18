package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This stores the project for which data is requested from NM servers
 * 
 * @author webonise
 *
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 319738790559514068L;

    private static final String DEFAULT_VALUE = "ALL";

    @JsonProperty("Project")
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = ((projectName == null) || (projectName.isEmpty())) ? DEFAULT_VALUE : projectName;
    }
}
