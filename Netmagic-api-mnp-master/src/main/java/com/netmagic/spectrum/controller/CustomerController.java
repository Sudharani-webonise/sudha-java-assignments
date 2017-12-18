package com.netmagic.spectrum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.dto.customer.ShoppingCartProject;
import com.netmagic.spectrum.service.CustomerService;

@RequestMapping(value = "/api/customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/associatedCustomers/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AssociatedCustomerListResponse getAssociateCustomers(@PathVariable("customerId") Long customerId) {
        return customerService.getAssociatedCustomers(customerId);
    }

    @RequestMapping(value = "/{customerId}/associated/{associateCustomerId}/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjects(@PathVariable("customerId") Long customerId,
            @PathVariable("associateCustomerId") Long associatedCustomerId) {
        return customerService.getProjects(customerId, associatedCustomerId);
    }

    @RequestMapping(value = "/{customerId}/associated/{associateCustomerId}/contacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> getContacts(@PathVariable("customerId") Long customerId,
            @PathVariable("associateCustomerId") Long associatedCustomerId,
            @RequestParam(value = "projectname", required = false) String projectName) {
        return customerService.getContacts(customerId, associatedCustomerId, projectName);
    }

    @RequestMapping(value = "/billingGroup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BillingGroupResponse getCustomerDetails(@RequestParam("customerId") Long customerId,
            @RequestParam("associateCustomerId") Long assoCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        return customerService.getBillToCutomerDetails(customerId, assoCustomerId, projectId);
    }

    @RequestMapping(value = "/combinationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fetchCombinationId(@RequestParam("customerId") Long customerId,
            @RequestParam("associateCustomerId") Long associatedCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        return customerService.getCombinationId(customerId, associatedCustomerId, projectId);
    }

    @RequestMapping(value = "/internal/associatedCustomers/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AssociatedCustomerListResponse fetchAllAssociateCustomers(@PathVariable("customerId") Long customerId) {
        return customerService.getAllAssociatedCustomers(customerId);
    }

    @RequestMapping(value = "/internal/{customerId}/associated/{associateCustomerId}/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> fetchAllProjects(@PathVariable("customerId") Long customerId,
            @PathVariable("associateCustomerId") Long associatedCustomerId) {
        return customerService.getAllProjects(customerId, associatedCustomerId);
    }

    @RequestMapping(value = "/projects/cart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjectsForCart(@RequestBody ShoppingCartProject cartProjectRequest) {
        return customerService.getProjectsForCart(cartProjectRequest);
    }

    @RequestMapping(value = "/address/{billToCustomer}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerAddress getProjectsForCart(@PathVariable String billToCustomer) {
        return customerService.getCustomerAddress(billToCustomer);
    }
}
