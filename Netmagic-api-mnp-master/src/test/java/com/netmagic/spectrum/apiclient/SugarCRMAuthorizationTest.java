package com.netmagic.spectrum.apiclient;

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
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.service.request.Token;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ SugarCRMAuthorization.class })
public class SugarCRMAuthorizationTest {

    @InjectMocks
    private SugarCRMAuthorization sugarCRMAuthorizationSpy;

    private static final String SERVICE_WIDGET_RESPONSE = "responses/serviceLedger/serviceWidgetResponse.json";

    @Mock
    private CacheService<Token> tokenCacheServiceMock;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Test
    public void testFetchServiceToken() throws Exception {
        Token token = new Token();
        token.setToken(MockData.TOKEN.getString());
        Mockito.when(tokenCacheServiceMock.get(Mockito.any(Token.class))).thenReturn(token);
        String tokenResult = Whitebox.invokeMethod(sugarCRMAuthorizationSpy, "fetchServiceToken");
        assertNotNull(tokenResult);
        assertEquals(token.getToken(), tokenResult);
    }

    @Test
    public void testGenerateServiceToken() throws Exception {
        String serviceWigetResponse = ResourceLoader.readFile(SERVICE_WIDGET_RESPONSE);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(serviceWigetResponse);
        String result = Whitebox.invokeMethod(sugarCRMAuthorizationSpy, "generateServiceToken");
        assertNotNull(result);
    }

}
