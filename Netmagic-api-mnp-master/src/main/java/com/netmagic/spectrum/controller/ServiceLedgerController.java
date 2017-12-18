package com.netmagic.spectrum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.service.request.LineItemsRestData;
import com.netmagic.spectrum.dto.service.request.SOFListRestData;
import com.netmagic.spectrum.dto.service.request.WidgetRestData;
import com.netmagic.spectrum.dto.service.response.SOF;
import com.netmagic.spectrum.dto.service.response.ServiceLineItem;
import com.netmagic.spectrum.dto.service.response.ServiceWidget;
import com.netmagic.spectrum.service.ServiceLedgerService;

@RequestMapping(value = "/api/services")
@RestController
public class ServiceLedgerController {

    private static final String DEFAULT_SOF_STATUS = "Active";

    @Autowired
    private ServiceLedgerService ledgerService;

    @RequestMapping(value = "/sofs/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SOF> getSOFList(@PathVariable("customerId") String customerId,
            @RequestParam(value = "associateCustomerId", required = false) String associateCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "page", required = false) Long page,
            @RequestParam(value = "statusCode", defaultValue = DEFAULT_SOF_STATUS) String statusCode) {
        SOFListRestData restData = new SOFListRestData();
        restData.setCustomerId(customerId);
        restData.setSupportId(associateCustomerId);
        restData.setProjectId(projectId);
        restData.setPage(page);
        restData.setStatusCode(statusCode);
        return ledgerService.getSOFList(restData);
    }

    @RequestMapping(value = "/line-items/{sofNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceLineItem> getLineItems(@PathVariable("sofNumber") String sofNumber,
            @RequestParam(value = "page", required = false) Long page,
            @RequestParam(value = "statusCode", defaultValue = DEFAULT_SOF_STATUS) String statusCode) {
        LineItemsRestData restData = new LineItemsRestData();
        restData.setContractNumber(sofNumber);
        restData.setStatusCode(statusCode);
        restData.setPage(page);
        return ledgerService.getLineItems(restData);
    }

    @RequestMapping(value = "/widget/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceWidget getServiceWidgetData(@PathVariable("customerId") String customerId,
            @RequestParam(value = "associateCustomerId") String associateCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        WidgetRestData restData = new WidgetRestData(customerId, associateCustomerId, projectId);
        return ledgerService.getWidgetData(restData);
    }
}