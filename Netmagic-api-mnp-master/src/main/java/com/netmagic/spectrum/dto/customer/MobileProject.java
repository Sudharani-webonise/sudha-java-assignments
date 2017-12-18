package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;
import java.util.List;

/***
 * This DTO consists the details of mobile projects
 * 
 * @author webonise
 *
 */
public class MobileProject implements Serializable {

    private static final long serialVersionUID = -1819953896192621686L;

    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
