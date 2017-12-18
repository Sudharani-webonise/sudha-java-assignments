package com.netmagic.spectrum.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netmagic.spectrum.commons.AuthType;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.user.request.DocumentDetails;
import com.netmagic.spectrum.dto.user.request.OtpRequest;
import com.netmagic.spectrum.dto.user.request.ResetPasswordRequest;
import com.netmagic.spectrum.dto.user.request.SignUpRequest;
import com.netmagic.spectrum.dto.user.request.SuperAdminRequest;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.dto.user.request.UserPermissionRequest;
import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.OtpResponse;
import com.netmagic.spectrum.dto.user.response.ResetPasswordResponse;
import com.netmagic.spectrum.dto.user.response.SignUpResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserPermissions;
import com.netmagic.spectrum.dto.user.response.UserSplashResponse;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.UserService;

@RequestMapping(value = "/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUser authUser;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserAuthResponse login(@RequestBody UserAuthRequest userAuthRequest, HttpSession session)
            throws InvalidCredentialsException {
        UserAuthResponse user = userService.authenticateUser(userAuthRequest);
        user.setAuthToken(session.getId());
        authUser.authenticateUser(user);
        return user;
    }

    @RequestMapping(value = "/internal/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InternalUserResponse internalLogin(@RequestBody UserAuthRequest userAuthRequest, HttpSession session)
            throws InvalidCredentialsException {
        InternalUserResponse user = userService.authenticateInternalUser(userAuthRequest);
        user.setAuthToken(session.getId());
        authUser.authenticateInternalUser(user);
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setModuleInformations(user.getInternalUserPermissions().getModuleInformations());
        authUser.updateAuthAuthorities(userPermissions, AuthType.INTERNAL_USER.getAuthType());
        return user;
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserPermissions fetchPermissions(@RequestParam(value = "mainCustomerId") Long mainCustomerId,
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "associateCustomerId") Long associateCustomerId) {
        UserPermissionRequest userPermissionRequest = new UserPermissionRequest(authUser.getAuthenticatedUserId(),
                mainCustomerId, associateCustomerId, projectId);
        UserPermissions userPermissions = userService.getPermissions(userPermissionRequest);
        authUser.updateAuthAuthorities(userPermissions, AuthType.USER.getAuthType());
        return userPermissions;
    }

    @RequestMapping(value = "/splash/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSplashResponse fetchSplashData() {
        return userService.getUserSplashData(authUser.getAuthenticatedUserId());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SignUpResponse registerUser(@RequestBody final SignUpRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

    @RequestMapping(value = "/new/user/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SignUpRequest getRegistredUser(@RequestParam(value = "userEmail") String userEmail) {
        return userService.getRegistredUser(userEmail);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String verifyDocuments(@RequestBody final DocumentDetails documentDetails) {
        return userService.verifyUserDetails(documentDetails);
    }

    @RequestMapping(value = "/generate/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OtpResponse generateToken(@RequestBody OtpRequest otpRequest) {
        return userService.getOtpResponse(otpRequest);
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResetPasswordResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }

    @RequestMapping(value = "/create/super/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean requesForSuperAdmin(@RequestParam(value = "emailId") String emailId,
            @RequestParam(value = "merchantRefNumber") String merchantRefNumber,
            @RequestBody SuperAdminRequest superAdminRequest)
            throws ServiceUnavailableException, JsonProcessingException {
        return userService.createSuperAdmin(emailId, merchantRefNumber, superAdminRequest);
    }

    @RequestMapping(value = "/verify/existing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean verifyUser(@RequestParam String emailId) {
        return userService.verifyExistingUser(emailId);
    }

    @RequestMapping(value = "/password/update/tempUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResetPasswordResponse resetPasswordForNewUser(@RequestBody ResetPasswordRequest resetPasswordRequest)
            throws JsonProcessingException {
        return userService.changeUserPassword(resetPasswordRequest);
    }
}
