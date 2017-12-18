package com.netmagic.spectrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.contact.request.AddContactRequest;
import com.netmagic.spectrum.dto.contact.request.EditContactRequest;
import com.netmagic.spectrum.dto.contact.request.InviteContact;
import com.netmagic.spectrum.dto.contact.request.ListRequest;
import com.netmagic.spectrum.dto.contact.request.WidgetRequest;
import com.netmagic.spectrum.dto.contact.response.CallingConfig;
import com.netmagic.spectrum.dto.contact.response.ContactDetails;
import com.netmagic.spectrum.dto.contact.response.ContactResponse;
import com.netmagic.spectrum.dto.contact.response.ContactSubTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactWidget;
import com.netmagic.spectrum.dto.contact.response.InternalUserContact;
import com.netmagic.spectrum.dto.contact.response.ListResponse;
import com.netmagic.spectrum.service.ContactService;

@RequestMapping("/api/contacts")
@RestController
public class ContactController {

    private static final String DEFAULT_CONTACT_TYPE = "All";

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/widget/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactWidget fetchWidgetData(@PathVariable(value = "customerId") Long customerId,
            @RequestParam(value = "associateCustomerId", required = false) Long associatedCustomerId,
            @RequestParam(value = "project", required = false) String project) {
        WidgetRequest widgetRequest = new WidgetRequest(customerId, associatedCustomerId, project);
        return contactService.getWidgetData(widgetRequest);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ListResponse fetchListData(@PathVariable(value = "customerId") Long customerId,
            @RequestParam(value = "associateCustomerId", required = false) Long associatedCustomerId,
            @RequestParam(value = "project", required = false) String project,
            @RequestParam(value = "contactType", defaultValue = DEFAULT_CONTACT_TYPE) String contactType) {
        ListRequest listRequest = new ListRequest(customerId, associatedCustomerId, project, contactType);
        return contactService.getListData(listRequest);
    }

    @RequestMapping(value = "/details/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactDetails fetchContactDetails(@PathVariable(value = "userId") Long userId) {
        return contactService.getContactDetails(userId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactResponse editContact(@RequestBody final EditContactRequest updatedContactRequest) {
        return contactService.updateContactInformation(updatedContactRequest);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactResponse addNewContact(@RequestBody final AddContactRequest addContactRequest) {
        return contactService.addContact(addContactRequest);
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContactResponse inviteNewContact(@RequestBody final InviteContact inviteContactRequest) {
        return contactService.inviteUser(inviteContactRequest);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactTypeList fetchContactTypes() {
        return contactService.getContactTypes();
    }

    @RequestMapping(value = "/subtypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactSubTypeList fetchContactSubTypes(@RequestParam(value = "typeId") Long typeId) {
        return contactService.getContactSubTypes(typeId);
    }

    @RequestMapping(value = "/calling/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CallingConfig fetchContactCallingConfig() {
        return contactService.getContactCallingConfig();
    }

    @RequestMapping(value = "/internal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public InternalUserContact fetchContacDetails(@RequestParam("mainCustomerId") Long mainCustomerId,
            @RequestParam("associateCustomerId") Long associatedCustomerId) {
        return contactService.getInternalUserContacts(mainCustomerId, associatedCustomerId);
    }
}
