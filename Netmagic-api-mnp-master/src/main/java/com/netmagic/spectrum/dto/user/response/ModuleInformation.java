package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the information of a module and the functions which are
 * present in the module
 * 
 * @author webonise
 *
 */

public class ModuleInformation implements Serializable {

    private static final long serialVersionUID = -4149526560793975292L;

    @JsonProperty("moduleId")
    private Long moduleId;
    @JsonProperty("moduleName")
    private String moduleName;
    @JsonProperty("myNmUserFunctionAccessBeans")
    private List<ModuleFunctionality> moduleFunctionalities;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<ModuleFunctionality> getModuleFunctionalities() {
        return moduleFunctionalities;
    }

    public void setModuleFunctionalities(List<ModuleFunctionality> moduleFunctionalities) {
        this.moduleFunctionalities = moduleFunctionalities;
    }
}
