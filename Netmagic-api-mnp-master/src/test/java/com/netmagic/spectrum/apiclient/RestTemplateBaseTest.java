package com.netmagic.spectrum.apiclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.netmagic.spectrum.apiclient.RestTemplateBase;

@RunWith(MockitoJUnitRunner.class)
public class RestTemplateBaseTest {

	@InjectMocks
	private RestTemplateBase restTemplateBase;

	@Mock
	private RestTemplate template;

	@Test
	public void testPerformRequest() {
		restTemplateBase.performRequest(Mockito.anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
				Mockito.<Class<?>> any());
		Mockito.verify(template, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<Class<?>> any());
	}

	@Test
	public void testPerformRequestWithParameterizedTypeReference() {
		restTemplateBase.performRequest(Mockito.anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<?>> any(),
				Mockito.<ParameterizedTypeReference<?>> any());
		Mockito.verify(template, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<ParameterizedTypeReference<?>> any());
	}
}
