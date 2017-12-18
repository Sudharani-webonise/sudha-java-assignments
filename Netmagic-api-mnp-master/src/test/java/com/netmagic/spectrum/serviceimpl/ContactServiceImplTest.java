package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.contact.request.AddContactRequest;
import com.netmagic.spectrum.dto.contact.request.EditContactRequest;
import com.netmagic.spectrum.dto.contact.request.InviteContact;
import com.netmagic.spectrum.dto.contact.request.ListRequest;
import com.netmagic.spectrum.dto.contact.request.WidgetRequest;
import com.netmagic.spectrum.dto.contact.response.CachedContactSubTypes;
import com.netmagic.spectrum.dto.contact.response.CallingConfig;
import com.netmagic.spectrum.dto.contact.response.ContactDetails;
import com.netmagic.spectrum.dto.contact.response.ContactResponse;
import com.netmagic.spectrum.dto.contact.response.ContactSubTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactWidget;
import com.netmagic.spectrum.dto.contact.response.InternalUserContact;
import com.netmagic.spectrum.dto.contact.response.ListResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.ContactServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ContactServiceImpl.class })
public class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactServiceSpy;

    private static final String CONTACT_TYPE = "Billing";

    private static final String WIDGET_RESPONSE_FILE = "responses/contacts/contactWidgetResponse.json";

    private static final String LIST_RESPONSE_FILE = "responses/contacts/contactListResponse.json";

    private static final String DETAILS_RESPONSE_FILE = "responses/contacts/contactDetailsResponse.json";

    private static final String ADD_CONTACT_RESPONSE_FILE = "responses/contacts/addContactResponse.json";

    private static final String ADD_CONTACT_EXISTING_MAIL_RESPONSE_FILE = "responses/contacts/addContactExistingEmailResponse.json";

    private static final String ADD_CONTACT_VALID_REQUEST = "requests/contacts/AddContactRequest.json";

    private static final String ADD_CONTACT_INVLAID_REQUEST = "requests/contacts/AddContactRequestWithExistingEmail.json";

    private static final String EDIT_CONTACT_SUCCESS_FILE = "responses/contacts/editContactValidIdResponse.json";

    private static final String EDIT_CONTACT_INVALID_RESPONSE_FILE = "responses/contacts/editContactNullCustomerIdResponse.json";

    private static final String EDIT_CONTACT_VALID_USER_REQUEST = "requests/contacts/EditContactRequest.json";

    private static final String EDIT_CONTACT_INVALID_USER_REQUEST = "requests/contacts/EditContactRequestWithNullUserId.json";

    private static final String INVITE_CONTACT_VALID_REQUEST = "requests/contacts/inviteContactRequest.json";

    private static final String INVITE_CONTACT_SUCCESS_FILE = "responses/contacts/inviteContactResponse.json";

    private static final String INVITE_CONTACT_EXISTING_MAIL = "responses/contacts/inviteContactExistingEmailResponse.json";

    private static final String CONTACT_TYPES_RESPONSE = "responses/contacts/contactTypes.json";

    private static final String CONTACT_SUB_TYPES_RESPONSE = "responses/contacts/contactSubTypes.json";

    private static final String CONTACT_CALLING_RESPONSE = "responses/contacts/callingConfig.json";

    private static final String INTERNAL_CUSTOMER_CONTACTS = "responses/contacts/internalCustomerContactResponse.json";

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ListResponse contactListMock;

    @Mock
    private ContactDetails contactDetailsMock;

    @Mock
    private ContactResponse contactResponseMock;

    @Mock
    private CacheService<CachedContactSubTypes> cachedContactSubTypeServiceMock;

    @Test
    public void testGetWidgetDataShouldGiveValidData() throws Exception {
        ContactWidget contactWidget = ResourceLoader.readAndGetObject(WIDGET_RESPONSE_FILE, ContactWidget.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactWidget>> any()))
                .thenReturn(contactWidget);
        WidgetRequest widgetRequest = new WidgetRequest(MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.MAIN_CUSTOMER_ID.getLong(), null);
        ContactWidget contactWidgetData = Whitebox.invokeMethod(contactServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", contactWidgetData);
        assertEquals("response of getWidgetData must be equal to mock data", contactWidget, contactWidgetData);
    }

    @Test
    public void testGetListDataShouldGiveValidData() throws Exception {
        ListResponse contactList = ResourceLoader.readAndGetObject(LIST_RESPONSE_FILE, ListResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ListResponse>> any()))
                .thenReturn(contactList);
        ListRequest listRequest = new ListRequest(MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.MAIN_CUSTOMER_ID.getLong(), null, CONTACT_TYPE);
        ListResponse contactListData = Whitebox.invokeMethod(contactServiceSpy, "getListData", listRequest);
        assertNotNull("response of getListData should not be null", contactListData);
        assertEquals("response of getListData must be equal to mock data", contactList, contactListData);
    }

    @Test
    public void testGetContactDetailsShouldGiveValidData() throws Exception {
        ContactDetails contactDetails = ResourceLoader.readAndGetObject(DETAILS_RESPONSE_FILE, ContactDetails.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactDetails>> any()))
                .thenReturn(contactDetails);
        ContactDetails contactDetailsData = Whitebox.invokeMethod(contactServiceSpy, "getContactDetails",
                MockData.CONTACT_ID.getLong());
        assertNotNull("response of getContactDetails should not be null", contactDetailsData);
        assertEquals("response of getContactDetails must be equal to mock data", contactDetails, contactDetailsData);
    }

    @Test
    public void testAddContactWithNewEmailId() throws Exception {
        AddContactRequest newContactRequest = ResourceLoader.readAndGetObject(ADD_CONTACT_VALID_REQUEST,
                AddContactRequest.class);
        ContactResponse contactResponseData = ResourceLoader.readAndGetObject(ADD_CONTACT_RESPONSE_FILE,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(contactResponseData);
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "addContact", newContactRequest);
        assertNotNull("response of addContact should not be null", contactResponse);
        assertEquals("response of addContact must be equal to mock data", contactResponseData, contactResponse);
    }

    @Test
    public void testAddContactWithExistingEmailId() throws Exception {
        AddContactRequest newInvalidContactRequest = ResourceLoader.readAndGetObject(ADD_CONTACT_INVLAID_REQUEST,
                AddContactRequest.class);
        ContactResponse contactResponseData = ResourceLoader.readAndGetObject(ADD_CONTACT_EXISTING_MAIL_RESPONSE_FILE,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(contactResponseData);
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "addContact",
                newInvalidContactRequest);
        assertNotNull("response of addContact should not be null", contactResponse);
        assertEquals("response of addContact must be equal to mock data", contactResponseData, contactResponse);
    }

    @Test
    public void testEditContactWithExistingValidContactId() throws Exception {
        EditContactRequest editContactRequest = ResourceLoader.readAndGetObject(EDIT_CONTACT_VALID_USER_REQUEST,
                EditContactRequest.class);
        ContactResponse contactResponseData = ResourceLoader.readAndGetObject(EDIT_CONTACT_SUCCESS_FILE,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(contactResponseData);
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "updateContactInformation",
                editContactRequest);
        assertNotNull("response of updateContactInformation should not be null", contactResponse);
        assertEquals("response of updateContactInformation must be equal to mock data", contactResponseData,
                contactResponse);
    }

    @Test
    public void testEditContactWithNullContactId() throws Exception {
        EditContactRequest editContactInvalidRequest = ResourceLoader
                .readAndGetObject(EDIT_CONTACT_INVALID_USER_REQUEST, EditContactRequest.class);
        ContactResponse contactResponseData = ResourceLoader.readAndGetObject(EDIT_CONTACT_INVALID_RESPONSE_FILE,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(contactResponseData);
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "updateContactInformation",
                editContactInvalidRequest);
        assertNotNull("response of updateContactInformation should not be null", contactResponse);
        assertEquals("response of updateContactInformation must be equal to mock data", contactResponseData,
                contactResponse);
    }

    @Test(expected = RequestViolationException.class)
    public void testGetWidgetDatawithNullData() throws Exception {
        WidgetRequest widgetRequest = null;
        Whitebox.invokeMethod(contactServiceSpy, "getWidgetData", widgetRequest);
    }

    @Test
    public void testGetListDataReturnsNullWithoutCustomerId() throws Exception {
        Long nullCustomerId = null;
        Whitebox.setInternalState(contactServiceSpy, "mapper", new ObjectMapper());
        ListRequest listRequest = new ListRequest(nullCustomerId, nullCustomerId, null, CONTACT_TYPE);
        ListResponse contactListData = Whitebox.invokeMethod(contactServiceSpy, "getListData", listRequest);
        Mockito.verify(apiClientMock, Mockito.times(1)).performPost(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ListResponse>> any());
        assertNull("getListData should return null for null data", contactListData);
    }

    @Test
    public void testGetContactDetailsReturnsNullWithoutUserId() throws Exception {
        Long nullUserId = null;
        Whitebox.setInternalState(contactServiceSpy, "mapper", new ObjectMapper());
        ContactDetails contactListData = Whitebox.invokeMethod(contactServiceSpy, "getContactDetails", nullUserId);
        Mockito.verify(apiClientMock, Mockito.times(1)).performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactDetails>> any());
        assertNull("getContactDetails should return null data for null request data", contactListData);
    }

    @Test
    public void testAddContactWithNullRequest() throws Exception {
        ContactDetails newContactDetails = null;
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "addContact", newContactDetails);
        Mockito.verify(apiClientMock, Mockito.times(1)).performPost(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, String.class),
                Mockito.<Class<ContactResponse>> any());
        assertNull("response of addContact should return null data for null request data", contactResponse);
    }

    @Test
    public void testEditContactWithNullRequest() throws Exception {
        EditContactRequest editContactRequest = null;
        ContactResponse contactResponse = Whitebox.invokeMethod(contactServiceSpy, "updateContactInformation",
                editContactRequest);
        Mockito.verify(apiClientMock, Mockito.times(1)).performPost(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, String.class),
                Mockito.<Class<ContactResponse>> any());
        assertNull("response of updateContactInformation should return nulldata for null request", contactResponse);
    }

    @Test
    public void testInviteContactWithNewEmailId() throws Exception {
        InviteContact newContactRequest = ResourceLoader.readAndGetObject(INVITE_CONTACT_VALID_REQUEST,
                InviteContact.class);
        ContactResponse inviteContactResponse = ResourceLoader.readAndGetObject(INVITE_CONTACT_SUCCESS_FILE,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(inviteContactResponse);
        ContactResponse inviteResponse = Whitebox.invokeMethod(contactServiceSpy, "inviteUser", newContactRequest);
        assertNotNull("response of inviteUser should not be null", inviteResponse);
        assertEquals("response of inviteUser must be equal to mock data", inviteContactResponse, inviteResponse);
    }

    @Test
    public void testInviteContactWithExistingEmailId() throws Exception {
        InviteContact newContactRequest = ResourceLoader.readAndGetObject(INVITE_CONTACT_VALID_REQUEST,
                InviteContact.class);
        ContactResponse inviteContactResponse = ResourceLoader.readAndGetObject(INVITE_CONTACT_EXISTING_MAIL,
                ContactResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactResponse>> any()))
                .thenReturn(inviteContactResponse);
        ContactResponse inviteResponse = Whitebox.invokeMethod(contactServiceSpy, "inviteUser", newContactRequest);
        assertNotNull("response of inviteUser should not be null", inviteResponse);
        assertEquals("response of inviteUser must be equal to mock data", inviteContactResponse, inviteResponse);
    }

    @Test
    public void testGetContactTypes() throws Exception {
        ContactTypeList contactTypeList = ResourceLoader.readAndGetObject(CONTACT_TYPES_RESPONSE,
                ContactTypeList.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactTypeList>> any()))
                .thenReturn(contactTypeList);
        ContactTypeList contactTypes = Whitebox.invokeMethod(contactServiceSpy, "getContactTypes");
        assertNotNull("response of getContactTypes should not be null", contactTypes);
        assertEquals("response of getContactTypes must be equal to mock data", contactTypeList, contactTypes);
    }

    @Test
    public void testGetContactSubTypesFromCache() throws Exception {
        ContactSubTypeList contactSubTypeList = ResourceLoader.readAndGetObject(CONTACT_SUB_TYPES_RESPONSE,
                ContactSubTypeList.class);
        CachedContactSubTypes cachedContactSubTypes = new CachedContactSubTypes(MockData.CONTACT_TYPE_ID.getLong());
        cachedContactSubTypes.setContactSubTypes(contactSubTypeList);
        Mockito.when(cachedContactSubTypeServiceMock.get(new CachedContactSubTypes(MockData.CONTACT_TYPE_ID.getLong())))
                .thenReturn(cachedContactSubTypes);
        Mockito.verify(apiClientMock, times(0)).performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactSubTypeList>> any());
        CachedContactSubTypes contactSubTypes = Whitebox.invokeMethod(cachedContactSubTypeServiceMock, "get",
                new CachedContactSubTypes(MockData.CONTACT_TYPE_ID.getLong()));
        ContactSubTypeList contactTypes = Whitebox.invokeMethod(contactServiceSpy, "getContactSubTypes",
                MockData.CONTACT_TYPE_ID.getLong());
        assertNotNull("response of get method frm cache should not be null", contactSubTypes);
        assertNotNull("response of getContactSubTypes should not be null", contactTypes);
        assertEquals("response of get method from cache must be equal to mock data", cachedContactSubTypes,
                contactSubTypes);
        assertEquals("response of getContactSubTypes must be equal to mock data", contactSubTypeList, contactTypes);
    }

    @Test
    public void testGetContactSubTypesCacheNull() throws Exception {
        ContactSubTypeList contactSubTypeList = ResourceLoader.readAndGetObject(CONTACT_SUB_TYPES_RESPONSE,
                ContactSubTypeList.class);
        Mockito.when(cachedContactSubTypeServiceMock.get(new CachedContactSubTypes(MockData.CONTACT_TYPE_ID.getLong())))
                .thenReturn(null);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ContactSubTypeList>> any()))
                .thenReturn(contactSubTypeList);
        CachedContactSubTypes contactSubTypes = Whitebox.invokeMethod(cachedContactSubTypeServiceMock, "get",
                new CachedContactSubTypes(MockData.CONTACT_TYPE_ID.getLong()));
        ContactSubTypeList contactTypes = Whitebox.invokeMethod(contactServiceSpy, "getContactSubTypes",
                MockData.CONTACT_TYPE_ID.getLong());
        assertNull("response of get method from cache should be null", contactSubTypes);
        assertNotNull("response of getContactSubTypes should not be null", contactTypes);
        assertEquals("response of get method from cache must be equal to mock data", null, contactSubTypes);
        assertEquals("response of getContactSubTypes must be equal to mock data", contactSubTypeList, contactTypes);
    }

    @Test
    public void testGetContactCallingConfig() throws Exception {
        CallingConfig contactCallingConfig = ResourceLoader.readAndGetObject(CONTACT_CALLING_RESPONSE,
                CallingConfig.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<CallingConfig>> any()))
                .thenReturn(contactCallingConfig);
        CallingConfig callingConfig = Whitebox.invokeMethod(contactServiceSpy, "getContactCallingConfig");
        assertNotNull("response of getContactCallingConfig should not be null", callingConfig);
        assertEquals("response of getContactCallingConfig must be equal to mock data", contactCallingConfig,
                callingConfig);
    }

    @Test
    public void testGetInternalUserCustomerContacts() throws Exception {
        InternalUserContact internalUserContact = ResourceLoader.readAndGetObject(INTERNAL_CUSTOMER_CONTACTS,
                InternalUserContact.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<InternalUserContact>> any()))
                .thenReturn(internalUserContact);
        InternalUserContact internalUserContactActual = Whitebox.invokeMethod(contactServiceSpy,
                "getInternalUserContacts", MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        assertNotNull("response of getInternalUserCustomerContacts should not be null", internalUserContactActual);
        assertEquals("response of getInternalUserCustomerContacts must be equal to mock data",
                internalUserContactActual, internalUserContact);
    }
}
