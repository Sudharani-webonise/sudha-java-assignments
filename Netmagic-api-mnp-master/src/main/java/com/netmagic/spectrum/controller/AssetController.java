package com.netmagic.spectrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.asset.request.AssetListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagRequest;
import com.netmagic.spectrum.dto.asset.request.WidgetRequest;
import com.netmagic.spectrum.dto.asset.response.AssetList;
import com.netmagic.spectrum.dto.asset.response.AssetTagList;
import com.netmagic.spectrum.dto.asset.response.WidgetData;
import com.netmagic.spectrum.service.AssetService;

@RequestMapping(value = "/api/assets")
@RestController
public class AssetController {

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/widget/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public WidgetData fetchWidgetData(@PathVariable("customerId") Long customerId,
            @RequestParam(value = "associateCustomerId") Long associateCustomerId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        WidgetRequest widgetRequest = new WidgetRequest(customerId, associateCustomerId, projectId);
        return assetService.getWidgetData(widgetRequest);

    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AssetList fetchAssetList(@RequestBody final AssetListRequest assetListRequest) {
        return assetService.getAssetList(assetListRequest);
    }

    @RequestMapping(value = "/tags/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AssetTagList fetchAssetTagList(@PathVariable("customerId") Long customerId,
            @RequestParam(value = "associateCustomerId") Long associateCustomerId) {
        AssetTagListRequest assetTagRequest = new AssetTagListRequest(customerId, associateCustomerId);
        return assetService.getAssetTagList(assetTagRequest);
    }

    @RequestMapping(value = "/tag/{assetId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addAssetTag(@PathVariable("assetId") Long assetId, @RequestParam(value = "tagName") String tagName) {
        AssetTagRequest assetTagAddRequest = new AssetTagRequest(assetId, tagName);
        return assetService.addAssetTag(assetTagAddRequest);
    }

    @RequestMapping(value = "/tag/remove/{assetId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String removeAssetTag(@PathVariable("assetId") Long assetId,
            @RequestParam(value = "tagName") String tagName) {
        AssetTagRequest assetTagRemoveRequest = new AssetTagRequest(assetId, tagName);
        return assetService.removeAssetTag(assetTagRemoveRequest);
    }
}
