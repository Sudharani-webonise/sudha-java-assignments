package com.netmagic.spectrum.service;

import com.netmagic.spectrum.dto.role.request.RoleAddRequest;
import com.netmagic.spectrum.dto.role.request.RoleViewRequest;
import com.netmagic.spectrum.dto.role.response.RoleViewResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface RoleService {

    /**
     * This method is used to get the role details of the user
     *
     * @param roleViewRequest
     * @return RoleViewResponse
     * @throws ServiceUnavailableException
     * @throws RequestViolationException
     */
    RoleViewResponse getRoleDetails(RoleViewRequest roleViewRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method is request o add the role details of the user
     *
     * @param roleAddRequest
     * @return {@link String}
     * @throws ServiceUnavailableException
     * @throws RequestViolationException
     */
    String addOrUpdateRole(RoleAddRequest roleAddRequest) throws RequestViolationException, ServiceUnavailableException;

}
