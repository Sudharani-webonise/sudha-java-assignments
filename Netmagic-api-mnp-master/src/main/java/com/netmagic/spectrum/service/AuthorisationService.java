package com.netmagic.spectrum.service;

import java.util.List;

import com.netmagic.spectrum.dto.user.response.ModuleInformation;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface AuthorisationService {
    /**
     * This method fetches the master mapping of modules and their respective
     * functionalities
     * 
     * @return {@link List<ModuleInformation>}
     * @throws ServiceUnavailableException
     */
    List<ModuleInformation> getModulesFunctionalities() throws ServiceUnavailableException;
}
