package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the list of associate customer when associate customer list
 * is fetched
 * 
 * @author webonise
 *
 */
public class AssociatedCustomerListResponse implements Serializable {

    private static final long serialVersionUID = 2854024367112793735L;

    @JsonProperty("listOfSupportToCustomerInfo")
    private List<Customer> associatedCustomers;

    public List<Customer> getAssociatedCustomers() {
        return associatedCustomers;
    }

    public void setAssociatedCustomers(List<Customer> associatedCustomers) {
        this.associatedCustomers = associatedCustomers;
    }
}
