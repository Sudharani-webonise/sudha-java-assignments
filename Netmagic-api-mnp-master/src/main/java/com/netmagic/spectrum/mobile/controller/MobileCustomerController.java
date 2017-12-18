package com.netmagic.spectrum.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.MobileContact;
import com.netmagic.spectrum.dto.customer.MobileProject;
import com.netmagic.spectrum.mobile.service.MobileCustomerService;

@RequestMapping(value = "/mobile/api/customer")
@RestController
public class MobileCustomerController {

    @Autowired
    private MobileCustomerService mobileCustomerService;

    @RequestMapping(value = "/associated/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AssociatedCustomerListResponse getAssociateMobileCustomers(@PathVariable("customerId") Long customerId) {
        return mobileCustomerService.getAssociatedMobileCustomers(customerId);
    }

    @RequestMapping(value = "/{customerId}/associated/{assoCustomerId}/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MobileProject getMobileProjects(@PathVariable("customerId") Long customerId,
            @PathVariable("assoCustomerId") Long associatedCustomerId) {
        return mobileCustomerService.getMobileProjects(customerId, associatedCustomerId);
    }

    @RequestMapping(value = "/{customerId}/associated/{assoCustomerId}/contacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MobileContact getMobileContacts(@PathVariable("customerId") Long customerId,
            @PathVariable("assoCustomerId") Long associatedCustomerId,
            @RequestParam(value = "projectname", required = false) String projectName) {
        return mobileCustomerService.getMobileContacts(customerId, associatedCustomerId, projectName);
    }
}
