package com.netmagic.spectrum.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResourceHelper {

    /**
     * This method used to map the object to json string
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String objectAsJsonString(final Object object) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(object);
        return jsonContent;
    }

}
