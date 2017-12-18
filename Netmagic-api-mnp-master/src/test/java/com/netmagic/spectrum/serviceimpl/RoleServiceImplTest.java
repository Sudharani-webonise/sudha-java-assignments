package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Status;
import com.netmagic.spectrum.dto.role.request.RoleAddRequest;
import com.netmagic.spectrum.dto.role.request.RoleViewRequest;
import com.netmagic.spectrum.dto.role.response.RoleViewResponse;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.RoleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ RoleServiceImpl.class })
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleServiceSpy;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private RoleViewResponse roleViewResponseMock;

    @Mock
    private AuthUser authUser;

    private static final String USER_ROLE_DETAIL_VALID_RESPONSE = "responses/role/viewRoleDetailValidResponse.json";

    private static final String USER_ROLE_DETAIL_VALID_REQUEST = "requests/role/viewRoleDetailValidRequest.json";

    private static final String USER_ROLE_ADD_DETAIL_VALID_REQUEST = "requests/role/addRoleDetailValidRequest.json";

    @Test
    public void testViewRoleDetailsWithValidRequest() throws Exception {
        RoleViewResponse viewRoleDetailsResponseExpected = ResourceLoader
                .readAndGetObject(USER_ROLE_DETAIL_VALID_RESPONSE, RoleViewResponse.class);
        performPostTestCall(viewRoleDetailsResponseExpected);
        RoleViewRequest viewRoleRequest = ResourceLoader.readAndGetObject(USER_ROLE_DETAIL_VALID_REQUEST,
                RoleViewRequest.class);
        RoleViewResponse viewRoleDetailsResponseActual = Whitebox.invokeMethod(roleServiceSpy, "getRoleDetails",
                viewRoleRequest);
        assertNotNull("response of getRoleDetails should not be null", viewRoleDetailsResponseActual);
        assertEquals("response of getRoleDetails must be equal to mock data", viewRoleDetailsResponseExpected,
                viewRoleDetailsResponseActual);
    }

    @Test
    public void testAddRoleDetailsWithValidRequest() throws Exception {
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(Status.SUCCESS.getStatus());
        RoleAddRequest addRoleRequest = ResourceLoader.readAndGetObject(USER_ROLE_ADD_DETAIL_VALID_REQUEST,
                RoleAddRequest.class);
        String addRoleResponseActual = Whitebox.invokeMethod(roleServiceSpy, "addOrUpdateRole", addRoleRequest);
        assertEquals("success response after add/edit/update role details", Status.SUCCESS.getStatus(),
                addRoleResponseActual);
    }

    private void performPostTestCall(RoleViewResponse viewRoleDetailsResponseExpected) {
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<RoleViewResponse>> any()))
                .thenReturn(viewRoleDetailsResponseExpected);
    }

}
