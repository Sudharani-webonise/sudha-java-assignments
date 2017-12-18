package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;
import java.util.List;

/***
 * This DTO consists the details of mobile contacts
 * 
 * @author webonise
 *
 */
public class MobileContact implements Serializable {

    private static final long serialVersionUID = 5119018626388372886L;

    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
