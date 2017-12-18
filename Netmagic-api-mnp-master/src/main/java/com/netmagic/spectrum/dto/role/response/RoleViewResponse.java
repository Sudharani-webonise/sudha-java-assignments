package com.netmagic.spectrum.dto.role.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the role view details response
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
public class RoleViewResponse implements Serializable {

    private static final long serialVersionUID = -1368696556923133847L;

    @JsonProperty("rolePermissionBean")
    private List<Roles> roles;
}
