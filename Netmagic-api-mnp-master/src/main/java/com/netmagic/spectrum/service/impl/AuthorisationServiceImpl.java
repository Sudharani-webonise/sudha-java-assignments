package com.netmagic.spectrum.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.authorisation.ModuleFunctionalityMapping;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.User;
import com.netmagic.spectrum.dto.user.response.ModuleInformation;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.AuthorisationService;

@Service
public class AuthorisationServiceImpl implements AuthorisationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisationServiceImpl.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private CacheService<ModuleFunctionalityMapping> moduleMappingCacheService;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${accessKey}")
    private String accessKey;

    @Override
    public List<ModuleInformation> getModulesFunctionalities() throws ServiceUnavailableException {
        try {
            ModuleFunctionalityMapping cachedModuleMapping = moduleMappingCacheService
                    .get(new ModuleFunctionalityMapping());
            if ( cachedModuleMapping == null ) {
                String url = String.format(Param.THREE.getParam(), apiBaseUrl, User.MODULE_CONFIG.getURL(), accessKey);
                cachedModuleMapping = apiClient.performGet(url, MediaType.APPLICATION_JSON_VALUE,
                        Collections.emptyMap(), ModuleFunctionalityMapping.class);
                moduleMappingCacheService.save(cachedModuleMapping, 7, TimeUnit.DAYS);
                return cachedModuleMapping.getModules();
            }
            return cachedModuleMapping.getModules();
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Authorisation Service URI");
        }
    }

}
