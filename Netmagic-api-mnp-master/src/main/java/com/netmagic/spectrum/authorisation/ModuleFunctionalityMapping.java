package com.netmagic.spectrum.authorisation;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.dto.user.response.ModuleInformation;

/**
 * This class stores list of Modules and their functionalities mapping
 * configuration information as required by MNP into the cache
 * 
 * @author webonise
 *
 */
public class ModuleFunctionalityMapping implements Cacheable {

    private static final long serialVersionUID = -272271955360726167L;
    private static final String CACHE_KEY = "MODULE_FUNCTIONALITY_MAPPING";
    private static final String OBJECT_KEY = "AUTHORISATION";

    @JsonProperty(value = "allMyNmUserModuleAccessBeans")
    private List<ModuleInformation> modules;

    public List<ModuleInformation> getModules() {
        return modules;
    }

    public void setModules(List<ModuleInformation> modules) {
        this.modules = modules;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String getCacheKey() {
        return CACHE_KEY;
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }

}
