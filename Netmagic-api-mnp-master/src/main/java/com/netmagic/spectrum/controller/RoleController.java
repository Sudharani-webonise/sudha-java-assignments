package com.netmagic.spectrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.role.request.RoleAddRequest;
import com.netmagic.spectrum.dto.role.request.RoleViewRequest;
import com.netmagic.spectrum.dto.role.response.RoleViewResponse;
import com.netmagic.spectrum.service.RoleService;

@RequestMapping(value = "/api/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthUser authUser;

    @RequestMapping(value = "/customer/{mainCustomerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleViewResponse fetchRoleDetails(@PathVariable(value = "mainCustomerId") Long mainCustomerId,
            @RequestParam(value = "associateCustomerId", required = false) Long associateCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        RoleViewRequest roleViewRequest = new RoleViewRequest();
        roleViewRequest.setAssociateCustomerId(associateCustomerId);
        roleViewRequest.setMainCustomerId(mainCustomerId);
        roleViewRequest.setProjectId(projectId);
        return roleService.getRoleDetails(roleViewRequest);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addRoleDetails(@RequestBody RoleAddRequest roleAddRequest) {
        roleAddRequest.setUserId(authUser.getAuthenticatedUserId());
        return roleService.addOrUpdateRole(roleAddRequest);
    }
}
