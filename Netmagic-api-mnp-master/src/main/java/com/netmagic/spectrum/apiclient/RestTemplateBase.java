package com.netmagic.spectrum.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBase {

    @Autowired
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> performRequest(String url, HttpMethod method,
            HttpEntity<?> requestEntity, Class<T> clazz) {
        return restTemplate.exchange(url, method, requestEntity, clazz);
    }

    public <T> ResponseEntity<T> performRequest(String url, HttpMethod method,
            HttpEntity<?> requestEntity, ParameterizedTypeReference<T> parameterizedTypeReference) {
        return restTemplate.exchange(url, method, requestEntity, parameterizedTypeReference);
    }
}
