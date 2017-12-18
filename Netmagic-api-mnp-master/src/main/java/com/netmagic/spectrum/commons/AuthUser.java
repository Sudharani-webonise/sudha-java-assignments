package com.netmagic.spectrum.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.dto.user.response.InternalUserResponse;
import com.netmagic.spectrum.dto.user.response.MobileUserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserAuthResponse;
import com.netmagic.spectrum.dto.user.response.UserPermissions;

/**
 * This class helps to retrieve the current user Id from the redis using session
 * context
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
@Service
public class AuthUser {

    public void authenticateUser(UserAuthResponse user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.USER.getAuthType())));
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(user, user, authorities));
        SecurityContextHolder.setContext(securityContext);
    }

    public void authenticateMobileUser(MobileUserAuthResponse mobileUser) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.USER.getAuthType())));
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(mobileUser, mobileUser, authorities));
        SecurityContextHolder.setContext(securityContext);
    }

    public void authenticateInternalUser(InternalUserResponse internalUser) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Arrays.asList(new SimpleGrantedAuthority(AuthType.INTERNAL_USER.getAuthType())));
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext
                .setAuthentication(new UsernamePasswordAuthenticationToken(internalUser, internalUser, authorities));
        SecurityContextHolder.setContext(securityContext);
    }

    public boolean updateAuthAuthorities(UserPermissions userPermissions, String authUserType) {
        if ( userPermissions.getModuleInformations() != null ) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(authUserType));
            authorities.addAll(userPermissions.getModuleInformations().stream()
                    .flatMap(model -> model.getModuleFunctionalities().stream())
                    .map(permission -> new SimpleGrantedAuthority(permission.getCode().toUpperCase()))
                    .collect(Collectors.toList()));
            Authentication updateAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                    authentication.getCredentials(), authorities);
            SecurityContextHolder.getContext().setAuthentication(updateAuthentication);
            return true;
        }
        return false;
    }

    public Long getAuthenticatedUserId() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getUserId();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getUserId();
        }
    }

    public String getAuthenticatedApiKey() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getApiKey();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getApiKey();
        }
    }

    public String getAuthenticatedSecretKey() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getSecretKey();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getSecretKey();
        }
    }

    public String getAuthentedEmailId() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getUserEmail();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getUserEmail();
        }
    }

    public String getFirstName() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getUserFirstName();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getUserName();
        }
    }

    public String getLastName() {
        if ( isInternalUser() ) {
            InternalUserResponse userResponse = getAuthInternalUserFromSecurityContext();
            return userResponse.getUserLastName();
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            return userResponse.getUserName();
        }
    }

    public boolean isExistingUser() {
        if ( isInternalUser() ) {
            return false;
        } else {
            UserAuthResponse userResponse = getAuthUserFromSecurityContext();
            if ( userResponse.isExistingUser() ) {
                return true;
            }
            return false;
        }
    }

    private UserAuthResponse getAuthUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAuthResponse) authentication.getPrincipal();
    }

    private InternalUserResponse getAuthInternalUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (InternalUserResponse) authentication.getPrincipal();
    }

    private boolean isInternalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for ( GrantedAuthority auth : authentication.getAuthorities() ) {
            if ( auth.getAuthority().equals(AuthType.USER.getAuthType()) ) {
                return false;
            }
        }
        return true;
    }

}
