package com.netmagic.spectrum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.user.response.ModuleInformation;
import com.netmagic.spectrum.service.AuthorisationService;

@RequestMapping("/api/authorisation")
@RestController
public class AuthorisationController {

    @Autowired
    AuthorisationService authorisationService;

    @RequestMapping(value = "/modules/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModuleInformation> getModulesMapping() {
        return authorisationService.getModulesFunctionalities();
    }
}
