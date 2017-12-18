package com.netmagic.spectrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.sso.response.SSOTokenResponse;
import com.netmagic.spectrum.service.SSOService;

@RequestMapping(value = "/api/sso")
@RestController
public class SSOController {

    @Autowired
    private SSOService ssoService;

    @RequestMapping(value = "/token/url", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSSOToken() {
        SSOTokenResponse tokenResponse = ssoService.getToken();
        return ssoService.getAuthenticatedSSOTokenURL(tokenResponse.getResponse().getSingleSignOnToken().getToken());
    }

    @RequestMapping(value = "/classicMnp/token/url", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClassicMnpSSOToken() {
        SSOTokenResponse tokenResponse = ssoService.getToken();
        return ssoService.getclassicMnpSSOTokenURL(tokenResponse.getResponse().getSingleSignOnToken().getToken());
    }
}
