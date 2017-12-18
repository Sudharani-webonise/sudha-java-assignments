package com.netmagic.spectrum.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class contains method which are used for API requests to NM Servers
 * 
 * @author webonise
 *
 */
@Component
public class ApiClient extends RestTemplateBase {

    private static final String WHITE_SPACES = "\\s+";

    private static final String EMPTY_STRING = "";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String ACCEPT = "Accept";

    public <T> T performGet(final String apiUrl, final String contentType, final Map<String, String> header,
            Class<T> clazz) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.GET.name(), contentType, null, header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.GET, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public <T> T performGet(final String apiUrl, final String contentType, final String accept,
            final Map<String, String> header, Class<T> clazz) {
        final HttpEntity<String> httpEntity = getSSOHttpRequestHeader(RequestMethod.GET.name(), contentType, accept,
                null, header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.GET, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public <T> T performGet(final String apiUrl, final String contentType, final Map<String, String> header,
            ParameterizedTypeReference<T> parameterizedTypeReference) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.GET.name(), contentType, null, header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.GET, httpEntity,
                parameterizedTypeReference);
        return responseEntity.getBody();
    }

    public <T> T performPost(final String apiUrl, final String contentType, final String requestBody,
            final Map<String, String> header, ParameterizedTypeReference<T> parameterizedTypeReference) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.POST.name(), contentType, requestBody,
                header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.POST, httpEntity,
                parameterizedTypeReference);
        return responseEntity.getBody();
    }

    public <T> T performPost(final String apiUrl, final String contentType, final String requestBody,
            final Map<String, String> header, Class<T> clazz) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.POST.name(), contentType, requestBody,
                header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.POST, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public <T> T performPut(final String apiUrl, final String contentType, final String requestBody,
            final Map<String, String> header, Class<T> clazz) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.PUT.name(), contentType, requestBody,
                header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.PUT, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public <T> T performDeleteCloud(final String apiUrl, final String contentType, final String accept,
            final Map<String, String> header, Class<T> clazz) {
        final HttpEntity<String> httpEntity = getSSOHttpRequestHeader(RequestMethod.DELETE.name(), contentType, accept,
                null, header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.DELETE, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public <T> T performDelete(final String apiUrl, final String contentType, final String requestBody,
            final Map<String, String> header, Class<T> clazz) {
        final HttpEntity<String> httpEntity = getHttpRequestHeader(RequestMethod.DELETE.name(), contentType,
                requestBody, header);
        final ResponseEntity<T> responseEntity = performRequest(apiUrl, HttpMethod.DELETE, httpEntity, clazz);
        return responseEntity.getBody();
    }

    public HttpEntity<String> getHttpRequestHeader(final String method, final String contentType,
            final String requestBody, final Map<String, String> header) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAll(header);
        if ( method.equalsIgnoreCase(RequestMethod.PUT.name()) || method.equalsIgnoreCase(RequestMethod.POST.name())
                || method.equalsIgnoreCase(RequestMethod.DELETE.name()) ) {
            headers.add(CONTENT_TYPE, contentType);
            return new HttpEntity<String>(requestBody, headers);
        }
        return new HttpEntity<String>(headers);
    }

    public HttpEntity<String> getSSOHttpRequestHeader(final String method, final String contentType,
            final String accept, final String requestBody, final Map<String, String> header) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAll(header);
        headers.add(CONTENT_TYPE, contentType);
        headers.add(ACCEPT, accept);
        return new HttpEntity<String>(requestBody, headers);
    }

    public String performGet(String urlString, String encoding) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new URL(urlString).openStream();
            return IOUtils.toString(inputStream, encoding).replaceAll(WHITE_SPACES, EMPTY_STRING);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
