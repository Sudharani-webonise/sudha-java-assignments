package com.netmagic.spectrum.apiclient;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netmagic.spectrum.commons.Param;

/**
 * This class is used to add authorization in header while calling outstanding
 * API
 * 
 * @author Pareekshit
 *
 */
@Component
public class OustandingApiAuthorization {

    private static final String COLON = ":";

    private static final String ISO_8859 = "ISO-8859-1";

    private static final String BASIC = "Basic ";

    private static final String AUTHORIZATION = "Authorization";

    @Value("${outstanding-api-username}")
    private String outstandingUsername;

    @Value("${outstanding-api-password}")
    private String outstandingPassword;

    public Map<String, String> getAuthorization() {
        Map<String, String> header = new HashMap<String, String>();
        String dataToEncode = String.format(Param.THREE.getParam(), outstandingUsername, COLON, outstandingPassword);
        String encodedData = Base64.getEncoder().encodeToString(dataToEncode.getBytes(Charset.forName(ISO_8859)));
        String authorization = String.format(Param.TWO.getParam(), BASIC, encodedData);
        header.put(AUTHORIZATION, authorization);
        return header;
    }

}
