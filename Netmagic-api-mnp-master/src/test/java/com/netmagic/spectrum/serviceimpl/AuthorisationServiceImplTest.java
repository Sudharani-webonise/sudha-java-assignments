package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.authorisation.ModuleFunctionalityMapping;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.user.response.ModuleInformation;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.AuthorisationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ AuthorisationServiceImpl.class })
public class AuthorisationServiceImplTest {

    @InjectMocks
    private AuthorisationServiceImpl authorisationServiceSpy;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private CacheService<ModuleFunctionalityMapping> moduleFunctionalityCacheServiceMock;

    private static final String MODULE_FUNCTIONALITY_MAPPING = "responses/authorisation/modulesMappingList.json";

    @Test
    public void testGetModulesFunctionalitiesInCache() throws Exception {
        List<ModuleInformation> moduleInformations = ResourceLoader.readAndGetObject(MODULE_FUNCTIONALITY_MAPPING,
                new TypeReference<List<ModuleInformation>>() {
                });
        ModuleFunctionalityMapping functionalityMapping = new ModuleFunctionalityMapping();
        functionalityMapping.setModules(moduleInformations);
        Mockito.when(moduleFunctionalityCacheServiceMock.get(Mockito.any())).thenReturn(functionalityMapping);
        ModuleFunctionalityMapping moduleFunctionalityMapping = Whitebox
                .invokeMethod(moduleFunctionalityCacheServiceMock, "get", new ModuleFunctionalityMapping());
        Mockito.verify(apiClientMock, times(0)).performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ModuleFunctionalityMapping>> any());
        List<ModuleInformation> modules = Whitebox.invokeMethod(authorisationServiceSpy, "getModulesFunctionalities");
        assertNotNull("response of getModulesFunctionalities should not be null", modules);
        assertEquals("response of cached data must be equal to mock data", functionalityMapping,
                moduleFunctionalityMapping);
        assertEquals("response of getModulesFunctionalities must be equal to mock data", moduleInformations, modules);
    }

    @Test
    public void testGetModulesFunctionalitiesInCacheNull() throws Exception {
        List<ModuleInformation> moduleInformations = ResourceLoader.readAndGetObject(MODULE_FUNCTIONALITY_MAPPING,
                new TypeReference<List<ModuleInformation>>() {
                });
        ModuleFunctionalityMapping functionalityMapping = new ModuleFunctionalityMapping();
        functionalityMapping.setModules(moduleInformations);
        Mockito.when(moduleFunctionalityCacheServiceMock.get(Mockito.any())).thenReturn(null);
        ModuleFunctionalityMapping moduleFunctionalityMapping = Whitebox
                .invokeMethod(moduleFunctionalityCacheServiceMock, "get", new ModuleFunctionalityMapping());
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ModuleFunctionalityMapping>> any()))
                .thenReturn(functionalityMapping);
        List<ModuleInformation> modules = Whitebox.invokeMethod(authorisationServiceSpy, "getModulesFunctionalities");
        assertNull("response of cached data must be null", moduleFunctionalityMapping);
        assertNotNull("response of getModulesFunctionalities should not be null", modules);
        assertEquals("response of getModulesFunctionalities must be equal to mock data", moduleInformations, modules);
    }

}
