package com.netmagic.spectrum.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.netmagic.spectrum.dto.asset.request.AssetListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagRequest;
import com.netmagic.spectrum.dto.asset.request.WidgetRequest;
import com.netmagic.spectrum.dto.asset.response.AssetList;
import com.netmagic.spectrum.dto.asset.response.AssetTagList;
import com.netmagic.spectrum.dto.asset.response.WidgetData;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface AssetService {
    /**
     * This method fetches the widget data with different asset category
     *
     * @param widgetRequest
     * @return
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('AST_WDGT')")
    WidgetData getWidgetData(WidgetRequest widgetRequest) throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the asset list data with different asset category
     * 
     * @param assetListRequest
     * @return {@link AssetList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('AST_LIST')")
    AssetList getAssetList(AssetListRequest assetListRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the asset tag list
     * 
     * @param assetTagRequest
     * @return {@link AssetTagList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('AST_LIST')")
    AssetTagList getAssetTagList(AssetTagListRequest assetTagRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method returns success/failure while adding a tag to an asset
     * 
     * @param assetTagAddRequest
     * @return "Success/Failure"
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('AST_LIST')")
    String addAssetTag(AssetTagRequest assetTagAddRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method returns success/failure while removing a tag from an asset
     * 
     * @param assetTagRemoveRequest
     * @return "Success/Failure"
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('AST_LIST')")
    String removeAssetTag(AssetTagRequest assetTagRemoveRequest)
            throws RequestViolationException, ServiceUnavailableException;
}
