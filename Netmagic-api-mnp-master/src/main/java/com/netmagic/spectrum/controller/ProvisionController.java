package com.netmagic.spectrum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.provision.LineItemDetails;
import com.netmagic.spectrum.dto.provision.SofDetails;
import com.netmagic.spectrum.service.ProvisionService;

@RequestMapping(value = "/api/provision")
@RestController
public class ProvisionController {

    @Autowired
    private ProvisionService provisionService;

    @RequestMapping(value = "/sofDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SofDetails> getProvisionSofData(@RequestParam String billToCustomer,
            @RequestParam String supportToCustomer, @RequestParam(required = false) String projectName) {
        return provisionService.getProvisionSofData(billToCustomer, supportToCustomer, projectName);
    }

    @RequestMapping(value = "/lineItemDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public LineItemDetails getProvisionLineItemData(@RequestParam Long sofNumber, @RequestParam Long lineItemNumber) {
        return provisionService.getProvisionLineItemData(sofNumber, lineItemNumber);
    }

}
