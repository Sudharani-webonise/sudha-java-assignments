package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the values of the calling configuration
 * 
 * @author webonise
 *
 */
public class CallingConfig implements Serializable {

    private static final long serialVersionUID = 8249204808134541840L;

    @JsonProperty("mynmContactCallingTypeBeans")
    private List<CallingDays> mynmContactCallingTypeBeans;

    @JsonProperty("mynmContactNumberTypeBeans")
    private List<ContactNumberCategory> mynmContactNumberTypeBeans;

    @JsonProperty("mynmContactHoursTypeBeans")
    private List<String> contactCallingHours;

    public List<CallingDays> getMynmContactCallingTypeBeans() {
        return mynmContactCallingTypeBeans;
    }

    public void setMynmContactCallingTypeBeans(List<CallingDays> mynmContactCallingTypeBeans) {
        this.mynmContactCallingTypeBeans = mynmContactCallingTypeBeans;
    }

    public List<ContactNumberCategory> getMynmContactNumberTypeBeans() {
        return mynmContactNumberTypeBeans;
    }

    public void setMynmContactNumberTypeBeans(List<ContactNumberCategory> mynmContactNumberTypeBeans) {
        this.mynmContactNumberTypeBeans = mynmContactNumberTypeBeans;
    }

    public List<String> getContactCallingHours() {
        return contactCallingHours;
    }

    public void setContactCallingHours(List<String> contactCallingHours) {
        this.contactCallingHours = contactCallingHours;
    }
}
