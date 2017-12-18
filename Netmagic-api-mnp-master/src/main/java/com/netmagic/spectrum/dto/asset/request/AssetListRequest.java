package com.netmagic.spectrum.dto.asset.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the data for the asset list request.
 * 
 * @author webonise
 *
 */
public class AssetListRequest implements Serializable {

    private static final long serialVersionUID = -7151737299221010793L;

    @JsonProperty("mainCustomerId")
    private Long mainCustomerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("assetTypeName")
    private String assetTypeName;
    @JsonProperty("assetTypeId")
    private String assetTypeId;
    @JsonProperty("listOfAssetTagName")
    private List<String> assetTags;
    @JsonProperty("pageNumber")
    private String pageNumber;
    @JsonProperty("maxResults")
    private String maxResults;

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public Long getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(Long mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public List<String> getAssetTags() {
        return assetTags;
    }

    public void setAssetTags(List<String> assetTags) {
        this.assetTags = assetTags;
    }
}
