package com.netmagic.spectrum.service.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Assets;
import com.netmagic.spectrum.dto.asset.request.AssetListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagRequest;
import com.netmagic.spectrum.dto.asset.request.WidgetRequest;
import com.netmagic.spectrum.dto.asset.response.AssetList;
import com.netmagic.spectrum.dto.asset.response.AssetTagList;
import com.netmagic.spectrum.dto.asset.response.WidgetData;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${time.hours-in-a-day}")
    private Long hoursInADay;

    @Override
    public WidgetData getWidgetData(WidgetRequest widgetRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Assets.WIDGET.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(widgetRequest), Collections.emptyMap(), WidgetData.class);

        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Asset Widget Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Asset Widget Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Asset Widget URI");
        }
    }

    @Override
    public AssetList getAssetList(AssetListRequest assetListRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Assets.LIST.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(assetListRequest), Collections.emptyMap(), AssetList.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Asset List Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Asset List Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Asset List URI");
        }

    }

    @Override
    public AssetTagList getAssetTagList(AssetTagListRequest assetTagRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Assets.TAG_LIST.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(assetTagRequest), Collections.emptyMap(), AssetTagList.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Asset Tag List Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Asset Tag List Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Asset Tag List URI");
        }
    }

    @Override
    public String addAssetTag(AssetTagRequest assetTagAddRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Assets.ADD_TAG.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(assetTagAddRequest), Collections.emptyMap(), String.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Asset Add Tag Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Asset Add Tag Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Asset Add Tag URI");
        }
    }

    @Override
    public String removeAssetTag(AssetTagRequest assetTagRemoveRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Assets.REMOVE_TAG.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(assetTagRemoveRequest), Collections.emptyMap(), String.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while removing Asset Tag Data", ex);
            throw new RequestViolationException("Error in json processing while removing Asset Tag Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Asset Remove Tag URI");
        }
    }
}
