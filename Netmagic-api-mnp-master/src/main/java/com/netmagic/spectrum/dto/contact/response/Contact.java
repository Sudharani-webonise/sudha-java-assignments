package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the values of a single contact on the contact list page
 * 
 * @author Pareekshit
 *
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = -3622658138761580138L;

    @JsonProperty("contactId")
    private Long contactId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("contactType")
    private String contactType;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("associateCustomerName")
    private String associateCustomerName;
    @JsonProperty("listOfProjects")
    private List<String> projects;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("contactNumber")
    private String contactNumber;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAssociateCustomerName() {
        return associateCustomerName;
    }

    public void setAssociateCustomerName(String associateCustomerName) {
        this.associateCustomerName = associateCustomerName;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
