package com.netmagic.spectrum.mobile.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.mobile.service.MobileUserService;

@RequestMapping(value = "/mobile/api/user")
@RestController
@CrossOrigin
public class MobileUserController {

    @Value("${mobile.user.max.time}")
    private int maxIntervalTime;

    @Autowired
    private MobileUserService mobileuserService;

    @Autowired
    private AuthUser authUser;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MobileUserAuthResponse login(@RequestBody UserAuthRequest userAuthRequest, HttpSession session)
            throws InvalidCredentialsException {
        MobileUserAuthResponse mobileUser = mobileuserService.authenticateUser(userAuthRequest);
        mobileUser.setAuthToken(session.getId());
        session.setMaxInactiveInterval(maxIntervalTime);
        authUser.authenticateMobileUser(mobileUser);
        return mobileUser;
    }

    @RequestMapping(value = "/splash/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSplashResponse fetchSplashData() {
        return mobileuserService.getUserSplashData(authUser.getAuthenticatedUserId());
    }
}
