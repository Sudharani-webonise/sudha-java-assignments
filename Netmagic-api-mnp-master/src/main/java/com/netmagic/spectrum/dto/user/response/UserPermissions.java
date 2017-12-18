package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of the permissions a user has for a set of
 * mainCustomer and associatedCustomer for different modules and their
 * functionalities
 * 
 * @author webonise
 *
 */

public class UserPermissions implements Serializable {

    private static final long serialVersionUID = -3860915537798677219L;

    @JsonProperty("myNmUserModuleAccessBeans")
    private List<ModuleInformation> moduleInformations;

    public List<ModuleInformation> getModuleInformations() {
        return moduleInformations;
    }

    public void setModuleInformations(List<ModuleInformation> moduleInformations) {
        this.moduleInformations = moduleInformations;
    }
}
