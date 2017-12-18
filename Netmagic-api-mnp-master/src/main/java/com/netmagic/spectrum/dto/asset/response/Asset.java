package com.netmagic.spectrum.dto.asset.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.utils.DateUtils;

/**
 * This class holds the details of a asset
 * 
 * @author webonise
 *
 */
public class Asset implements Serializable {

    private static final long serialVersionUID = 549543952581731663L;

    @JsonProperty("assetId")
    private Long assetId;
    @JsonProperty("assetDateOfEntry")
    private String assetDateOfEntry;
    @JsonProperty("assetHardwareId")
    private String assetHardwareId;
    @JsonProperty("assetRackCageInformation")
    private String assetRackCageInformation;
    @JsonProperty("assetType")
    private String assetType;
    @JsonProperty("assetDesc")
    private String assetDesc;
    @JsonProperty("assetMake")
    private String assetMake;
    @JsonProperty("warrantyExpiryFrom")
    private String warrantyExpiryFrom;
    @JsonProperty("warrantyExpiryTo")
    private String warrantyExpiryTo;
    @JsonProperty("vendor")
    private String vendor;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("hostName")
    private String hostName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("assetCurrentLocation")
    private String assetCurrentLocation;
    @JsonProperty("from_U")
    private String fromU;
    @JsonProperty("to_U")
    private String toU;
    @JsonProperty("privateIpAddress")
    private String privateIpAddress;
    @JsonProperty("publicIp1")
    private String publicIp1;
    @JsonProperty("publicIp2")
    private String publicIp2;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("databaseIp")
    private String databaseIp;
    @JsonProperty("managed_OS")
    private String managedOS;
    @JsonProperty("managed_application")
    private String managedApplication;
    @JsonProperty("managed_database")
    private String managedDatabase;
    @JsonProperty("network_managed")
    private String networkManaged;
    @JsonProperty("urlMonitoring")
    private String urlMonitoring;
    @JsonProperty("bmcInstance")
    private String bmcInstance;
    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("assetModel")
    private String assetModel;
    @JsonProperty("assetLineItemNumber")
    private String assetLineItemNumber;
    @JsonProperty("assetSOFNumber")
    private String assetSOFNumber;
    @JsonProperty("assetTag")
    private String assetTag;
    @JsonProperty("virtualInstanceName")
    private String virtualInstanceName;
    @JsonProperty("billToCustomerDetails")
    private CustomerDetails billToCustomerDetails;
    @JsonProperty("ownerToCustomerDetails")
    private CustomerDetails ownerToCustomerDetails;
    @JsonProperty("assignToCustomerDetails")
    private CustomerDetails assignToCustomerDetails;
    @JsonProperty("osVersion")
    private String osVersion;
    @JsonProperty("manageByNetmagic")
    private String osManageByNetmagic;

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetDateOfEntry() {
        return assetDateOfEntry;
    }

    public void setAssetDateOfEntry(String assetDateOfEntry) {
        this.assetDateOfEntry = DateUtils.changeGmtMsToDDMMYYYY(assetDateOfEntry);
    }

    public String getAssetHardwareId() {
        return assetHardwareId;
    }

    public void setAssetHardwareId(String assetHardwareId) {
        this.assetHardwareId = assetHardwareId;
    }

    public String getAssetRackCageInformation() {
        return assetRackCageInformation;
    }

    public void setAssetRackCageInformation(String assetRackCageInformation) {
        this.assetRackCageInformation = assetRackCageInformation;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public String getWarrantyExpiryFrom() {
        return warrantyExpiryFrom;
    }

    public void setWarrantyExpiryFrom(String warrantyExpiryFrom) {
        this.warrantyExpiryFrom = DateUtils.changeGmtMsToDDMMYYYY(warrantyExpiryFrom);
    }

    public String getWarrantyExpiryTo() {
        return warrantyExpiryTo;
    }

    public void setWarrantyExpiryTo(String warrantyExpiryTo) {
        this.warrantyExpiryTo = DateUtils.changeGmtMsToDDMMYYYY(warrantyExpiryTo);
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssetCurrentLocation() {
        return assetCurrentLocation;
    }

    public void setAssetCurrentLocation(String assetCurrentLocation) {
        this.assetCurrentLocation = assetCurrentLocation;
    }

    public String getFromU() {
        return fromU;
    }

    public void setFromU(String fromU) {
        this.fromU = fromU;
    }

    public String getToU() {
        return toU;
    }

    public void setToU(String toU) {
        this.toU = toU;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public String getPublicIp1() {
        return publicIp1;
    }

    public void setPublicIp1(String publicIp1) {
        this.publicIp1 = publicIp1;
    }

    public String getPublicIp2() {
        return publicIp2;
    }

    public void setPublicIp2(String publicIp2) {
        this.publicIp2 = publicIp2;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDatabaseIp() {
        return databaseIp;
    }

    public void setDatabaseIp(String databaseIp) {
        this.databaseIp = databaseIp;
    }

    public String getManagedOS() {
        return managedOS;
    }

    public void setManagedOS(String managedOS) {
        this.managedOS = managedOS;
    }

    public String getManagedApplication() {
        return managedApplication;
    }

    public void setManagedApplication(String managedApplication) {
        this.managedApplication = managedApplication;
    }

    public String getManagedDatabase() {
        return managedDatabase;
    }

    public void setManagedDatabase(String managedDatabase) {
        this.managedDatabase = managedDatabase;
    }

    public String getNetworkManaged() {
        return networkManaged;
    }

    public void setNetworkManaged(String networkManaged) {
        this.networkManaged = networkManaged;
    }

    public String getUrlMonitoring() {
        return urlMonitoring;
    }

    public void setUrlMonitoring(String urlMonitoring) {
        this.urlMonitoring = urlMonitoring;
    }

    public String getBmcInstance() {
        return bmcInstance;
    }

    public void setBmcInstance(String bmcInstance) {
        this.bmcInstance = bmcInstance;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(String assetModel) {
        this.assetModel = assetModel;
    }

    public String getAssetLineItemNumber() {
        return assetLineItemNumber;
    }

    public void setAssetLineItemNumber(String assetLineItemNumber) {
        this.assetLineItemNumber = assetLineItemNumber;
    }

    public String getAssetSOFNumber() {
        return assetSOFNumber;
    }

    public void setAssetSOFNumber(String assetSOFNumber) {
        this.assetSOFNumber = assetSOFNumber;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getVirtualInstanceName() {
        return virtualInstanceName;
    }

    public void setVirtualInstanceName(String virtualInstanceName) {
        this.virtualInstanceName = virtualInstanceName;
    }

    public CustomerDetails getBillToCustomerDetails() {
        return billToCustomerDetails;
    }

    public void setBillToCustomerDetails(CustomerDetails billToCustomerDetails) {
        this.billToCustomerDetails = billToCustomerDetails;
    }

    public CustomerDetails getOwnerToCustomerDetails() {
        return ownerToCustomerDetails;
    }

    public void setOwnerToCustomerDetails(CustomerDetails ownerToCustomerDetails) {
        this.ownerToCustomerDetails = ownerToCustomerDetails;
    }

    public CustomerDetails getAssignToCustomerDetails() {
        return assignToCustomerDetails;
    }

    public void setAssignToCustomerDetails(CustomerDetails assignToCustomerDetails) {
        this.assignToCustomerDetails = assignToCustomerDetails;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsManageByNetmagic() {
        return osManageByNetmagic;
    }

    public void setOsManageByNetmagic(String osManageByNetmagic) {
        this.osManageByNetmagic = osManageByNetmagic;
    }
}
