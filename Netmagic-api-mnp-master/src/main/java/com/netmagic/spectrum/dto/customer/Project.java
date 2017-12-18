package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of a single project which is fetched while
 * raising a ticket
 * 
 * @author webonise
 *
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 863880509590128124L;

    @JsonProperty("projectId")
    private Long id;
    @JsonProperty("projectName")
    private String name;
    @JsonProperty("projectSugarId")
    private String sugarCrmProjectId;

    public Project() {
        super();
    }

    public Project(String name) {
        super();
        this.name = name;
    }

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

    public String getSugarCrmProjectId() {
        return sugarCrmProjectId;
    }

    public void setSugarCrmProjectId(String sugarCrmProjectId) {
        this.sugarCrmProjectId = sugarCrmProjectId;
    }

}
