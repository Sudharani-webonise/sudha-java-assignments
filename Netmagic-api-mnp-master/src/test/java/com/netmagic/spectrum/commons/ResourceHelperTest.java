package com.netmagic.spectrum.commons;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netmagic.spectrum.dto.user.request.UserAuthRequest;
import com.netmagic.spectrum.helpers.MockData;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ResourceHelper.class })
public class ResourceHelperTest {

    @InjectMocks
    private ResourceHelper resourceHelper;

    @Test
    public void testObjectAsJsonString() throws JsonProcessingException {
        UserAuthRequest authRequest = new UserAuthRequest();
        authRequest.setUserEmail(MockData.USER_EMAIL.getString());
        assertNotNull(ResourceHelper.objectAsJsonString(authRequest));
    }

}
