package com.netmagic.spectrum.dto.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class maps the request object for the mobile user authentication
 * Response
 * 
 * @author Webonise
 * @version Spectrum 1.0
 */
@JsonIgnoreProperties({ "allTicketsCount", "customerLastUpdatedOn", "countBySeverityBeans", "customerAssetsCount",
        "customerAlertsCount", "customerSugarId" })
public class MobileCustomerDetails extends CustomerDetails {

    private static final long serialVersionUID = 3287796902552240692L;

}
