package com.netmagic.spectrum.service.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Role;
import com.netmagic.spectrum.dto.role.request.RoleAddRequest;
import com.netmagic.spectrum.dto.role.request.RoleViewRequest;
import com.netmagic.spectrum.dto.role.response.RoleViewResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthUser authUser;

    @Override
    public RoleViewResponse getRoleDetails(RoleViewRequest roleViewRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, Role.VIEW_ROLE.getURL(),
                    authUser.getAuthenticatedUserId());
            RoleViewResponse roleViewResponse = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(roleViewRequest), Collections.emptyMap(), RoleViewResponse.class);
            return roleViewResponse;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in getting role details ", ex);
            throw new RequestViolationException("Error in getting role details");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Role Service URI");
        }
    }

    @Override
    public String addOrUpdateRole(RoleAddRequest roleAddRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl, Role.ADD_ROLE.getURL());
            String isUpdated = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(roleAddRequest), Collections.emptyMap(), String.class);
            LOGGER.info("Role update status : {}", isUpdated);
            return isUpdated;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error to add the role details ", ex);
            throw new RequestViolationException("Error to add the role details");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Role Service URI");
        }
    }
}